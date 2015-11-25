package org.next.lms.tag.service;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.ContentRepository;
import org.next.infra.view.JsonView;
import org.next.lms.content.Content;
import org.next.lms.tag.dto.TagDto;
import org.next.lms.tag.dto.TagUpdateDto;
import org.next.lms.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;


@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;


    @Autowired
    ContentRepository contentRepository;

    public JsonView find(String keyword) {
        return new JsonView(ResponseCode.SUCCESS, tagRepository.findByTextContaining(keyword).stream().map(TagDto::new).collect(Collectors.toList()));
    }

    @Transactional
    public JsonView updateContent(TagUpdateDto tagUpdateDto) {
        Content content = contentRepository.findOne(tagUpdateDto.getId());
        tagRepository.deleteByContentId(tagUpdateDto.getId());
        tagUpdateDto.getTags().forEach(tag -> {
            tag.setContent(content);
            tagRepository.save(tag);
        });
        return JsonView.successJsonResponse();
    }
}
