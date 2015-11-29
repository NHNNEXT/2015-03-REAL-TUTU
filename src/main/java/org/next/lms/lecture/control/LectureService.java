package org.next.lms.lecture.control;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.*;
import org.next.infra.result.Result;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.control.auth.LectureAuth;
import org.next.lms.lecture.control.auth.RegisterPolicy;
import org.next.lms.lecture.domain.dto.*;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.template.LectureEnrollApprovedMessage;
import org.next.lms.message.template.LectureEnrolledMessage;
import org.next.lms.message.template.LectureEnrollRejectMessage;
import org.next.lms.message.template.LectureEnrollRequestMessage;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
@Transactional
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserEnrolledLectureRepository userEnrolledLectureRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private LectureAuth lectureAuthority;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private ContentTypeRepository contentTypeRepository;

    @Autowired
    private UserGroupCanReadContentRepository userGroupCanReadContentRepository;

    @Autowired
    private UserGroupCanWriteContentRepository userGroupCanWriteContentRepository;

    @Autowired
    private UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository;

    @Autowired
    private ContentRepository contentRepository;


    public Result save(Lecture lecture, User user) {
        lecture.setHostUser(user);
        lectureRepository.save(lecture);
        lecture.setAuthorities(userGroupCanReadContentRepository, userGroupCanWriteContentRepository, userGroupCanReadSubmitRepository);
        enrollLecture(user, lecture);
        return success(lecture.getId());
    }

    public Result update(Lecture lecture, User user) {
        Lecture lectureFromDB = assureNotNull(lectureRepository.findOne(lecture.getId()));
        lectureAuthority.checkUpdateRight(lectureFromDB, user);

        lectureFromDB.update(lecture, userGroupRepository, contentTypeRepository, userGroupCanReadContentRepository, userGroupCanWriteContentRepository, userGroupCanReadSubmitRepository, contentRepository);
        lectureFromDB.setAuthorities(userGroupCanReadContentRepository, userGroupCanWriteContentRepository, userGroupCanReadSubmitRepository);

        return success(lecture.getId());
    }


    public Result getLectureById(Long lectureId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        if (lecture.getHostUser().equals(user))
            return success(new LectureForHostUserDto(lecture));
        return success(new LectureDto(lecture));
    }

    public Result getSupportContentTypeById(Long lectureId) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        return success(new SupportContentTypeDto(lecture));
    }


    public Result getList() {
        return success(lectureRepository.findAll().stream().map(LectureSummaryDto::new).collect(Collectors.toList()));
    }

    public Result enroll(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));

        if (lecture.getRegisterPolicy().equals(RegisterPolicy.INVITE_ONLY))
            return new Result(ResponseCode.WRONG_ACCESS);

        UserEnrolledLecture relation = getEnrollRelation(user, lecture);

        if (lecture.getRegisterPolicy().equals(RegisterPolicy.NO_ADDITIONAL)) {
            relation.setApprovalState(ApprovalState.OK);
            relation.showLectureOnSideBar();
            userEnrolledLectureRepository.save(relation);

            messageService
                    .send(new LectureEnrolledMessage(lecture))
                    .to(lecture.getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()));
            return success();
        } else if (lecture.getRegisterPolicy().equals(RegisterPolicy.NEED_APPROVAL)) {
            relation.setApprovalState(ApprovalState.WAITING_APPROVAL);
            userEnrolledLectureRepository.save(relation);

            messageService
                    .send(new LectureEnrollRequestMessage(lecture, user, relation, Math.toIntExact(relation.getLecture().getUserEnrolledLectures().stream().filter(enrolledUser -> enrolledUser.getApprovalState().equals(ApprovalState.WAITING_APPROVAL)).count())))
                    .to(lecture.getHostUser());
            return new Result(ResponseCode.Enroll.WAITING_FOR_APPROVAL, new LectureSummaryDto(lecture));
        }
        return new Result(ResponseCode.WRONG_ACCESS);
    }


    public Result approval(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLectureWithAuthCheck(id, userId, user);
        userEnrolledLecture.setApprovalState(ApprovalState.OK);

        messageService
                .send(new LectureEnrollApprovedMessage(userEnrolledLecture.getLecture()))
                .to(userEnrolledLecture.getUser());

        return success(new UserSummaryDto(userEnrolledLecture));
    }

    public Result reject(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLectureWithAuthCheck(id, userId, user);
        userEnrolledLecture.setApprovalState(ApprovalState.REJECT);

        messageService
                .send(new LectureEnrollRejectMessage(userEnrolledLecture.getLecture()))
                .to(userEnrolledLecture.getUser());
        return success();
    }


    public UserEnrolledLecture getEnrollRelation(User user, Lecture lecture) {
        UserEnrolledLecture relation = userEnrolledLectureRepository.findOneByUserIdAndLectureId(user.getId(), lecture.getId());
        return relation != null ? relation : enrollLecture(user, lecture);
    }

    private UserEnrolledLecture enrollLecture(User user, Lecture lecture) {
        UserEnrolledLecture relation = new UserEnrolledLecture();;
        relation.setLecture(lecture);
        relation.setUser(user);
        userEnrolledLectureRepository.save(relation);
        return relation;
    }

    public Result delete(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkDeleteRight(user, lecture);
        lecture.setDeleteState();

        return success();
    }

    public Result sideMenuToggle(Long lectureId, User user) {
        UserEnrolledLecture userEnrolledLecture = assureNotNull(user.getEnrolledLectures().stream().filter(relation -> relation.getLecture().getId().equals(lectureId)).findFirst().get());
        userEnrolledLecture.toggleSideMenu();

        return success(userEnrolledLecture.getSideMenu());
    }

    private UserEnrolledLecture getUserEnrolledLectureWithAuthCheck(Long id, Long userId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkApprovalRight(user, lecture);
        return lecture.getUserEnrolledLectures().stream().filter(relation -> relation.getUser().getId().equals(userId)).findFirst().get();
    }

    public Result userGroupChange(Long lectureId, Long groupId, Long userId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        lectureAuthority.checkGroupChangeRight(user, lecture);
        UserEnrolledLecture userEnrolledLecture = assureNotNull(lecture.getUserEnrolledLectures().stream().filter(relation -> relation.getUser().getId().equals(userId)).findFirst().get());
        UserGroup group = assureNotNull(lecture.getUserGroups().stream().filter(userGroup -> userGroup.getId().equals(groupId)).findFirst().get());
        userEnrolledLecture.setUserGroup(group);

        return success(new UserGroupDto(group));
    }
}
