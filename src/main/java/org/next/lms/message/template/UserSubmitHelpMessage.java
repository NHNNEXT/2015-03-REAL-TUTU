package org.next.lms.message.template;

import org.next.infra.util.CommonUtils;
import org.next.lms.content.domain.Content;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.ImmutableMessageTemplate;
import org.next.lms.user.domain.User;

public class UserSubmitHelpMessage extends ImmutableMessageTemplate {

    private static final String messageTemplate = "%s님이 %s에 도움말 썼습니다.";

    private static final String singleEventUrlTemplate = "/게시물/%d";

    private Content content;
    private User writer;

    public UserSubmitHelpMessage(Content content, User writer) {
        this.content = content;
        this.writer = writer;
    }

    @Override
    protected String getMessageString() {
        return String.format(messageTemplate, writer.getName(), CommonUtils.ellipsis(content.getTitle(), 30));
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
