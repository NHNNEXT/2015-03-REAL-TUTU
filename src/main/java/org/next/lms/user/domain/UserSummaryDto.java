package org.next.lms.user.domain;

import lombok.Getter;
import lombok.Setter;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.lecture.domain.dto.UserGroupDto;

@Getter
@Setter
public class UserSummaryDto {

    private UserGroupDto group;
    private final Long id;
    private final String email;
    private final String name;
    private final String profileUrl;
    private final String major;
    private final String introduce;

    public UserSummaryDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileUrl = user.getProfileUrl();
        this.email = user.getEmail();
        this.major = user.getMajor();
        this.introduce = user.getIntroduce();
    }

    public UserSummaryDto(UserEnrolledLecture userEnrolledLecture) {
        this(userEnrolledLecture.getUser());
        if (userEnrolledLecture.getUserGroup() != null) {
            this.group = new UserGroupDto(userEnrolledLecture.getUserGroup());
            return;
        }
        this.group = new UserGroupDto(userEnrolledLecture.getLecture().getUserGroups().stream().filter(UserGroup::getDefaultGroup).findAny().get());
    }
}
