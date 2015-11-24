package org.next.lms.tag.dto;

import lombok.Getter;
import org.next.lms.tag.Tag;

@Getter
public class TagDto {
    private final String tag;
    private final Long id;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.tag = tag.getTag();
    }
}
