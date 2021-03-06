package org.next.lms.submit.control;

import com.mysema.query.jpa.impl.JPAQuery;
import org.next.config.AppConfig;
import org.next.infra.repository.SubmitRepository;
import org.next.infra.repository.UserHaveToSubmitRepository;
import org.next.infra.result.Result;
import org.next.infra.uploadfile.service.AttachmentDeclareService;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.domain.PackagedMessage;
import org.next.lms.message.template.UserSubmitHelpMessage;
import org.next.lms.message.template.UserSubmitMissionToScoreGraderMessage;
import org.next.lms.submit.domain.*;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private MessageService messageService;

    @Autowired
    private AttachmentDeclareService attachmentDeclareService;

    public Result save(SubmitParameterDto submitParameterDto, User user) {
        UserHaveToSubmit userHaveToSubmit = assureNotNull(userHaveToSubmitRepository.findOne(submitParameterDto.getSubmitId()));
        submitAuth.checkWriteRight(userHaveToSubmit, user, userHaveToSubmit.getContent().getContentGroup().hasReadRight(user), userHaveToSubmit.getContent().getLecture().isHostUser(user));
        Submit submit = new Submit();
        submit.setWriter(user);
        submit.setUserHaveToSubmit(userHaveToSubmit);
        submit.setWriteDate(new Date());
        submit.setUpdateTime(new Date());
        submit.setBody(submitParameterDto.getBody());
        attachmentDeclareService.attachmentsDeclare(submitParameterDto, submit);

        submitRepository.save(submit);

        List<User> messageReceiverList = new ArrayList<>();
        userHaveToSubmit.getContent().getContentGroup().getSubmitReadable().stream().forEach(
                userGroupCanReadSubmit -> messageReceiverList.addAll(userGroupCanReadSubmit.getUserGroup().getUsers())
        );
        User hostUser = userHaveToSubmit.getContent().getLecture().getHostUser();
        if (!messageReceiverList.contains(hostUser))
            messageReceiverList.add(hostUser);
        if (!messageReceiverList.contains(userHaveToSubmit.getUser()))
            messageReceiverList.add(userHaveToSubmit.getUser());
        messageReceiverList.remove(submit.getWriter());

        if (submit.getWriter().equals(userHaveToSubmit.getUser())) {
            PackagedMessage message = aMessage().from(user).to(messageReceiverList)
                    .with(new UserSubmitMissionToScoreGraderMessage(userHaveToSubmit.getContent(), submit.getWriter())).packaging();
            messageService.send(message);
            return success(new SubmitDto(submit));
        }

        PackagedMessage message = aMessage().from(user).to(messageReceiverList)
                .with(new UserSubmitHelpMessage(userHaveToSubmit.getContent(), submit.getWriter())).packaging();
        messageService.send(message);
        return success(new SubmitDto(submit));
    }

    public Result update(SubmitParameterDto submitParameterDto, User user) {
        Submit fromDB = assureNotNull(submitRepository.findOne(submitParameterDto.getId()));
        fromDB.setUpdateTime(new Date());
        fromDB.setBody(submitParameterDto.getBody());
        attachmentDeclareService.attachmentsDeclare(submitParameterDto, fromDB);
        submitAuth.checkUpdateRight(fromDB, user);
        return success(new SubmitDto(fromDB));
    }

    public Result delete(Long id, User user) {
        Submit submit = assureNotNull(submitRepository.findOne(id));
        submitAuth.checkDeleteRight(submit, user);
        submitRepository.delete(submit);
        return success();
    }

    @PersistenceContext
    EntityManager entityManager;

    public Result getList(Long userHaveToSubmitId, Integer page, User user) {
        UserHaveToSubmit userHaveToSubmit = userHaveToSubmitRepository.findOne(userHaveToSubmitId);
        submitAuth.checkSubmitReadable(userHaveToSubmit, user);
        QSubmit qSubmit = QSubmit.submit;
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qSubmit).where(qSubmit.userHaveToSubmit.id.eq(userHaveToSubmitId)).orderBy(qSubmit.id.desc()).limit(AppConfig.PAGE_SIZE).offset(AppConfig.PAGE_SIZE * page);
        List<Submit> submitList = query.list(qSubmit);
        return success(submitList.stream().map(SubmitDto::new).collect(Collectors.toList()));
    }
}
