package org.next.lms.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.next.lms.lecture.dto.UserGroupDto;
import org.next.lms.user.User;

@Getter
@Setter
public class UserSummaryDto {

    public UserSummaryDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileUrl = user.getProfileUrl();
        this.email = user.getEmail();
        this.major = user.getMajor();
        this.introduce = user.getIntroduce();
    }

    private UserGroupDto group;

    private final Long id;

    private final String email;

    private final String name;

    private final String profileUrl;

    private final String major;

    private final String introduce;

}
