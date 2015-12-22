package org.next.lms.submit;

import org.next.infra.repository.SubmitRepository;
import org.next.infra.repository.UploadFileRepository;
import org.next.infra.repository.UserHaveToSubmitRepository;
import org.next.infra.result.Result;
import org.next.infra.uploadfile.UploadedFile;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.domain.PackagedMessage;
import org.next.lms.message.template.UserSubmitMissionToScoreGraderMessage;
import org.next.lms.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.lms.message.domain.MessageBuilder.aMessage;

@Service
@Transactional
public class SubmitService {

    @Autowired
    private UserHaveToSubmitRepository userHaveToSubmitRepository;

    @Autowired
    private SubmitRepository submitRepository;

    @Autowired
    private SubmitAuth submitAuth;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(SubmitService.class);

    public Result save(SubmitParameterDto submitParameterDto, User user) {

        UserHaveToSubmit userHaveToSubmit = assureNotNull(userHaveToSubmitRepository.findOne(submitParameterDto.getSubmitId()));
        submitAuth.checkWriteRight(userHaveToSubmit, user, userHaveToSubmit.getContent().getContentGroup().getSubmitReadable().contains(user), userHaveToSubmit.getContent().getLecture().getHostUser().equals(user));
        Submit submit = new Submit();
        submit.setWriter(user);
        submit.setUserHaveToSubmit(userHaveToSubmit);
        submit.setWriteDate(new Date());
        submit.setBody(submitParameterDto.getBody());
        attachmentsDeclare(submitParameterDto, submit);

        submitRepository.save(submit);

        List<User> scoreGradeAbleUsers = new ArrayList<>();
        scoreGradeAbleUsers.add(userHaveToSubmit.getContent().getLecture().getHostUser());

        userHaveToSubmit.getContent().getLecture().getUserGroups().stream()
                .filter(userGroup -> userGroup.getSubmitReadable().size() > 0)
                .forEach(userGroup -> userGroup.getUserEnrolledLectures().stream()
                        .forEach(relation -> scoreGradeAbleUsers.add(relation.getUser())));

        PackagedMessage message = aMessage().from(user).to(scoreGradeAbleUsers)
                .with(new UserSubmitMissionToScoreGraderMessage(userHaveToSubmit.getContent())).packaging();

        messageService.send(message);

        return success(new SubmitDto(submit));
    }

    public Result update(SubmitParameterDto submitParameterDto, User user) {
        Submit fromDB = assureNotNull(submitRepository.findOne(submitParameterDto.getId()));
        fromDB.setWriteDate(new Date());
        fromDB.setBody(submitParameterDto.getBody());
        attachmentsDeclare(submitParameterDto, fromDB);
        submitAuth.checkUpdateRight(fromDB, user);
        return success(new SubmitDto(fromDB));
    }

    public Result delete(Long id, User user) {
        Submit submit = assureNotNull(submitRepository.findOne(id));

        submitAuth.checkDeleteRight(submit, user);
        submitRepository.delete(submit);
        return success();
    }

    private void attachmentsDeclare(SubmitParameterDto submitParameterDto, Submit submit) {
        if (submitParameterDto.getAttachments() == null)
            return;
        submit.getAttachments().stream()
                .filter(attachment -> !submitParameterDto.getAttachments().stream()
                        .filter(id -> attachment.getId().equals(id)).findAny().isPresent()).forEach(attachment -> attachment.setContent(null));

        submitParameterDto.getAttachments().forEach(id -> {
            if (submit.getAttachments().stream().filter(attachment -> attachment.getId().equals(id)).findAny().isPresent())
                return;
            UploadedFile file = uploadFileRepository.findOne(id);
            file.setSubmit(submit);
            submit.getAttachments().add(file);
            uploadFileRepository.save(file);
        });
    }
}
