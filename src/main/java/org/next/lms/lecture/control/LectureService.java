package org.next.lms.lecture.control;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.*;
import org.next.infra.result.Result;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.control.auth.LectureAuth;
import org.next.lms.lecture.control.auth.RegisterPolicy;
import org.next.lms.lecture.domain.dto.*;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.domain.PackagedMessage;
import org.next.lms.message.template.LectureEnrollApprovedMessage;
import org.next.lms.message.template.LectureEnrolledMessage;
import org.next.lms.message.template.LectureEnrollRejectMessage;
import org.next.lms.message.template.LectureEnrollRequestMessage;
import org.next.lms.submit.UserHaveToSubmit;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.lms.message.domain.MessageBuilder.aMessage;

@Service
@Transactional
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserEnrolledLectureRepository userEnrolledLectureRepository;

    @Autowired
    private UserHaveToSubmitRepository userHaveToSubmitRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private LectureAuth lectureAuthority;


    public Result getLectureById(Long lectureId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        if (lecture.getHostUser().equals(user))
            return success(new LectureForHostUserDto(lecture, user));
        return success(new LectureDto(lecture, user));
    }

    public Result getWriteInfoById(Long lectureId) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        return success(new WriteInfoDto(lecture));
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

            PackagedMessage message = aMessage().from(user).to(user).with(new LectureEnrolledMessage(lecture)).packaging();

            messageService.send(message);

            return success();
        } else if (lecture.getRegisterPolicy().equals(RegisterPolicy.NEED_APPROVAL)) {
            relation.setApprovalState(ApprovalState.WAITING_APPROVAL);
            userEnrolledLectureRepository.save(relation);

            PackagedMessage message = aMessage().from(user).to(lecture.getHostUser())
                    .with(new LectureEnrollRequestMessage(lecture, user, relation, Math.toIntExact(relation.getLecture().getUserEnrolledLectures().stream().filter(enrolledUser -> enrolledUser.getApprovalState().equals(ApprovalState.WAITING_APPROVAL)).count()))).packaging();

            messageService.send(message);
            return new Result(ResponseCode.Enroll.WAITING_FOR_APPROVAL, new LectureSummaryDto(lecture));
        }
        return new Result(ResponseCode.WRONG_ACCESS);
    }

    public Result approval(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLectureWithAuthCheck(id, userId, user);
        userEnrolledLecture.setApprovalState(ApprovalState.OK);
        userEnrolledLecture.setSideMenu(true);

        PackagedMessage enrolledLectureApprovedNoticeMessage = aMessage().from(user).to(userEnrolledLecture.getUser())
                .with(new LectureEnrollApprovedMessage(userEnrolledLecture.getLecture())).packaging();
        messageService.send(enrolledLectureApprovedNoticeMessage);

        List<User> waitingForApprovalUser = userEnrolledLecture.getLecture().getUserEnrolledLectures().stream().filter(enrolledUser -> enrolledUser.getApprovalState().equals(ApprovalState.WAITING_APPROVAL)).map(UserEnrolledLecture::getUser).collect(Collectors.toList());
        waitingForApprovalUser.remove(user);

        User randomWaitingUser = (waitingForApprovalUser.size() > 0) ? waitingForApprovalUser.get(0) : null;

        PackagedMessage remainApprovalWatingStudentInfoMessage = aMessage().from(user).to(userEnrolledLecture.getLecture().getHostUser())
                .with(new LectureEnrollRequestMessage(userEnrolledLecture.getLecture(), randomWaitingUser, userEnrolledLecture, Math.toIntExact(userEnrolledLecture.getLecture().getUserEnrolledLectures().stream().filter(enrolledUser -> enrolledUser.getApprovalState().equals(ApprovalState.WAITING_APPROVAL)).count()))).packaging();
        messageService.send(remainApprovalWatingStudentInfoMessage);

        return success(new UserSummaryDto(userEnrolledLecture));
    }

    public Result reject(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLectureWithAuthCheck(id, userId, user);
        userEnrolledLectureRepository.delete(userEnrolledLecture);

        PackagedMessage message = aMessage().from(user).to(userEnrolledLecture.getUser())
                .with(new LectureEnrollRejectMessage(userEnrolledLecture.getLecture())).packaging();

        messageService.send(message);
        return success();
    }


    public UserEnrolledLecture getEnrollRelation(User user, Lecture lecture) {
        UserEnrolledLecture relation = userEnrolledLectureRepository.findOneByUserIdAndLectureId(user.getId(), lecture.getId());
        return relation != null ? relation : enrollLecture(user, lecture);
    }

    UserEnrolledLecture enrollLecture(User user, Lecture lecture) {
        UserEnrolledLecture relation = new UserEnrolledLecture();
        relation.setLecture(lecture);
        relation.setUser(user);
        relation.setUserGroup(lecture.getUserGroups().stream().filter(UserGroup::getDefaultGroup).findAny().get());
        userEnrolledLectureRepository.save(relation);
        return relation;
    }

    public Result delete(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));

        lectureAuthority.checkDeleteRight(lecture, user);

//        lecture.setDeleteState();
//        userEnrolledLectureRepository.deleteByLectureIdOrUserId(lecture.getId(), user.getId());
        lectureRepository.delete(lecture);

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

    public Result expel(Long lectureId, Long userId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        lectureAuthority.checkExpelRight(lecture, userId, user);
        UserEnrolledLecture userEnrolledLecture = userEnrolledLectureRepository.findOneByUserIdAndLectureId(userId, lectureId);
        userEnrolledLectureRepository.delete(userEnrolledLecture);
        return success();
    }
}
