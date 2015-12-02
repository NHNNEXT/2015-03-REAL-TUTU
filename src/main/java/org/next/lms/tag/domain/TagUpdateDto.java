package org.next.lms.tag.domain;

import lombok.Getter;
import org.next.lms.tag.domain.Tag;

import java.util.List;

@Getter
public class TagUpdateDto {
    private List<String> tags;
    private Long id;
}
