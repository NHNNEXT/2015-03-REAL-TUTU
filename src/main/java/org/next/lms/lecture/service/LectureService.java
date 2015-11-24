package org.next.lms.lecture.service;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.view.JsonView;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.view.JsonView.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
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


    public JsonView save(Lecture lecture, User user) {
        lecture.setHostUser(user);
        lectureRepository.save(lecture);
        lecture.setAuthorities(userGroupCanReadContentRepository, userGroupCanWriteContentRepository);
        enroll(user, lecture);
        return successJsonResponse(lecture.getId());
    }

    public JsonView update(Lecture lecture, User user) {
        Lecture fromDB = lectureRepository.findOne(lecture.getId());
        lectureAuthority.checkUpdateRight(fromDB, user);

        fromDB.update(lecture, userGroupRepository, contentTypeRepository, userGroupCanReadContentRepository, userGroupCanWriteContentRepository);
        lectureRepository.save(fromDB);
        fromDB.setAuthorities(userGroupCanReadContentRepository, userGroupCanWriteContentRepository);

        return successJsonResponse(lecture.getId());
    }


    public LectureDto getById(Long lectureId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        if (lecture.getHostUser().equals(user))
            return new LectureForHostUserDto(lecture);
        return new LectureDto(lecture);
    }

    public LectureTypeDto getTypeById(Long lectureId) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        return new LectureTypeDto(lecture);
    }


    public List<LectureSummaryDto> getList() {
        return lectureRepository.findAll().stream().map(LectureSummaryDto::new).collect(Collectors.toList());
    }

    public JsonView enroll(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));

        if (lecture.getRegisterPolicy().equals(RegisterPolicy.INVITE_ONLY))
            return new JsonView(ResponseCode.WRONG_ACCESS);

        UserEnrolledLecture relation = enroll(user, lecture);

        if (lecture.getRegisterPolicy().equals(RegisterPolicy.NO_ADDITIONAL)) {
            relation.setApprovalState(ApprovalState.OK);
            relation.setSideMenu(true);
            userEnrolledLectureRepository.save(relation);
            messageService.newMessage(lecture.getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()), new EnrollMessageTemplate());
            return successJsonResponse();
        } else if (lecture.getRegisterPolicy().equals(RegisterPolicy.NEED_APPROVAL)) {
            relation.setApprovalState(ApprovalState.WAITING_APPROVAL);
            userEnrolledLectureRepository.save(relation);
            messageService.newMessage(lecture.getHostUser(), new EnrollRequestTemplate());
            return new JsonView(ResponseCode.Enroll.WAITING_FOR_APPROVAL, new LectureSummaryDto(lecture));
        }
        return new JsonView(ResponseCode.WRONG_ACCESS);
    }


    public JsonView approval(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLecture(id, userId, user);
        userEnrolledLecture.setApprovalState(ApprovalState.OK);
        userEnrolledLectureRepository.save(userEnrolledLecture);
        messageService.newMessage(userEnrolledLecture.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()), new EnrollMessageTemplate());
        return successJsonResponse();
    }

    public JsonView reject(Long id, Long userId, User user) {
        UserEnrolledLecture userEnrolledLecture = getUserEnrolledLecture(id, userId, user);
        userEnrolledLecture.setApprovalState(ApprovalState.REJECT);
        userEnrolledLectureRepository.save(userEnrolledLecture);
        messageService.newMessage(userEnrolledLecture.getUser(), new EnrollRejectMessageTemplate());
        return successJsonResponse();
    }


    private UserEnrolledLecture enroll(User user, Lecture lecture) {
        UserEnrolledLecture relation = new UserEnrolledLecture();
        relation.setLecture(lecture);
        relation.setUser(user);
        userEnrolledLectureRepository.save(relation);
        return relation;
    }

    public JsonView delete(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkDeleteRight(user, lecture);
        lecture.setDeleteState();
        lectureRepository.save(lecture);
        return successJsonResponse();
    }

    public JsonView sideMenuToggle(Long lectureId, User user) {
        UserEnrolledLecture userEnrolledLecture = user.getEnrolledLectures().stream().filter(relation -> relation.getLecture().getId().equals(lectureId)).findFirst().get();
        userEnrolledLecture.sideMenuToggle();
        userEnrolledLectureRepository.save(userEnrolledLecture);
        return successJsonResponse(userEnrolledLecture.getSideMenu());
    }


    private UserEnrolledLecture getUserEnrolledLecture(Long id, Long userId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkApprovalRight(user, lecture);
        return lecture.getUserEnrolledLectures().stream().filter(relation -> relation.getUser().getId().equals(userId)).findFirst().get();
    }

    public JsonView userGroupChange(Long lectureId, Long groupId, Long userId, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        lectureAuthority.checkGroupChangeRight(user, lecture);
        UserEnrolledLecture userEnrolledLecture = lecture.getUserEnrolledLectures().stream().filter(relation -> relation.getUser().getId().equals(userId)).findFirst().get();
        UserGroup group = lecture.getUserGroups().stream().filter(userGroup -> userGroup.getId().equals(groupId)).findFirst().get();
        userEnrolledLecture.setUserGroup(group);
        userEnrolledLectureRepository.save(userEnrolledLecture);
        return successJsonResponse(new UserGroupDto(group));
    }
}
