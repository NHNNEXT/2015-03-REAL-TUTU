package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.lecture.domain.UserGroup;

@Getter
public class UserGroupDto {

    private final Long id;
    private final boolean defaultGroup;
    private final String name;

    public UserGroupDto(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.defaultGroup = userGroup.isDefaultGroup();
        this.name = userGroup.getName();
    }
}
