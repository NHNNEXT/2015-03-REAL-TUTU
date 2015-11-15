package org.next.infra.relation.controller;

import org.next.infra.util.SessionUtil;
import org.next.infra.repository.*;
import org.next.infra.view.JsonView;
import org.next.infra.reponse.ResponseCode;
import org.next.lms.user.User;
import org.next.infra.relation.UserLikesContent;
import org.next.infra.relation.UserLikesLecture;
import org.next.infra.relation.UserLikesReply;
import org.next.infra.relation.repository.UserLikesContentRepository;
import org.next.infra.relation.repository.UserLikesLectureRepository;
import org.next.infra.relation.repository.UserLikesReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    SessionUtil sessionUtil;


    @RequestMapping(method = RequestMethod.POST)
    public JsonView like(Integer type, Long id, HttpSession session) {
        if (type == null || id == null)
            return new JsonView(ResponseCode.WRONG_ACCESS);
        User user = sessionUtil.getLoggedUser(session);

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
        return new JsonView(ResponseCode.Like.ADD);
    }
}
