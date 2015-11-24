package org.next.lms.tag.service;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.view.JsonView;
import org.next.lms.tag.dto.TagDto;
import org.next.lms.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public JsonView find(String keyword) {
        return new JsonView(ResponseCode.SUCCESS, tagRepository.findByTagContaining(keyword).stream().map(TagDto::new).collect(Collectors.toList()));
    }
}
