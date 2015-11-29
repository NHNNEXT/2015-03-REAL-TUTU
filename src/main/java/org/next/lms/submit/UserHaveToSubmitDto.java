package org.next.lms.submit;

import lombok.Getter;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserHaveToSubmitDto {

    private UserSummaryDto user;

    private List<SubmitDto> submits;

    private Long id;

    public UserHaveToSubmitDto(UserHaveToSubmit userHaveToSubmit) {
        this.id = userHaveToSubmit.getId();
        this.user = new UserSummaryDto(userHaveToSubmit.getUser());
        this.submits = userHaveToSubmit.getSubmits().stream().map(SubmitDto::new).collect(Collectors.toList());
    }
}
