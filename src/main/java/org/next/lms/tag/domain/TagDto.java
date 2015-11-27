package org.next.lms.tag.domain;

import lombok.Getter;
import org.next.lms.tag.domain.Tag;

@Getter
public class TagDto {
    private final String text;

    public TagDto(Tag tag) {
        this.text = tag.getText();
    }
}
