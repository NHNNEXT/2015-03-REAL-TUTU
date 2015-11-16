package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.infra.relation.UserEnrolledLecture;
import org.next.infra.relation.UserLikesLecture;
import org.next.lms.content.Content;
import org.next.lms.content.dto.ContentSummaryDto;
import org.next.lms.lecture.ContentType;
import org.next.lms.lecture.UserGroup;
import org.next.lms.user.User;
import org.next.lms.user.dto.UserDto;
import org.next.lms.user.dto.UserSummaryDto;
import org.next.lms.lecture.Lecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
public class LectureDto {

    public LectureDto(Lecture lecture) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.registerPolicyType = lecture.getRegisterPolicyType();

        this.hostUser = new UserSummaryDto(lecture.getHostUser());

        this.contents = lecture.getContents().stream().map(ContentSummaryDto::new).collect(Collectors.toList());
        this.likes = lecture.getLikes().stream().map(like -> like.getId()).collect(Collectors.toList());
        this.contentTypes = lecture.getContentTypes().stream().map(ContentTypeDto::new).collect(Collectors.toList());
        this.users = lecture.getUserEnrolledLectures().stream().map(relation -> new UserSummaryDto(relation.getUser())).collect(Collectors.toList());
        this.userGroups = lecture.getUserGroups().stream().map(UserGroupDto::new).collect(Collectors.toList());
    }

    private UserSummaryDto hostUser;

    private List<Long> likes;

    private List<UserGroupDto> userGroups;

    private List<ContentTypeDto> contentTypes;

    private List<UserSummaryDto> users;

    private List<ContentSummaryDto> contents;

    private Long id;

    private String name;

    private Integer majorType;

    private Integer registerPolicyType;


}
