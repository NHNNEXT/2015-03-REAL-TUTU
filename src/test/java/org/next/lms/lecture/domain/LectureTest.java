package org.next.lms.lecture.domain;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class LectureTest {

    @Autowired
    LectureRepository lectureRepository;

    @Test
    public void testInsertSameNameLecture() throws Exception {
        Lecture lecture = new Lecture();
        lecture.setMajorType(1);
        lecture.setRegisterPolicy(1);
        lecture.setName("가나다");

        Lecture lecture2 = new Lecture();
        lecture2.setMajorType(1);
        lecture2.setRegisterPolicy(1);
        lecture2.setName("가나다");

        lectureRepository.save(lecture);
            lectureRepository.save(lecture2);

    }


}