package org.next.lms.lecture.service;

import org.next.infra.relation.UserInMenuLecture;
import org.next.infra.relation.repository.UserInMenuLectureRepository;
import org.next.infra.view.JsonView;
import org.next.lms.user.User;
import org.next.lms.lecture.auth.LectureAuth;
import org.next.infra.relation.UserEnrolledLecture;
import org.next.lms.lecture.dto.LectureDto;
import org.next.lms.lecture.Lecture;
import org.next.infra.repository.LectureRepository;
import org.next.infra.relation.repository.UserEnrolledLectureRepository;
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
    private UserInMenuLectureRepository userInMenuLectureRepository;


    @Autowired
    private LectureAuth lectureAuthority;


    public JsonView save(Lecture lecture, User user) {
        lecture.setHostUser(user);
        inMenu(lecture, user);
        lectureRepository.save(lecture);
        return successJsonResponse(new LectureDto(lecture));
    }

    public JsonView updateLecture(Lecture lecture, User user, String lessonString) {
        Lecture fromDB = assureNotNull(lectureRepository.findOne(lecture.getId()));
        lectureAuthority.checkUpdateRight(fromDB, user);
        fromDB.update(lecture);
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
        inMenu(lecture, user);
        return successJsonResponse();
    }

    private void inMenu(Lecture lecture, User user) {
        UserInMenuLecture inMenu = new UserInMenuLecture();
        inMenu.setLecture(lecture);
        inMenu.setUser(user);
        userInMenuLectureRepository.save(inMenu);
    }

    public JsonView delete(Long id, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(id));
        lectureAuthority.checkDeleteRight(user, lecture);
        lecture.setDeleteState();
        lectureRepository.save(lecture);
        return successJsonResponse();
    }
}
