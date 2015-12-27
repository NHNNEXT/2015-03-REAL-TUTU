package org.next.lms.submit;

import lombok.Getter;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserHaveToSubmitDto {

    private final Boolean done;
    private final UserSummaryDto user;
    private final List<SubmitDto> submits;
    private final Long id;

    public UserHaveToSubmitDto(UserHaveToSubmit userHaveToSubmit) {
        this.id = userHaveToSubmit.getId();
        this.done = userHaveToSubmit.getDone();
        this.user = new UserSummaryDto(userHaveToSubmit.getUser());
        this.submits = userHaveToSubmit.getSubmits().stream().map(SubmitDto::new).collect(Collectors.toList());
    }
}
