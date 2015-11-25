package org.next.lms.tag.service;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.ContentRepository;
import org.next.infra.result.Result;
import org.next.lms.content.Content;
import org.next.lms.tag.dto.TagDto;
import org.next.lms.tag.dto.TagUpdateDto;
import org.next.lms.tag.repository.TagRepository;
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
        return success(tagRepository.findByTextContaining(keyword).stream().map(TagDto::new).collect(Collectors.toList()));
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
