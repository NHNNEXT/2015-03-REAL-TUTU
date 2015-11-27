package org.next.lms.like.control;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.LectureRepository;
import org.next.infra.repository.ReplyRepository;
import org.next.infra.result.Result;
import org.next.lms.like.domain.UserLikesContent;
import org.next.lms.like.domain.UserLikesLecture;
import org.next.lms.like.domain.UserLikesReply;
import org.next.infra.repository.UserLikesContentRepository;
import org.next.infra.repository.UserLikesLectureRepository;
import org.next.infra.repository.UserLikesReplyRepository;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.domain.template.UserLikesContentMessageTemplate;
import org.next.lms.message.domain.template.UserLikesReplyMessageTemplate;
import org.next.lms.user.domain.User;
import org.next.lms.user.control.inject.Logged;
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
    public Result like(Integer type, Long id, @Logged User user) {
        if (type == null || id == null)
            return new Result(ResponseCode.WRONG_ACCESS);

        if (Objects.equals(type, ResponseCode.Like.CONTENT))
            return likeContent(id, user);
        if (Objects.equals(type, ResponseCode.Like.LECTURE))
            return likeLecture(id, user);
        if (Objects.equals(type, ResponseCode.Like.REPLY))
            return likeReply(id, user);

        return new Result(ResponseCode.WRONG_ACCESS);
    }

    private Result likeContent(Long id, User user) {
        UserLikesContent relation = new UserLikesContent(user, contentRepository.findOne(id));
        if (user.getLikeContents().contains(relation)) {
            user.getLikeContents().forEach(like -> {
                if (Objects.equals(like.getContent().getId(), id))
                    userLikesContentRepository.delete(like);
            });
            return new Result(ResponseCode.Like.REMOVE);
        }
        userLikesContentRepository.save(relation);
        messageService.newMessage(relation.getContent().getWriter(), new UserLikesContentMessageTemplate(relation.getContent(), user, relation.getContent().getUserLikesContents().size()));
        return new Result(ResponseCode.Like.ADD);
    }

    private Result likeLecture(Long id, User user) {
        UserLikesLecture relation = new UserLikesLecture(user, lectureRepository.findOne(id));
        if (user.getLikeLectures().contains(relation)) {
            user.getLikeLectures().forEach(like -> {
                if (Objects.equals(like.getLecture().getId(), id))
                    userLikesLectureRepository.delete(like);
            });
            return new Result(ResponseCode.Like.REMOVE);
        }
        userLikesLectureRepository.save(relation);
        return new Result(ResponseCode.Like.ADD);
    }

    private Result likeReply(Long id, User user) {
        UserLikesReply relation = new UserLikesReply(user, replyRepository.findOne(id));
        if (user.getLikeReplies().contains(relation)) {
            user.getLikeReplies().forEach(like -> {
                if (Objects.equals(like.getReply().getId(), id))
                    userLikesReplyRepository.delete(like);
            });
            return new Result(ResponseCode.Like.REMOVE);
        }
        userLikesReplyRepository.save(relation);
        messageService.newMessage(relation.getReply().getWriter(), new UserLikesReplyMessageTemplate(relation.getReply(), user, relation.getReply().getUserLikesReplies().size()));
        return new Result(ResponseCode.Like.ADD);
    }
}
