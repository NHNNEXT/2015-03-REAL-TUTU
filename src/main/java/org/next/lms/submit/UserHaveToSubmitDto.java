package org.next.lms.submit;

import lombok.Getter;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserHaveToSubmitDto {

    private final boolean done;
    private final UserSummaryDto user;
    private final Long id;

    public UserHaveToSubmitDto(UserHaveToSubmit userHaveToSubmit) {
        this.id = userHaveToSubmit.getId();
        this.done = userHaveToSubmit.isDone();
        this.user = new UserSummaryDto(userHaveToSubmit.getUser());
    }
}
