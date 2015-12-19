package org.next.lms.submit;

import lombok.Getter;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.dto.ContentSummaryDto;
import org.next.lms.lecture.domain.dto.LectureSummaryDto;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserHaveToSubmitContentDto {

    private final Boolean done;
    private final Long id;
    private final ContentSummaryDto content;

    public UserHaveToSubmitContentDto(UserHaveToSubmit userHaveToSubmit) {
        this.content = new ContentSummaryDto(userHaveToSubmit.getContent());
        this.id = userHaveToSubmit.getId();
        this.done = userHaveToSubmit.getDone();
    }
}
