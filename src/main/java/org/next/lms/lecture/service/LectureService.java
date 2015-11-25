package org.next.lms.lecture.service;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.result.Result;
import org.next.lms.lecture.UserGroup;
import org.next.lms.lecture.auth.ApprovalState;
import org.next.lms.lecture.auth.RegisterPolicy;
import org.next.lms.lecture.dto.*;
import org.next.lms.lecture.repository.ContentTypeRepository;
import org.next.lms.lecture.repository.UserGroupCanReadContentRepository;
import org.next.lms.lecture.repository.UserGroupCanWriteContentRepository;
import org.next.lms.lecture.repository.UserGroupRepository;
import org.next.lms.message.MessageService;
import org.next.lms.message.template.EnrollRequestTemplate;
import org.next.lms.message.template.EnrollMessageTemplate;
import org.next.lms.message.template.EnrollRejectMessageTemplate;
import org.next.lms.user.User;
import org.next.lms.lecture.auth.LectureAuth;
import org.next.lms.lecture.UserEnrolledLecture;
import org.next.lms.lecture.Lecture;
import org.next.infra.repository.LectureRepository;
import org.next.lms.like.repository.UserEnrolledLectureRepository;
import org.next.lms.user.dto.UserSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.result.Result.success;

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


    public Result save(Lecture lecture, User user) {
        lecture.setHostUser(user);
        lectureRepository.save(lecture);
        lecture.setAuthorities(userGroupCanReadContentRepository, userGroupCanWriteContentRepository);
        getEnrollRelation(user, lecture);   // TODO not void but ???
        return success(lecture.getId());
    }

    public Result update(Lecture lecture, User user) {
        Lecture lectureFromDB = assureNotNull(lectureRepository.findOne(lecture.getId()));
        lectureAuthority.checkUpdateRight(lectureFromDB, user);

        lectureFromDB.update(lecture, userGroupRepository, contentTypeRepository, userGroupCanReadContentRepository, userGroupCanWriteContentRepository);
        lectureFromDB.setAuthorities(userGroupCanReadContentRepository, userGroupCanWriteContentRepository);

        return success(lecture.getId());
    }


    public Result getById(Long lectureId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        if (lecture.getHostUser().equals(user))
            return success(new LectureForHostUserDto(lecture));
        return success(new LectureDto(lecture));
    }

    public Result getTypeById(Long lectureId) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        return success(new LectureTypeDto(lecture));
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
            relation.setSideMenu(true);
            userEnrolledLectureRepository.save(relation);
            messageService.newMessage(lecture.getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()), new EnrollMessageTemplate());
            return success();
        } else if (lecture.getRegisterPolicy().equals(RegisterPolicy.NEED_APPROVAL)) {
            relation.setApprovalState(ApprovalState.WAITING_APPROVAL);
            userEnrolledLectureRepository.save(relation);
            messageService.newMessage(lecture.getHostUser(), new EnrollRequestTemplate());
            return new Result(ResponseCode.Enroll.WAITING_FOR_APPROVAL, new LectureSummaryDto(lecture));
        }
        return new Result(ResponseCode.WRONG_ACCESS);
    }


    public Result approval(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLectureWithAuthCheck(id, userId, user);
        userEnrolledLecture.setApprovalState(ApprovalState.OK);
        userEnrolledLectureRepository.save(userEnrolledLecture);
        messageService.newMessage(userEnrolledLecture.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()), new EnrollMessageTemplate());
        return success(new UserSummaryDto(userEnrolledLecture));
    }

    public Result reject(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLectureWithAuthCheck(id, userId, user);
        userEnrolledLecture.setApprovalState(ApprovalState.REJECT);
        userEnrolledLectureRepository.save(userEnrolledLecture);
        messageService.newMessage(userEnrolledLecture.getUser(), new EnrollRejectMessageTemplate());
        return success();
    }


    public UserEnrolledLecture getEnrollRelation(User user, Lecture lecture) {
        UserEnrolledLecture relation = userEnrolledLectureRepository.findOneByUserIdAndLectureId(user.getId(), lecture.getId());
        if (relation == null) {
            relation = new UserEnrolledLecture();
            relation.setLecture(lecture);
            relation.setUser(user);
            userEnrolledLectureRepository.save(relation);
        }
        return relation;
    }

    public Result delete(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkDeleteRight(user, lecture);
        lecture.setDeleteState();
        lectureRepository.save(lecture);
        return success();
    }

    public Result sideMenuToggle(Long lectureId, User user) {
        UserEnrolledLecture userEnrolledLecture = user.getEnrolledLectures().stream().filter(relation -> relation.getLecture().getId().equals(lectureId)).findFirst().get();
        userEnrolledLecture.sideMenuToggle();
        userEnrolledLectureRepository.save(userEnrolledLecture);
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
        UserEnrolledLecture userEnrolledLecture = lecture.getUserEnrolledLectures().stream().filter(relation -> relation.getUser().getId().equals(userId)).findFirst().get();
        UserGroup group = lecture.getUserGroups().stream().filter(userGroup -> userGroup.getId().equals(groupId)).findFirst().get();
        userEnrolledLecture.setUserGroup(group);
        userEnrolledLectureRepository.save(userEnrolledLecture);
        return success(new UserGroupDto(group));
    }
}
