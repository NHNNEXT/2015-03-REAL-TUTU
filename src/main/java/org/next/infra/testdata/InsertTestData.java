package org.next.infra.testdata;

import org.next.lms.lecture.Lecture;
import org.next.lms.user.User;
import org.next.lms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Component
public class InsertTestData {

    @Autowired
    UserService userService;

    List<User> userList = new ArrayList<>();

    @PostConstruct
    public void init() {
        insertUsers(30);
        insertLectures(30);
    }

    private void insertLectures(int length) {
        for (int i = 0; i < length; i++) {
            Lecture lecture = new Lecture();
            lecture.setHostUser(userList.get(i));
            lecture.setName("강의");
        }
    }

    private void insertUsers(Integer length) {
        for (int i = 0; i < length; i++) {
            User user = new User();
            user.setEmail("test" + i + "@test.com");
            user.setName("이름" + i);
            user.setPassword("password");
            userList.add(user);
            userService.register(user);
        }
    }

    private Integer random(Integer i) {
        return toIntExact(Math.round((Math.random() * i)));
    }

}
