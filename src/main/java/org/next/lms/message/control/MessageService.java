package org.next.lms.message.control;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.next.config.AppConfig;
import org.next.infra.repository.MessageRepository;
import org.next.infra.result.Result;
import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageDto;
import org.next.lms.message.domain.PackagedMessage;
import org.next.lms.message.domain.QMessage;
import org.next.lms.message.structure.MessageTemplate;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.CommonUtils.assureTrue;

@Slf4j
@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @PersistenceContext
    EntityManager entityManager;

    public Result getList(User user, Integer page) {
        JPAQuery query = new JPAQuery(entityManager);
        QMessage qMessage = QMessage.message1;
        query = query.from(qMessage).where(qMessage.receiver.id.eq(user.getId())).orderBy(qMessage.checked.asc()).orderBy(qMessage.date.desc()).limit(AppConfig.PAGE_SIZE).offset(AppConfig.PAGE_SIZE*page);
//        query.setHint("org.hibernate.cacheable", true);
        List<Message> messages = query.list(qMessage);
        return success(messages.stream().map(MessageDto::new).collect(Collectors.toList()));
    }

    public Result read(User user, Long id) {
        Message message = assureNotNull(messageRepository.findOne(id));
        assureTrue(message.getReceiver().equals(user));

        message.read();
        return success();
    }

    public void send(PackagedMessage message) {
        saveMessage(message);
    }

    private void saveMessage(PackagedMessage message) {
        for(User receiver : message.getReceiverList()) {
            saveMessage(message.getSender(), receiver, message.getMessage());
        }
    }

    private void saveMessage(User sender, User receiver, MessageTemplate messageHolder) {
        Message messageFromDb = messageRepository.findByReceiverIdAndTypeAndPkAtBelongTypeTable(receiver.getId(), messageHolder.messageType(), messageHolder.pkAtBelongTypeTable());

        if(sender.equals(receiver) && messageHolder.needToExcludeEventEmitUser())
            return;

        if(messageFromDb == null) {
            createNewMessage(receiver, messageHolder);
            return;
        }

        if(messageHolder.isUpdatableMessage()) {
            updateMessage(messageFromDb, messageHolder);
            messageFromDb.setChecked(false);
        }

        if(!messageHolder.isUpdatableMessage() && messageFromDb.isChecked()) {
            updateMessage(messageFromDb, messageHolder);
            messageFromDb.setChecked(false);
        }
    }

    private void updateMessage(Message messageFromDb, MessageTemplate messageHolder) {
        messageFromDb.setMessage(messageHolder.getMessage().getMessage());
        messageFromDb.setDate(new Date());
    }

    private void createNewMessage(User receiver, MessageTemplate messageHolder) {
        Message message = messageHolder.getMessage();
        message.setReceiver(receiver);
        messageRepository.save(message);
    }
}

