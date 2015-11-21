package org.next.infra.testdata;

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
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    @Value("classpath:testdata/lectures.json")
    private Resource lectures;

    @Value("classpath:testdata/users.json")
    private Resource users;

    @Value("classpath:testdata/contents.json")
    private Resource contents;

    private ObjectMapper mapper;

    @PostConstruct
    public void insertData() throws IOException {
        mapper = new ObjectMapper();

        // 유저
        List<User> users = mapper.readValue(toString(this.users), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        users.forEach(userService::register);

        // 수업
        List<Lecture> lectures = mapper.readValue(toString(this.lectures), mapper.getTypeFactory().constructCollectionType(List.class, Lecture.class));
        lectures.forEach(lecture -> {
            lecture.getUserGroups().forEach(userGroup -> userGroup.setLecture(lecture));
            lecture.getContentTypes().forEach(contentType -> contentType.setLecture(lecture));
            lectureService.save(lecture, users.get(random(users.size() - 1)));
        });

        // 컨텐츠
        List<ContentParameterDto> contentParameterDtoList = mapper.readValue(toString(this.contents), mapper.getTypeFactory().constructCollectionType(List.class, ContentParameterDto.class));
        lectures.forEach(lecture -> {
            contentParameterDtoList.forEach(contentParameterDto -> {
                Content content = contentParameterDto.getTypeDeclaredContent(contentTypeRepository);
                content.setLecture(lecture);
                content.setWriter(users.get(random(users.size() - 1)));
                contentRepository.save(content);
            });
        });
    }


    private String toString(Resource lectures) throws IOException {
        return IOUtils.toString(lectures.getInputStream(), "utf-8");
    }

    private Integer random(Integer i) {
        return toIntExact(Math.round((Math.random() * i)));
    }

}
