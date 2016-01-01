package org.next.lms.letter;


import lombok.Getter;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.Date;

@Getter
public class LetterDto {
    private UserSummaryDto receiver;
    private UserSummaryDto sender;
    private Long id;
    private String message;
    private boolean checked;
    private Date date;

    public LetterDto(Letter letter) {
        this.id = letter.getId();
        this.receiver = new UserSummaryDto(letter.getReceiver());
        this.sender = new UserSummaryDto(letter.getReceiver());
        this.message = letter.getMessage();
        this.date = letter.getDate();
        this.checked = letter.isChecked();
    }
}
