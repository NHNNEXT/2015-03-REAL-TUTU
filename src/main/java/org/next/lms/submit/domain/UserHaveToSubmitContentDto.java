package org.next.lms.submit.domain;

import lombok.Getter;
import org.next.lms.content.domain.dto.ContentSummaryDto;

@Getter
public class UserHaveToSubmitContentDto {

    private final boolean done;
    private final Long id;
    private final ContentSummaryDto content;

    public UserHaveToSubmitContentDto(UserHaveToSubmit userHaveToSubmit) {
        this.content = new ContentSummaryDto(userHaveToSubmit.getContent());
        this.id = userHaveToSubmit.getId();
        this.done = userHaveToSubmit.isDone();
    }
}
