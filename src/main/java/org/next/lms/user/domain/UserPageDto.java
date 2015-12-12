package org.next.lms.user.domain;

import lombok.Getter;
import lombok.Setter;
import org.next.lms.content.domain.dto.ContentSummaryDto;
import org.next.lms.lecture.domain.dto.LectureSummaryDto;
import org.next.lms.lecture.domain.dto.UserGroupDto;
import org.next.lms.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserPageDto {

    private UserGroupDto group;
    private final List<ContentSummaryDto> writeContents;
    private final List<ContentSummaryDto> likeContents;
    private final List<LectureSummaryDto> lectures;
    private final Long id;
    private final String email;
    private final String name;
    private final String profileUrl;
    private final String major;
    private final String introduce;

    public UserPageDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileUrl = user.getProfileUrl();
        this.email = user.getEmail();
        this.major = user.getMajor();
        this.introduce = user.getIntroduce();
        this.lectures = user.getEnrolledLectures().stream().map(relation -> new LectureSummaryDto(relation.getLecture())).collect(Collectors.toList());
        this.writeContents = user.getContents().stream().map(ContentSummaryDto::new).collect(Collectors.toList());
        this.likeContents = user.getLikeContents().stream().map(likeContent -> new ContentSummaryDto(likeContent.getContent())).collect(Collectors.toList());
    }
}
