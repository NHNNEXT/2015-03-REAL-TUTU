package org.next.lms.content.service;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.util.SessionUtil;
import org.next.infra.view.JsonView;
import org.next.lms.content.dto.Contents;
import org.next.lms.user.User;
import org.next.lms.lecture.auth.LectureAuth;
import org.next.lms.content.Content;
import org.next.lms.content.dto.ContentDto;
import org.next.lms.content.dto.ContentSummaryDto;
import org.next.lms.lecture.Lecture;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.LectureRepository;
import org.next.lms.content.auth.ContentAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.view.JsonView.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
public class ContentService {


    @Autowired
    ContentRepository contentRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    ContentAuth contentAuthority;

    @Autowired
    LectureAuth lectureAuthority;

    @Autowired
    private SessionUtil sessionUtil;


    public ContentDto getDtoById(Long id) {
        Content content = assureNotNull(contentRepository.findOne(id));
        content.hits();
        contentRepository.save(content);
        return new ContentDto(content);
    }

    public JsonView save(Content content, HttpSession session, Long lectureId) {
        if (lectureId == null)
            return new JsonView(ResponseCode.WRONG_ACCESS);

        User user = sessionUtil.getLoggedUser((session));
        Lecture lecture = lectureRepository.findOne(lectureId);
        lectureAuthority.checkUpdateRight(lecture, user);

        content.setWriter(user);
        content.setWriteDate(new Date());
        content.setLecture(lecture);

        contentRepository.save(content);
        return successJsonResponse(new ContentDto(content));
    }

    public JsonView update(Content content, HttpSession session) {
        User user = sessionUtil.getLoggedUser((session));
        Content fromDB = contentRepository.findOne(content.getId());
        contentAuthority.checkUpdateRight(fromDB, user);
        fromDB.update(content);
        contentRepository.save(fromDB);
        return successJsonResponse();
    }

    public List<ContentSummaryDto> getList() {
        return contentRepository.findAll().stream().map(ContentSummaryDto::new).collect(Collectors.toList());
    }

    public JsonView delete(Long id, HttpSession session) {
        User user = sessionUtil.getLoggedUser((session));
        Content content = assureNotNull(contentRepository.findOne(id));
        contentAuthority.checkDeleteRight(content, user);
        content.setDeleteState();
        contentRepository.save(content);
        return successJsonResponse();
    }


    public JsonView listSave(Contents contents, HttpSession session) {
        User user = sessionUtil.getLoggedUser((session));
        Lecture lecture = lectureRepository.findOne(contents.getLectureId());
        lectureAuthority.checkUpdateRight(lecture, user);
        contents.getContents().forEach(content->{
            content.setWriter(user);
            content.setWriteDate(new Date());
            content.setLecture(lecture);
            contentRepository.save(content);
        });
        return successJsonResponse();
    }
}
