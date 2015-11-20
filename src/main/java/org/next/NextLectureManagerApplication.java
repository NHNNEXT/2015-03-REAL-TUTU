package org.next;

import org.next.lms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NextLectureManagerApplication {

    @Autowired
    UserService userService;


    public static void main(String[] args) {
        SpringApplication.run(NextLectureManagerApplication.class, args);


    }
}
