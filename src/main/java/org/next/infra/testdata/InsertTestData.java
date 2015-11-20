package org.next.infra.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.service.LectureService;
import org.next.lms.user.User;
import org.next.lms.user.repository.UserRepository;
import org.next.lms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static java.lang.Math.toIntExact;

@Component
public class InsertTestData {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureService lectureService;

    @Value("classpath:testdata/lectures.json")
    private Resource lectures;

    @Value("classpath:testdata/users.json")
    private Resource users;

    private ObjectMapper mapper;

    @PostConstruct
    public void insertData() throws IOException {
        mapper = new ObjectMapper();
        int size = saveUsers();
        saveLectures(size);
    }

    private int saveUsers() throws IOException {
        List<User> users = mapper.readValue(toString(this.users), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        int size = users.size();
        users.forEach(userService::register);
        return size;
    }

    private void saveLectures(int size) throws IOException {
        List<Lecture> lectures = mapper.readValue(toString(this.lectures), mapper.getTypeFactory().constructCollectionType(List.class, Lecture.class));
        lectures.forEach(lecture -> {
            lecture.getUserGroups().forEach(userGroup -> userGroup.setLecture(lecture));
            lectureService.save(lecture, userRepository.findOne((long) random(size)));
        });
    }


    private String toString(Resource lectures) throws IOException {
        return IOUtils.toString(lectures.getInputStream(), "utf-8");
    }

    private Integer random(Integer i) {
        return toIntExact(Math.round((Math.random() * i)));
    }

}
