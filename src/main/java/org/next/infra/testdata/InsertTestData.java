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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertTestData {


    @Value("classpath:testdata/users.json")
    Resource usersJson;

    @Value("classpath:testdata/contents.json")
    Resource contentsJson;

    @Value("classpath:testdata/lectures.json")
    Resource lecturesJson;

    @Value("classpath:testdata/tags.json")
    Resource tagsJson;


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
        List<User> users = JsonDataToList(usersJson, User.class);
        users.forEach(userService::register);
        Loop<User> userLoop = new Loop<>(users);

        // 수업
        List<Lecture> lectures = JsonDataToList(lecturesJson, Lecture.class);
        Loop<Lecture> lectureLoop = new Loop<>(lectures);
        lectures.forEach(lecture -> {
            lecture.getUserGroups().forEach(userGroup -> userGroup.setLecture(lecture));
            lecture.getContentTypes().forEach(contentType -> contentType.setLecture(lecture));
            lectureService.save(lecture, userLoop.next());
        });

        // 수업 등록
        users.forEach(user -> {
            UserEnrolledLecture userEnrolledLecture = lectureService.getEnrollRelation(user, lectureLoop.next());
            userEnrolledLecture.setApprovalState(ApprovalState.OK);
            userEnrolledLecture.setSideMenu(true);
            userEnrolledLectureRepository.save(userEnrolledLecture);
            userEnrolledLecture = lectureService.getEnrollRelation(user, lectureLoop.next());
            userEnrolledLecture.setApprovalState(ApprovalState.WAITING_APPROVAL);
            userEnrolledLectureRepository.save(userEnrolledLecture);
            userEnrolledLecture = lectureService.getEnrollRelation(user, lectureLoop.next());
            userEnrolledLecture.setApprovalState(ApprovalState.REJECT);
            userEnrolledLectureRepository.save(userEnrolledLecture);
        });


        // 태그
        List<Tag> tags = JsonDataToList(tagsJson, Tag.class);
        Loop<Tag> tagLoop = new Loop<>(tags);

        // 컨텐츠
        lectures.forEach(lecture -> {
            List<Content> contents = JsonDataToList(contentsJson, Content.class);
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

    private <T> List<T> JsonDataToList(Resource json, Class type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json.getFile(), mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

}
