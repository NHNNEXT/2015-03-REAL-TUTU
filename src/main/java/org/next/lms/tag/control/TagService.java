package org.next.lms.tag.control;

import org.next.infra.repository.ContentRepository;
import org.next.infra.result.Result;
import org.next.lms.content.domain.Content;
import org.next.lms.tag.domain.TagDto;
import org.next.lms.tag.domain.TagUpdateDto;
import org.next.infra.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;


@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ContentRepository contentRepository;

    public Result find(String keyword) {
        return success(tagRepository.findDistinctTextByTextContaining(keyword).stream().map(TagDto::new).collect(Collectors.toList()));
    }

    public Result updateContent(TagUpdateDto tagUpdateDto) {
        Content content = assureNotNull(contentRepository.findOne(tagUpdateDto.getId()));
        tagRepository.deleteByContentId(tagUpdateDto.getId());
        tagUpdateDto.getTags().forEach(tag -> {
            tag.setContent(content);
            tagRepository.save(tag);
        });
        return success();
    }
}