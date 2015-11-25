package org.next.infra.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.next.infra.repository.ContentRepository;
import org.next.lms.content.Content;
import org.next.lms.content.ContentType;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.UserEnrolledLecture;
import org.next.lms.lecture.auth.ApprovalState;
import org.next.lms.lecture.service.LectureService;
import org.next.lms.like.repository.UserEnrolledLectureRepository;
import org.next.lms.tag.Tag;
import org.next.lms.tag.repository.TagRepository;
import org.next.lms.user.User;
import org.next.lms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertTestData {

    public static final String TEST_DATA_PATH = "./src/main/resources/testdata/";

    @Autowired
    private UserService userService;


    @Autowired
    private LectureService lectureService;


    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserEnrolledLectureRepository userEnrolledLectureRepository;


    @PostConstruct
    public void insertData() {

        // 유저
        List<User> users = JsonDataToList(TEST_DATA_PATH + "users.json", User.class);
        users.forEach(userService::register);
        Loop<User> userLoop = new Loop<>(users);

        // 수업
        List<Lecture> lectures = JsonDataToList(TEST_DATA_PATH + "lectures.json", Lecture.class);
        Loop<Lecture> lectureLoop = new Loop<>(lectures);
        lectures.forEach(lecture -> {
            lecture.getUserGroups().forEach(userGroup -> userGroup.setLecture(lecture));
            lecture.getContentTypes().forEach(contentType -> contentType.setLecture(lecture));
            lectureService.save(lecture, userLoop.next());
        });

        // 수업 등록
        users.forEach(user->{
            UserEnrolledLecture userEnrolledLecture = new UserEnrolledLecture();
            userEnrolledLecture.setUser(user);
            userEnrolledLecture.setApprovalState(ApprovalState.OK);
            userEnrolledLecture.setLecture(lectureLoop.next());
            userEnrolledLectureRepository.save(userEnrolledLecture);
            userEnrolledLecture = new UserEnrolledLecture();
            userEnrolledLecture.setApprovalState(ApprovalState.WAITING_APPROVAL);
            userEnrolledLecture.setUser(user);
            userEnrolledLecture.setLecture(lectureLoop.next());
            userEnrolledLectureRepository.save(userEnrolledLecture);
            userEnrolledLecture = new UserEnrolledLecture();
            userEnrolledLecture.setApprovalState(ApprovalState.REJECT);
            userEnrolledLecture.setUser(user);
            userEnrolledLecture.setLecture(lectureLoop.next());
            userEnrolledLectureRepository.save(userEnrolledLecture);
        });


        // 태그
        List<Tag> tags = JsonDataToList(TEST_DATA_PATH + "tags.json", Tag.class);
        Loop<Tag> tagLoop = new Loop<>(tags);

        // 컨텐츠
        lectures.forEach(lecture -> {
            List<Content> contents = JsonDataToList(TEST_DATA_PATH + "contents.json", Content.class);
            Loop<ContentType> contentTypeIterator = new Loop<>(lecture.getContentTypes());
            contents.forEach(content -> {
                content.setLecture(lecture);
                content.setWriter(userLoop.next());
                content.setType(contentTypeIterator.next());
                contentRepository.save(content);
                Tag tagData = tagLoop.next();
                Tag tag = new Tag();
                tag.setText(tagData.getText());
                tag.setContent(content);
                tagRepository.save(tag);
                tagData = tagLoop.next();
                tag = new Tag();
                tag.setText(tagData.getText());
                tag.setContent(content);
                tagRepository.save(tag);
            });
        });


    }

    private <T> List<T> JsonDataToList(String jsonFilePath, Class type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(jsonFilePath), mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
