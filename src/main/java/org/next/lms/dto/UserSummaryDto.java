package org.next.lms.dto;

import lombok.Getter;
import org.next.infra.user.domain.UserInfo;

@Getter
public class UserSummaryDto {

    public UserSummaryDto(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.name = userInfo.getName();
        this.profileUrl = userInfo.getProfileUrl();
        this.email = userInfo.getLoginAccount().getEmailId();
    }

    private Long id;

    private String email;

    private String name;

    private String profileUrl;


}