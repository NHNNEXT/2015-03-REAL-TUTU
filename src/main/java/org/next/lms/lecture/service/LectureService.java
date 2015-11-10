package org.next.lms.lecture.service;

import org.next.infra.view.JsonView;
import org.next.lms.user.User;
import org.next.lms.lecture.auth.LectureAuth;
import org.next.infra.relation.UserEnrolledLecture;
import org.next.lms.lecture.dto.LectureDto;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.Lesson;
import org.next.infra.repository.LectureRepository;
import org.next.infra.repository.LessonRepository;
import org.next.infra.relation.repository.UserEnrolledLectureRepository;
import org.next.infra.relation.repository.UserManageLectureRepository;
import org.next.lms.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.view.JsonView.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.CommonUtils.parseList;

@Service
public class LectureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserEnrolledLectureRepository userEnrolledLectureRepository;

    @Autowired
    private UserManageLectureRepository userManageLectureRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LectureAuth lectureAuthority;


    public JsonView save(Lecture lecture, String managerIds, String lessonString, User user) {
        lecture.setDate(new Date());

        if (lecture.getId() != null) {
            return updateLecture(lecture, user, managerIds, lessonString);
        }

        List<Long> managerIdList = assureNotNull(parseList(Long.class, managerIds));
        List<User> managers = managerIdList.stream().map(userRepository::findOne).collect(Collectors.toList());
        lecture.addManagers(managers);
        List<Lesson> lessons = assureNotNull(parseList(Lesson.class, lessonString));
        lessons.forEach(lesson -> {
            lesson.setLecture(lecture);
            lessonRepository.save(lesson);
        });

        lecture.setHostUser(user);

        lectureRepository.save(lecture);
        return successJsonResponse(new LectureDto(lecture));
    }

    private JsonView updateLecture(Lecture lecture, User user, String managerIds, String lessonString) {
        Lecture fromDB = assureNotNull(lectureRepository.findOne(lecture.getId()));
        lectureAuthority.checkUpdateRight(fromDB, user);
        fromDB.update(lecture);
        List<Long> managerIdList = assureNotNull(parseList(Long.class, managerIds));
        List<User> managers = managerIdList.stream().map(userRepository::findOne).collect(Collectors.toList());
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

    public JsonView enroll(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        UserEnrolledLecture relation = new UserEnrolledLecture();
        relation.setLecture(lecture);
        relation.setUser(user);
        userEnrolledLectureRepository.save(relation);
        return successJsonResponse();
    }

    public JsonView delete(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkDeleteRight(user, lecture);
        lecture.setDeleteState();
        lectureRepository.save(lecture);
        return successJsonResponse();
    }
}
