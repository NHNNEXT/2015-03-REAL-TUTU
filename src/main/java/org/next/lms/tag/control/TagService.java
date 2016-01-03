package org.next.lms.tag.control;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.TagRepository;
import org.next.infra.result.Result;
import org.next.lms.content.control.ContentAuth;
import org.next.lms.content.domain.Content;
import org.next.lms.tag.domain.Tag;
import org.next.lms.tag.domain.TagUpdateDto;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
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
    private ContentAuth contentAuth;

    @PersistenceContext
    EntityManager entityManager;

    public Result find(TagDao tagDao) {
        return success(tagDao.getList(entityManager));
    }

    public Result updateContent(TagUpdateDto tagUpdateDto, User user) {
        Content content = assureNotNull(contentRepository.findOne(tagUpdateDto.getId()));
        contentAuth.checkReadRight(content, user);
        if(content.isTagBlock())
            return new Result(ResponseCode.Tag.UPDATE_BLOCKED);
        List<Tag> removedTags = content.getTags().stream().filter(tag -> !tagUpdateDto.getTags().stream().filter(tagText -> tagText.equals(tag.getText())).findAny().isPresent()).collect(Collectors.toList());
        addUpdated(tagUpdateDto, content);
        content.getTags().removeAll(removedTags);
        tagRepository.delete(removedTags);
        return success();
    }

    private void addUpdated(TagUpdateDto tagUpdateDto, Content content) {
        tagUpdateDto.getTags().stream()
                .filter(tagString -> !content.getTags().stream().filter(tag -> tag.getText().equals(tagString)).findAny().isPresent())
                .forEach(tagString -> {
                    Tag tagObject = new Tag();
                    tagObject.setContent(content);
                    tagObject.setText(tagString);
                    content.getTags().add(tagObject);
                    tagRepository.save(tagObject);
                });
    }
}
