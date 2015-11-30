package org.next.infra.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.next.infra.repository.ContentRepository;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.lecture.control.LectureSaveService;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.control.LectureService;
import org.next.infra.repository.UserEnrolledLectureRepository;
import org.next.lms.tag.domain.Tag;
import org.next.infra.repository.TagRepository;
import org.next.lms.user.domain.User;
import org.next.lms.user.control.UserService;
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


    String usersJson;

    String contentsJson;

    String lecturesJson;

    String tagsJson;


    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureSaveService lectureSaveService;


    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserEnrolledLectureRepository userEnrolledLectureRepository;


    @PostConstruct
    public void insertData() {

        makeStrings();

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
            lectureSaveService.save(lecture, userLoop.next());
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

    private void makeStrings() {
        usersJson = "[\n" +
                "  {\n" +
                "    \"email\": \"test1@test.com\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"name\": \"황정민\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"email\": \"test2@test.com\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"name\": \"하태호\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"email\": \"test3@test.com\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"name\": \"가나다\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"email\": \"test4@test.com\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"name\": \"라마바\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"email\": \"test5@test.com\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"name\": \"테스트\"\n" +
                "  }\n" +
                "]";

        contentsJson = "[\n" +
                "  {\n" +
                "    \"title\": \"벡터\",\n" +
                "    \"body\": \"<p><u>기하학</u></p>\",\n" +
                "    \"endTime\": \"2015-11-03T07:00:51.399Z\",\n" +
                "    \"startTime\": \"2015-11-03T03:50:51.399Z\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"벡터\",\n" +
                "    \"body\": \"<p><u>기하학</u></p>\",\n" +
                "    \"endTime\": \"2015-11-04T07:00:51.399Z\",\n" +
                "    \"startTime\": \"2015-11-04T03:50:51.399Z\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"벡터\",\n" +
                "    \"body\": \"<p><u>기하학</u></p>\",\n" +
                "    \"endTime\": \"2015-11-05T07:00:51.399Z\",\n" +
                "    \"startTime\": \"2015-11-05T03:50:51.399Z\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"벡터\",\n" +
                "    \"body\": \"<p><u>기하학</u></p>\",\n" +
                "    \"endTime\": \"2015-11-03T07:00:51.399Z\",\n" +
                "    \"startTime\": \"2015-11-03T03:50:51.399Z\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"벡터\",\n" +
                "    \"body\": \"<p><u>기하학</u></p>\",\n" +
                "    \"endTime\": \"2015-11-04T07:00:51.399Z\",\n" +
                "    \"startTime\": \"2015-11-04T03:50:51.399Z\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"벡터\",\n" +
                "    \"body\": \"<p><u>기하학</u></p>\",\n" +
                "    \"endTime\": \"2015-11-05T07:00:51.399Z\",\n" +
                "    \"startTime\": \"2015-11-05T03:50:51.399Z\"\n" +
                "  }\n" +
                "]";

        lecturesJson = "[\n" +
                "  {\n" +
                "    \"name\": \"실전프\",\n" +
                "    \"majorType\": 1,\n" +
                "    \"registerPolicy\": 1,\n" +
                "    \"contentTypes\": [\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": true,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"수업\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"강의자료\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"질문\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": true,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"과제\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"userGroups\": [\n" +
                "      {\n" +
                "        \"defaultGroup\": false,\n" +
                "        \"name\": \"조교\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"defaultGroup\": true,\n" +
                "        \"name\": \"수강생\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"writable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        false,\n" +
                "        false,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"readable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"submitReadable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        false\n" +
                "      ]\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"선형대수\",\n" +
                "    \"majorType\": 1,\n" +
                "    \"registerPolicy\": 1,\n" +
                "    \"contentTypes\": [\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": true,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"수업\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"강의자료\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"질문\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": true,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"과제\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"userGroups\": [\n" +
                "      {\n" +
                "        \"defaultGroup\": false,\n" +
                "        \"name\": \"조교\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"defaultGroup\": true,\n" +
                "        \"name\": \"수강생\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"writable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        false,\n" +
                "        false,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"readable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"submitReadable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        false\n" +
                "      ]\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"그래픽스\",\n" +
                "    \"majorType\": 1,\n" +
                "    \"registerPolicy\": 1,\n" +
                "    \"contentTypes\": [\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": true,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"수업\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"강의자료\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"질문\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": true,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"과제\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"userGroups\": [\n" +
                "      {\n" +
                "        \"defaultGroup\": false,\n" +
                "        \"name\": \"조교\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"defaultGroup\": true,\n" +
                "        \"name\": \"수강생\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"writable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        false,\n" +
                "        false,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"readable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"submitReadable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        false\n" +
                "      ]\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"인문사회학\",\n" +
                "    \"majorType\": 1,\n" +
                "    \"registerPolicy\": 1,\n" +
                "    \"contentTypes\": [\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": true,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"수업\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"강의자료\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"질문\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": true,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"과제\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"userGroups\": [\n" +
                "      {\n" +
                "        \"defaultGroup\": false,\n" +
                "        \"name\": \"조교\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"defaultGroup\": true,\n" +
                "        \"name\": \"수강생\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"writable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        false,\n" +
                "        false,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"readable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"submitReadable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        false\n" +
                "      ]\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"자바프로그래밍\",\n" +
                "    \"majorType\": 1,\n" +
                "    \"registerPolicy\": 1,\n" +
                "    \"contentTypes\": [\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": true,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"수업\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"강의자료\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"질문\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": true,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"과제\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"userGroups\": [\n" +
                "      {\n" +
                "        \"defaultGroup\": false,\n" +
                "        \"name\": \"조교\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"defaultGroup\": true,\n" +
                "        \"name\": \"수강생\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"writable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        false,\n" +
                "        false,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"readable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"submitReadable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        false\n" +
                "      ]\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"웹서버\",\n" +
                "    \"majorType\": 1,\n" +
                "    \"registerPolicy\": 1,\n" +
                "    \"contentTypes\": [\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": true,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"수업\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"강의자료\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": false,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": false,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"질문\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"endTime\": true,\n" +
                "        \"startTime\": false,\n" +
                "        \"submit\": true,\n" +
                "        \"submitOpen\": false,\n" +
                "        \"reply\": true,\n" +
                "        \"name\": \"과제\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"userGroups\": [\n" +
                "      {\n" +
                "        \"defaultGroup\": false,\n" +
                "        \"name\": \"조교\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"defaultGroup\": true,\n" +
                "        \"name\": \"수강생\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"writable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        false,\n" +
                "        false,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"readable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"submitReadable\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        true\n" +
                "      ],\n" +
                "      [\n" +
                "        true,\n" +
                "        true,\n" +
                "        true,\n" +
                "        false\n" +
                "      ]\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        tagsJson = "[\n" +
                "  {\n" +
                "    \"text\": \"2015년 1학기\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"text\": \"자료구조\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"text\": \"질문\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"text\": \"시험\"\n" +
                "  }\n" +
                "]\n";

    }

    private <T> List<T> JsonDataToList(String json, Class type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

}
