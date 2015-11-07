package org.next.lms.service;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.repository.UserInfoRepository;
import org.next.lms.auth.LectureAuthority;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.dto.LectureDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.Lesson;
import org.next.lms.repository.LectureRepository;
import org.next.lms.repository.UserEnrolledLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.CommonUtils.parseList;

@Service
public class LectureService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserEnrolledLectureRepository userEnrolledLectureRepository;

    @Autowired
    private UserInfoBroker userInfoBroker;

    @Autowired
    private LectureAuthority lectureAuthority;


    public CommonJsonResponse save(Lecture lecture, String managerIds, String lessonString, HttpSession session) {
        UserInfo userInfo = userInfoBroker.getUserInfo(session);
        if (lecture.getId() != null) {
            lectureAuthority.checkUpdateRight(userInfo, assureNotNull(lectureRepository.findOne(lecture.getId())));
        } else
            lecture.setHostUser(userInfo);

        List<Long> managerIdList = parseList(Long.class, managerIds);
        if (managerIdList != null) {
            List<UserInfo> managers = managerIdList.stream().map(userInfoRepository::findOne).collect(Collectors.toList());
            lecture.addManagers(managers);
        }

        List<Lesson> lessons = parseList(Lesson.class, lessonString);
        lecture.addLessons(lessons);

        lectureRepository.save(lecture);
        return successJsonResponse(new LectureDto(lecture));
    }

    public LectureDto getById(Long lectureId) {
        return new LectureDto(lectureRepository.findOne(lectureId));
    }

    public List<LectureDto> getList() {
        return lectureRepository.findAll().stream().map(LectureDto::new).collect(Collectors.toList());
    }

    public CommonJsonResponse enroll(Long id, HttpSession session) {
        LoginAccount user = userInfoBroker.getLoginAccount(session);
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        UserEnrolledLecture relation = new UserEnrolledLecture();
        relation.setLecture(lecture);
        relation.setUserInfo(user.getUserInfo());
        userEnrolledLectureRepository.save(relation);
        return successJsonResponse();
    }

    public CommonJsonResponse delete(Long id, HttpSession session) {
        LoginAccount user = userInfoBroker.getLoginAccount(session);
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkDeleteRight(user, lecture);
        return null;
    }
}
