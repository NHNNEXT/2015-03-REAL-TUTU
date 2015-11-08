package org.next.lms.service;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.repository.UserInfoRepository;
import org.next.lms.auth.LectureAuthority;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;
import org.next.lms.dto.LectureDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.Lesson;
import org.next.lms.repository.LectureRepository;
import org.next.lms.repository.LessonRepository;
import org.next.lms.repository.UserEnrolledLectureRepository;
import org.next.lms.repository.UserManageLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
    private UserManageLectureRepository userManageLectureRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserInfoBroker userInfoBroker;

    @Autowired
    private LectureAuthority lectureAuthority;


    public CommonJsonResponse save(Lecture lecture, String managerIds, String lessonString, HttpSession session) {
        UserInfo userInfo = userInfoBroker.getUserInfo(session);
        lecture.setDate(new Date());

        if (lecture.getId() != null) {
            return updateLecture(lecture, userInfo, managerIds, lessonString);
        }

        List<Long> managerIdList = assureNotNull(parseList(Long.class, managerIds));
        List<UserInfo> managers = managerIdList.stream().map(userInfoRepository::findOne).collect(Collectors.toList());
        lecture.addManagers(managers);
        List<Lesson> lessons = assureNotNull(parseList(Lesson.class, lessonString));
        lessons.forEach(lesson -> {
            lesson.setLecture(lecture);
            lessonRepository.save(lesson);
        });

        lecture.setHostUser(userInfo);

        lectureRepository.save(lecture);
        return successJsonResponse(new LectureDto(lecture));
    }

    private CommonJsonResponse updateLecture(Lecture lecture, UserInfo userInfo, String managerIds, String lessonString) {
        Lecture fromDB = assureNotNull(lectureRepository.findOne(lecture.getId()));
        lectureAuthority.checkUpdateRight(fromDB, userInfo);
        fromDB.update(lecture);
        List<Long> managerIdList = assureNotNull(parseList(Long.class, managerIds));
        List<UserInfo> managers = managerIdList.stream().map(userInfoRepository::findOne).collect(Collectors.toList());
        userManageLectureRepository.deleteByLectureId(fromDB.getId());
        fromDB.addManagers(managers);
        List<Lesson> lessons = assureNotNull(parseList(Lesson.class, lessonString));
        lessonRepository.deleteByLectureId(fromDB.getId());
        lessons.forEach(lesson -> {
            lesson.setLecture(fromDB);
            lessonRepository.save(lesson);
        });
        lectureRepository.save(fromDB);
        return successJsonResponse(new LectureDto(fromDB));
    }


    public LectureDto getById(Long lectureId) {
        return new LectureDto(assureNotNull(lectureRepository.findOne(lectureId)));
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
        lecture.setDeleteState();
        lectureRepository.save(lecture);
        return successJsonResponse();
    }
}
