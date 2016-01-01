package org.next.lms.letter;

import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.next.config.AppConfig;
import org.next.infra.repository.LetterRepository;
import org.next.infra.repository.MessageRepository;
import org.next.infra.repository.UserRepository;
import org.next.infra.result.Result;
import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.domain.QMessage;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.CommonUtils.assureTrue;

@Slf4j
@Service
@Transactional
public class LetterService {


    public static final String NEW_LETTER_MESSAGE = "새로운 쪽지가 왔습니다.";
    public static final String MESSAGE_URL = "/쪽지함";

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MessageRepository messageRepository;

    public Result getList(User user, Integer page) {
        JPAQuery query = new JPAQuery(entityManager);
        QLetter qLetter = QLetter.letter;
        query = query.from(qLetter).where(qLetter.receiver.id.eq(user.getId())).orderBy(qLetter.id.desc()).limit(AppConfig.PAGE_SIZE).offset(page * AppConfig.PAGE_SIZE);

        deleteNewLetterArrivedMessage(user);

        return success(query.list(qLetter).stream().map(LetterDto::new).collect(Collectors.toList()));
    }

    private void deleteNewLetterArrivedMessage(User user) {
        QMessage qMessage = QMessage.message1;
        new JPADeleteClause(entityManager, qMessage).where(qMessage.receiver.id.eq(user.getId()).and(qMessage.type.eq(MessageType.NEW_LETTER_ARRIVED))).execute();
    }

    public Result read(User user, Long id) {
        Letter letter = assureNotNull(letterRepository.findOne(id));
        assureTrue(letter.getReceiver().equals(user));
        letter.read();
        return success();
    }


    public Result saveLetter(User user, String message, Long receiverId) {
        Letter letter = new Letter();
        User receiver = assureNotNull(userRepository.findOne(receiverId));
        letter.setSender(user);
        letter.setReceiver(receiver);
        letter.setDate(new Date());
        letter.setMessage(message);
        letterRepository.save(letter);

        long count = messageRepository.countByReceiverIdAndType(receiver.getId(), MessageType.NEW_LETTER_ARRIVED);
        if (count > 0)
            return success(new LetterDto(letter));
        Message alert = new Message();
        alert.setType(MessageType.NEW_LETTER_ARRIVED);
        alert.setMessage(NEW_LETTER_MESSAGE);
        alert.setReceiver(receiver);
        alert.setDate(new Date());
        alert.setChecked(false);
        alert.setUrl(MESSAGE_URL);
        messageRepository.save(alert);
        return success(new LetterDto(letter));
    }
}

