package org.next.infra.testdata;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.LectureRepository;
import org.next.lms.content.Content;
import org.next.lms.content.dto.ContentParameterDto;
import org.next.lms.content.dto.Contents;
import org.next.lms.content.service.ContentService;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.repository.ContentTypeRepository;
import org.next.lms.lecture.service.LectureService;
import org.next.lms.user.User;
import org.next.lms.user.repository.UserRepository;
import org.next.lms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.Math.random;
import static java.lang.Math.toIntExact;

@Component
public class InsertTestData {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureService lectureService;

    @Autowired
    ContentService contentService;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ContentTypeRepository contentTypeRepository;


    @PostConstruct
    public void insertData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // 유저
        List<User> users = mapper.readValue(new File("./src/main/resources/testdata/users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        users.forEach(userService::register);

        // 수업
        List<Lecture> lectures = mapper.readValue(new File("./src/main/resources/testdata/lectures.json"), mapper.getTypeFactory().constructCollectionType(List.class, Lecture.class));
        lectures.forEach(lecture -> {
            lecture.getUserGroups().forEach(userGroup -> userGroup.setLecture(lecture));
            lecture.getContentTypes().forEach(contentType -> contentType.setLecture(lecture));
            lectureService.save(lecture, users.get(random(users.size() - 1)));
        });

        // 컨텐츠
        List<Content> contents = mapper.readValue(new File("./src/main/resources/testdata/contents.json"), mapper.getTypeFactory().constructCollectionType(List.class, Content.class));
        lectures.forEach(lecture -> {
            contents.forEach(content -> {
                content.setLecture(lecture);
                content.setWriter(users.get(random(users.size() - 1)));
                contentRepository.save(content);
            });
        });
    }


    private Integer random(Integer i) {
        return toIntExact(Math.round((Math.random() * i)));
    }

}
