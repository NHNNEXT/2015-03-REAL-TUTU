package org.next.lms.search.control;

import com.mysema.query.jpa.impl.JPAQuery;
import org.next.config.AppConfig;
import org.next.infra.repository.UserRepository;
import org.next.infra.result.Result;
import org.next.lms.content.domain.QContent;
import org.next.lms.content.domain.dto.ContentSummaryDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.QLecture;
import org.next.lms.lecture.domain.QUserEnrolledLecture;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.util.CommonUtils.getLikeExpression;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/mylectures")
    public Result searchInMyLectureContents(String keyword, @Logged User user) {
        QContent qContent = QContent.content;
        QLecture qLecture = QLecture.lecture;
        QUserEnrolledLecture qUserEnrolledLecture = QUserEnrolledLecture.userEnrolledLecture;
        List<Lecture> enrolledLectures = new JPAQuery(entityManager).from(qUserEnrolledLecture).innerJoin(qUserEnrolledLecture.lecture, qLecture).where(qUserEnrolledLecture.user.eq(user)).list(qLecture);
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qContent).where(qContent.lecture.in(enrolledLectures)).where(qContent.title.like(getLikeExpression(keyword)).or(qContent.body.like(getLikeExpression(keyword)))).limit(AppConfig.pageSize);
        return Result.success(query.list(qContent).stream().map(ContentSummaryDto::new).collect(Collectors.toList()));
    }

}
