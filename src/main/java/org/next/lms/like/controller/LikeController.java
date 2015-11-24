package org.next.lms.like.controller;

import org.next.infra.repository.*;
import org.next.infra.view.JsonView;
import org.next.infra.reponse.ResponseCode;
import org.next.lms.message.MessageService;
import org.next.lms.message.template.UserLikesContentTemplate;
import org.next.lms.message.template.UserLikesReplyTemplate;
import org.next.lms.user.User;
import org.next.lms.like.UserLikesContent;
import org.next.lms.like.UserLikesLecture;
import org.next.lms.like.UserLikesReply;
import org.next.lms.like.repository.UserLikesContentRepository;
import org.next.lms.like.repository.UserLikesLectureRepository;
import org.next.lms.like.repository.UserLikesReplyRepository;
import org.next.lms.user.inject.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    @Autowired
    UserLikesContentRepository userLikesContentRepository;

    @Autowired
    UserLikesLectureRepository userLikesLectureRepository;

    @Autowired
    UserLikesReplyRepository userLikesReplyRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    MessageService messageService;

    @RequestMapping(method = RequestMethod.POST)
    public JsonView like(Integer type, Long id, @Logged User user) {
        if (type == null || id == null)
            return new JsonView(ResponseCode.WRONG_ACCESS);

        if (Objects.equals(type, ResponseCode.Like.CONTENT))
            return likeContent(id, user);
        if (Objects.equals(type, ResponseCode.Like.LECTURE))
            return likeLecture(id, user);
        if (Objects.equals(type, ResponseCode.Like.REPLY))
            return likeReply(id, user);

        return new JsonView(ResponseCode.WRONG_ACCESS);
    }

    private JsonView likeContent(Long id, User user) {
        UserLikesContent relation = new UserLikesContent(user, contentRepository.findOne(id));
        if (user.getLikeContents().contains(relation)) {
            user.getLikeContents().forEach(like -> {
                if (Objects.equals(like.getContent().getId(), id))
                    userLikesContentRepository.delete(like);
            });
            return new JsonView(ResponseCode.Like.REMOVE);
        }
        userLikesContentRepository.save(relation);
        messageService.newMessage(relation.getContent().getWriter(), new UserLikesContentTemplate(relation.getContent(), user, relation.getContent().getUserLikesContents().size()));
        return new JsonView(ResponseCode.Like.ADD);
    }

    private JsonView likeLecture(Long id, User user) {
        UserLikesLecture relation = new UserLikesLecture(user, lectureRepository.findOne(id));
        if (user.getLikeLectures().contains(relation)) {
            user.getLikeLectures().forEach(like -> {
                if (Objects.equals(like.getLecture().getId(), id))
                    userLikesLectureRepository.delete(like);
            });
            return new JsonView(ResponseCode.Like.REMOVE);
        }
        userLikesLectureRepository.save(relation);
        return new JsonView(ResponseCode.Like.ADD);
    }

    private JsonView likeReply(Long id, User user) {
        UserLikesReply relation = new UserLikesReply(user, replyRepository.findOne(id));
        if (user.getLikeReplies().contains(relation)) {
            user.getLikeReplies().forEach(like -> {
                if (Objects.equals(like.getReply().getId(), id))
                    userLikesReplyRepository.delete(like);
            });
            return new JsonView(ResponseCode.Like.REMOVE);
        }
        userLikesReplyRepository.save(relation);
        messageService.newMessage(relation.getReply().getWriter(), new UserLikesReplyTemplate(relation.getReply(), user, relation.getReply().getUserLikesReplies().size()));
        return new JsonView(ResponseCode.Like.ADD);
    }
}
