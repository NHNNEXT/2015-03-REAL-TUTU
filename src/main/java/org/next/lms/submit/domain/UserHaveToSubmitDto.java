package org.next.lms.submit.domain;

import lombok.Getter;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.Date;
import java.util.List;

@Getter
public class UserHaveToSubmitDto {

    private final boolean done;
    private final UserSummaryDto user;
    private final Long id;
    private final SubmitState submitState;

    public UserHaveToSubmitDto(UserHaveToSubmit userHaveToSubmit) {
        this.id = userHaveToSubmit.getId();
        this.done = userHaveToSubmit.isDone();
        this.user = new UserSummaryDto(userHaveToSubmit.getUser());
        List<Submit> submitList = userHaveToSubmit.getSubmitOf(userHaveToSubmit.getUser());
        Date deadline = userHaveToSubmit.getContent().getEndTime();
        if (submitList.size() == 0) {
            submitState = SubmitState.NO_SUBMIT;
            return;
        }
        if (submitList.stream().filter(submit -> submit.getUpdateTime().before(deadline)).findAny().isPresent()) {
            submitState = SubmitState.SUBMIT_IN_TIME;
            return;
        }
        submitState = SubmitState.SUBMIT_DELAYED;
    }
}
