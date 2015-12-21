package org.next.lms.message.template;

import org.next.infra.util.CommonUtils;
import org.next.infra.util.HanGuelUtil;
import org.next.lms.content.domain.Content;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.ImmutableMessageTemplate;

import static org.next.infra.util.HanGuelUtil.josaWhenEllipsisApplied;

public class UserSubmitMissionToScoreGraderMessage extends ImmutableMessageTemplate {

    private static final String messageTemplate = "'%s' %s 제출한 학생이 있습니다.";

    private static final String singleEventUrlTemplate = "/content/%d";

    private Content content;

    public UserSubmitMissionToScoreGraderMessage(Content content) {
        this.content = content;
    }

    @Override
    protected String getMessageString() {
        return String.format(messageTemplate, CommonUtils.ellipsis(content.getTitle(), 30), josaWhenEllipsisApplied(content.getTitle(), HanGuelUtil.JosaType.을를, 30));
    }

    @Override
    public MessageType messageType() {
        return MessageType.USER_SUBMIT_MISSION_TO_SCORE_GRADER;
    }

    @Override
    public Long pkAtBelongTypeTable() {
        return content.getId();
    }

    @Override
    public Boolean needToExcludeEventEmitUser() {
        return true;
    }

    @Override
    public String getUrl() {
        return String.format(singleEventUrlTemplate, content.getId());
    }
}
