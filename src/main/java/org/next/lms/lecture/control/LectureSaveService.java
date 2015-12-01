package org.next.lms.lecture.control;

import org.next.infra.repository.*;
import org.next.infra.result.Result;
import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.content.control.auth.UserGroupCanReadSubmit;
import org.next.lms.content.control.auth.UserGroupCanWriteContent;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.control.auth.LectureAuth;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
@Transactional
public class LectureSaveService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureAuth lectureAuthority;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private ContentGroupRepository contentGroupRepository;

    @Autowired
    private UserGroupCanReadContentRepository userGroupCanReadContentRepository;

    @Autowired
    private UserGroupCanWriteContentRepository userGroupCanWriteContentRepository;

    @Autowired
    private UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ReplyRepository replyRepository;


    public Result save(Lecture lecture, User user) {
        lecture.setHostUser(user);
        lectureRepository.save(lecture);
        setAuthorities(lecture);
        lectureService.enrollLecture(user, lecture);
        return success(lecture.getId());
    }

    public Result update(Lecture lecture, User user) {
        Lecture lectureFromDB = assureNotNull(lectureRepository.findOne(lecture.getId()));
        lectureAuthority.checkUpdateRight(lectureFromDB, user);

        lectureFromDB.update(lecture);
        removeRelations(lectureFromDB, lecture);
        updateAndAddNew(lectureFromDB, lecture);

        setAuthorities(lectureFromDB);

        return success(lecture.getId());
    }


    private void updateAndAddNew(Lecture lectureFromDB, Lecture lecture) {
        updateUserGroups(lectureFromDB, lecture);
        EnsureDefaultGroupExist(lectureFromDB.getUserGroups());
        updateContentGroups(lectureFromDB, lecture);
    }

    private void updateContentGroups(Lecture lectureFromDB, Lecture lecture) {
        List<ContentGroup> updatedContentGroups = lecture.getContentGroups();
        List<ContentGroup> dbContentGroups = lectureFromDB.getContentGroups();
        updatedContentGroups.forEach(updatedContentType -> {
            if (!dbContentGroups.contains(updatedContentType)) {
                dbContentGroups.add(updatedContentType);
                updatedContentType.setLecture(lecture);
            } else {
                dbContentGroups.stream().filter(updatedContentType::equals).findFirst().get().update(updatedContentType);
            }
        });
    }

    private void updateUserGroups(Lecture lectureFromDB, Lecture lecture) {
        List<UserGroup> updatedUserGroups = lecture.getUserGroups();
        List<UserGroup> dbUserGroups = lectureFromDB.getUserGroups();
        updatedUserGroups.forEach(updatedUserGroup -> {
            if (!dbUserGroups.contains(updatedUserGroup)) {
                updatedUserGroup.setLecture(lecture);
                dbUserGroups.add(updatedUserGroup);
            } else {
                dbUserGroups.stream().filter(updatedUserGroup::equals).findFirst().get().update(updatedUserGroup);
            }
        });
    }


    private void EnsureDefaultGroupExist(List<UserGroup> userGroups) {
        if (!userGroups.stream().filter(UserGroup::getDefaultGroup).findAny().isPresent()) {
            userGroups.get(0).setDefaultGroup(true);
        }
    }

    private void removeRelations(Lecture lectureFromDB, Lecture lecture) {
        List<UserGroup> userGroups = lectureFromDB.getUserGroups();
        List<ContentGroup> contentGroups = lectureFromDB.getContentGroups();
        List<UserGroup> deletedUserGroups = userGroups.stream().filter(userGroup -> !lecture.getUserGroups().contains(userGroup)).collect(Collectors.toList());
        List<ContentGroup> deletedContentGroups = contentGroups.stream().filter(contentGroup -> !lecture.getContentGroups().contains(contentGroup)).collect(Collectors.toList());
        deletedContentGroups.forEach(deletedContentGroup -> {
            removeRights(deletedContentGroup);
            removeAllContents(deletedContentGroup); // TODO 로직변경
        });
        deletedUserGroups.forEach(this::removeRights);
        userGroups.removeAll(deletedUserGroups);
        userGroupRepository.delete(deletedUserGroups);
        contentGroups.removeAll(deletedContentGroups);
        contentGroupRepository.delete(deletedContentGroups);
    }

    private void removeRights(UserGroup deletedUserGroup) {
        userGroupCanReadContentRepository.deleteByUserGroupId(deletedUserGroup.getId());
        userGroupCanWriteContentRepository.deleteByUserGroupId(deletedUserGroup.getId());
        userGroupCanReadSubmitRepository.deleteByUserGroupId(deletedUserGroup.getId());
    }

    private void removeRights(ContentGroup deletedContentGroup) {
        userGroupCanReadContentRepository.deleteByContentGroupId(deletedContentGroup.getId());
        userGroupCanWriteContentRepository.deleteByContentGroupId(deletedContentGroup.getId());
        userGroupCanReadSubmitRepository.deleteByContentGroupId(deletedContentGroup.getId());
    }

    private void removeAllContents(ContentGroup deletedContentGroup) {
        ContentGroup type = contentGroupRepository.findOne(deletedContentGroup.getId());
        deletedContentGroup.getContents().stream().forEach(content -> {
            replyRepository.deleteByContentId(content.getId());
        });
        contentRepository.delete(type.getContents());
    }


    public void setAuthorities(Lecture lecture) {
        lecture.getUserGroups().forEach(userGroup -> {
            userGroup.setLecture(lecture);
            userGroupCanReadContentRepository.deleteByUserGroupId(userGroup.getId());
            userGroupCanReadSubmitRepository.deleteByUserGroupId(userGroup.getId());
            userGroupCanWriteContentRepository.deleteByUserGroupId(userGroup.getId());
        });
        lecture.getUserGroups().forEach(contentGroup -> {
            contentGroup.setLecture(lecture);
            userGroupCanReadContentRepository.deleteByContentGroupId(contentGroup.getId());
            userGroupCanReadSubmitRepository.deleteByContentGroupId(contentGroup.getId());
            userGroupCanWriteContentRepository.deleteByContentGroupId(contentGroup.getId());
        });

        createAuthTable(lecture);
    }

    /*
        아래와 같이 생긴 모양의 권한 테이블을 생성하는 메서드

        Readable 권한
                        user group  |  user group
        content Type |      O               O
        content Type |      O               X


        Writable 권한
                        user group  |  user group
        content Type |      O               O
        content Type |      O               X

    */
    private void createAuthTable(Lecture lecture) {
        List<UserGroup> userGroups = lecture.getUserGroups();
        List<ContentGroup> contentGroups = lecture.getContentGroups();
        List<List<Boolean>> writable = lecture.getWritable();
        List<List<Boolean>> readable = lecture.getReadable();
        List<List<Boolean>> submitReadable = lecture.getSubmitReadable();
        for (int userGrpIdx = 0; userGrpIdx < userGroups.size(); userGrpIdx++) {
            for (int contentTypIdx = 0; contentTypIdx < contentGroups.size(); contentTypIdx++) {

                if (userGrpIdx < writable.size() && contentTypIdx < writable.get(userGrpIdx).size())    // check table index range
                    if (writable.get(userGrpIdx).get(contentTypIdx))
                        makeWriteAuth(userGroupCanWriteContentRepository, userGroups.get(userGrpIdx), contentGroups.get(contentTypIdx));

                if (userGrpIdx < readable.size() && contentTypIdx < readable.get(userGrpIdx).size())    // check table index range
                    if (readable.get(userGrpIdx).get(contentTypIdx))
                        makeReadAuth(userGroupCanReadContentRepository, userGroups.get(userGrpIdx), contentGroups.get(contentTypIdx));

                if (userGrpIdx < submitReadable.size() && contentTypIdx < submitReadable.get(userGrpIdx).size())    // check table index range
                    if (submitReadable.get(userGrpIdx).get(contentTypIdx))
                        makeReadSubmitAuth(userGroupCanReadSubmitRepository, userGroups.get(userGrpIdx), contentGroups.get(contentTypIdx));
            }
        }
    }

    private void makeReadSubmitAuth(UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository, UserGroup userGroup, ContentGroup contentGroup) {
        UserGroupCanReadSubmit userGroupCanReadSubmit = new UserGroupCanReadSubmit();
        userGroupCanReadSubmit.setUserGroup(userGroup);
        userGroupCanReadSubmit.setContentGroup(contentGroup);
        userGroupCanReadSubmitRepository.save(userGroupCanReadSubmit);
    }

    private void makeReadAuth(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroup userGroup, ContentGroup contentGroup) {
        UserGroupCanReadContent userGroupCanReadContent = new UserGroupCanReadContent();
        userGroupCanReadContent.setUserGroup(userGroup);
        userGroupCanReadContent.setContentGroup(contentGroup);
        userGroupCanReadContentRepository.save(userGroupCanReadContent);
    }

    private void makeWriteAuth(UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroup userGroup, ContentGroup contentGroup) {
        UserGroupCanWriteContent userGroupCanWriteContent = new UserGroupCanWriteContent();
        userGroupCanWriteContent.setUserGroup(userGroup);
        userGroupCanWriteContent.setContentGroup(contentGroup);
        userGroupCanWriteContentRepository.save(userGroupCanWriteContent);
    }


}
