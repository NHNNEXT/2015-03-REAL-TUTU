package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.lecture.UserGroup;

@Getter
public class UserGroupDto {
    public UserGroupDto(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.defaultGroup = userGroup.getDefaultGroup();
        this.name = userGroup.getName();
    }

    private final Long id;

    private final Boolean defaultGroup;

    private final String name;
}
