package org.next.lms.tag.control;

import org.hibernate.exception.ConstraintViolationException;
import org.next.config.AppConfig;
import org.next.infra.repository.ContentRepository;
import org.next.infra.result.Result;
import org.next.lms.content.domain.Content;
import org.next.lms.tag.domain.Tag;
import org.next.lms.tag.domain.TagUpdateDto;
import org.next.infra.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    TagDao tagDao;

    public Result find(String keyword) {
        return success(tagDao.getTags(keyword));
    }

    public Result updateContent(TagUpdateDto tagUpdateDto) {
        Content content = assureNotNull(contentRepository.findOne(tagUpdateDto.getId()));
        content.getTags().stream()
                .filter(tag -> tagUpdateDto.getTags().stream()
                        .filter(tagString -> !tag.getText().equals(tagString)).findAny().isPresent())
                .forEach(tag -> tagRepository.delete(tag));
        tagUpdateDto.getTags().stream()
                .filter(tagString -> !content.getTags().stream().filter(tag -> tag.getText().equals(tagString)).findAny().isPresent())
                .forEach(tagString -> {
                    Tag tagObject = new Tag();
                    tagObject.setContent(content);
                    tagObject.setText(tagString);
                    content.getTags().add(tagObject);
                    tagRepository.save(tagObject);
                });
        return success();
    }
}
