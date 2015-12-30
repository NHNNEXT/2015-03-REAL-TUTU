package org.next.lms.content.domain.dto;

import lombok.Getter;
import org.next.infra.uploadfile.dto.UploadedFileDto;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.like.domain.UserLikesContent;
import org.next.lms.submit.domain.UserHaveToSubmitDto;
import org.next.lms.tag.domain.Tag;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContentDto {

    private final List<Long> likes;
    private final String lectureName;
    private final Long hits;
    private final Integer repliesSize;
    private final UserSummaryDto writer;
    private final ContentGroupDto contentGroup;
    private final Long lectureId;
    private final Long id;
    private final String title;
    private final String body;
    private final Date writeDate;
    private final Date startTime;
    private final Date endTime;
    private final List<String> tags;

    private Long like;
    private List<UserHaveToSubmitDto> submitRequires;
    private List<ContentSummaryDto> relativeContents;
    private List<UploadedFileDto> attachments;

    public ContentDto(Content content, User user) {
        this.writer = new UserSummaryDto(content.getWriter());
        this.lectureName = content.getLecture().getName();
        this.lectureId = content.getLecture().getId();
        this.id = content.getId();
        this.title = content.getTitle();
        this.body = content.getBody();
        this.writeDate = content.getWriteDate();
        this.startTime = content.getStartTime();
        this.endTime = content.getEndTime();
        this.contentGroup = new ContentGroupDto(content.getContentGroup());
        this.hits = content.getHits();
        this.likes = content.getUserLikesContents().stream().map(UserLikesContent::getId).collect(Collectors.toList());
        this.tags = content.getTags().stream().map(Tag::getText).collect(Collectors.toList());
        this.repliesSize = content.getReplies().size();
        this.attachments = content.getAttachments().stream().map(UploadedFileDto::new).collect(Collectors.toList());

        this.relativeContents = new ArrayList<>();
        this.relativeContents.addAll(content.getLinkContents().stream().map(contentLinkContent -> new ContentSummaryDto(contentLinkContent.getLinkedContent())).collect(Collectors.toList()));
        this.relativeContents.addAll(content.getLinkedContents().stream().map(contentLinkedContent -> new ContentSummaryDto(contentLinkedContent.getLinkContent())).collect(Collectors.toList()));
        if (!ContentType.SUBMIT.equals(content.getContentGroup().getContentType()))
            return;
        boolean hasRightReadSubmits = user.equals(content.getLecture().getHostUser())
                || content.getContentGroup().getSubmitReadable().stream()
                .filter(userGroupCanReadSubmit -> userGroupCanReadSubmit.getUserGroup().getUserEnrolledLectures().stream()
                        .filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user)).findAny().isPresent()).findAny().isPresent();
        if (hasRightReadSubmits) {
            this.submitRequires = content.getUserHaveToSubmits().stream().map(UserHaveToSubmitDto::new).collect(Collectors.toList());
            return;
        }
        if (content.getUserHaveToSubmits().stream().filter(haveToSubmit -> haveToSubmit.getUser().equals(user)).findAny().isPresent()) {
            this.submitRequires = new ArrayList<>();
            this.submitRequires.add(new UserHaveToSubmitDto(content.getUserHaveToSubmits().stream().filter(haveToSubmit -> haveToSubmit.getUser().equals(user)).findAny().get()));
        }
    }
}
