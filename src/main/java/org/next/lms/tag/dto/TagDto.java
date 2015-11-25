package org.next.lms.tag.dto;

import lombok.Getter;
import org.next.lms.tag.Tag;

@Getter
public class TagDto {
    private final String text;

    public TagDto(Tag tag) {
        this.text = tag.getText();
    }
}
