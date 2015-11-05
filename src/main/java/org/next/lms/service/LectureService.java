package org.next.lms.service;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.repository.UserInfoRepository;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.dto.LectureDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.Lesson;
import org.next.lms.repository.LectureRepository;
import org.next.lms.repository.UserEnrolledLectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;
import static org.next.infra.util.CommonUtils.parseList;

@Service
public class LectureService {

    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserEnrolledLectureRepository userEnrolledLectureRepository;

    @Autowired
    private UserInfoBroker userInfoBroker;



    public CommonJsonResponse save(Lecture lecture, String managerIds, String lessonString, HttpSession session) {
        UserInfo userInfo = userInfoBroker.getUserInfo(session);
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

    public LectureDto getDtoById(Long lectureId) {
        return new LectureDto(lectureRepository.getOne(lectureId));
    }

    public List<LectureDto> getDtoList() {
        List<LectureDto> result = new ArrayList<>();
        lectureRepository.findAll().forEach(lecture -> result.add(new LectureDto(lecture)));
        return result;
    }

    public CommonJsonResponse enroll(Long id, HttpSession session) {
        LoginAccount user = userInfoBroker.getLoginAccount(session);
        Lecture lecture = lectureRepository.getOne(id);
        UserEnrolledLecture relation = new UserEnrolledLecture();
        relation.setLecture(lecture);
        relation.setUserInfo(user.getUserInfo());
        userEnrolledLectureRepository.save(relation);
        return successJsonResponse();
    }
}
