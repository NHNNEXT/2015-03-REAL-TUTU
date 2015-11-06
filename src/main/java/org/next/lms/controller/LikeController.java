package org.next.lms.controller;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.like.UserLikesContent;
import org.next.lms.like.UserLikesLecture;
import org.next.lms.like.UserLikesLesson;
import org.next.lms.like.UserLikesReply;
import org.next.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    @Autowired
    UserLikesContentRepository userLikesContentRepository;

    @Autowired
    UserLikesLessonRepository userLikesLessonRepository;

    @Autowired
    UserLikesLectureRepository userLikesLectureRepository;

    @Autowired
    UserLikesReplyRepository userLikesReplyRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserInfoBroker userInfoBroker;


    @RequestMapping(method = RequestMethod.POST)
    public CommonJsonResponse like(Integer type, Long id, HttpSession session) {
        if (type == null || id == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
        LoginAccount loginAccount = userInfoBroker.getLoginAccount(session);
        if (loginAccount == null)
            return new CommonJsonResponse(ResponseCode.LOGIN_NEEDED);

        if (Objects.equals(type, ResponseCode.Like.CONTENT))
            return likeContent(id, loginAccount);
        if (Objects.equals(type, ResponseCode.Like.LECTURE))
            return likeLecture(id, loginAccount);
        if (Objects.equals(type, ResponseCode.Like.LESSON))
            return likeLesson(id, loginAccount);
        if (Objects.equals(type, ResponseCode.Like.REPLY))
            return likeReply(id, loginAccount);

        return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
    }

    private CommonJsonResponse likeContent(Long id, LoginAccount loginAccount) {
        UserInfo user = loginAccount.getUserInfo();
        UserLikesContent relation = new UserLikesContent(user, contentRepository.findOne(id));
        if (user.getLikeContents().contains(relation)) {
            user.getLikeContents().forEach(like -> {
                if (Objects.equals(like.getContent().getId(), id))
                    userLikesContentRepository.delete(like);
            });
            return new CommonJsonResponse(ResponseCode.Like.REMOVE);
        }
        userLikesContentRepository.save(relation);
        return new CommonJsonResponse(ResponseCode.Like.ADD);
    }

    private CommonJsonResponse likeLecture(Long id, LoginAccount loginAccount) {
        UserInfo user = loginAccount.getUserInfo();
        UserLikesLecture relation = new UserLikesLecture(user, lectureRepository.findOne(id));
        if (user.getLikeLectures().contains(relation)) {
            user.getLikeLectures().forEach(like -> {
                if (Objects.equals(like.getLecture().getId(), id))
                    userLikesLectureRepository.delete(like);
            });
            return new CommonJsonResponse(ResponseCode.Like.REMOVE);
        }
        userLikesLectureRepository.save(relation);
        return new CommonJsonResponse(ResponseCode.Like.ADD);
    }

    private CommonJsonResponse likeLesson(Long id, LoginAccount loginAccount) {
        UserInfo user = loginAccount.getUserInfo();
        UserLikesLesson relation = new UserLikesLesson(user, lessonRepository.findOne(id));
        if (user.getLikeLessons().contains(relation)) {
            user.getLikeLessons().forEach(like -> {
                if (Objects.equals(like.getLesson().getId(), id))
                    userLikesLessonRepository.delete(like);
            });
            return new CommonJsonResponse(ResponseCode.Like.REMOVE);
        }
        userLikesLessonRepository.save(relation);
        return new CommonJsonResponse(ResponseCode.Like.ADD);
    }

    private CommonJsonResponse likeReply(Long id, LoginAccount loginAccount) {
        UserInfo user = loginAccount.getUserInfo();
        UserLikesReply relation = new UserLikesReply(user, replyRepository.findOne(id));
        if (user.getLikeReplies().contains(relation)) {
            user.getLikeReplies().forEach(like -> {
                if (Objects.equals(like.getReply().getId(), id))
                    userLikesReplyRepository.delete(like);
            });
            return new CommonJsonResponse(ResponseCode.Like.REMOVE);
        }
        userLikesReplyRepository.save(relation);
        return new CommonJsonResponse(ResponseCode.Like.ADD);
    }
}
