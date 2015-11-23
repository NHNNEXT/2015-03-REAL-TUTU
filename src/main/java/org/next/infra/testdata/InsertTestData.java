package org.next.infra.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.LectureRepository;
import org.next.lms.content.Content;
import org.next.lms.content.service.ContentService;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.repository.ContentTypeRepository;
import org.next.lms.lecture.service.LectureService;
import org.next.lms.user.User;
import org.next.lms.user.repository.UserRepository;
import org.next.lms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.next.infra.util.CommonUtils.random;

@Component
public class InsertTestData {

    public static final String TEST_DATA_PATH = "./src/main/resources/testdata/";

    @Autowired
    private UserService userService;


    @Autowired
    private LectureService lectureService;


    @Autowired
    private ContentRepository contentRepository;


    @PostConstruct
    public void insertData() throws IOException {

        // 유저
        List<User> users = JsonDataToList(TEST_DATA_PATH + "users.json", User.class);
        users.forEach(userService::register);

        // 수업
        List<Lecture> lectures = JsonDataToList(TEST_DATA_PATH + "lectures.json", Lecture.class);
        lectures.forEach(lecture -> {
            lecture.getUserGroups().forEach(userGroup -> userGroup.setLecture(lecture));
            lecture.getContentTypes().forEach(contentType -> contentType.setLecture(lecture));
            lectureService.save(lecture, users.get(random(users.size() - 1)));
        });

        // 컨텐츠
        List<Content> contents = JsonDataToList(TEST_DATA_PATH + "contents.json", Content.class);

        lectures.forEach(lecture -> {
            contents.forEach(content -> {
                content.setLecture(lecture);
                content.setWriter(users.get(random(users.size() - 1)));
                contentRepository.save(content);
            });
        });
    }

    private <T> List<T> JsonDataToList(String jsonFilePath, Class type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), mapper.getTypeFactory().constructCollectionType(List.class, type));
    }
}
