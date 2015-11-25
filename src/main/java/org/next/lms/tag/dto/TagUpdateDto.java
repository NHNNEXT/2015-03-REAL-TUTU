package org.next.lms.tag.dto;

import lombok.Getter;
import org.next.lms.tag.Tag;

import java.util.List;

@Getter
public class TagUpdateDto {
    private List<Tag> tags;
    private Long id;
}
