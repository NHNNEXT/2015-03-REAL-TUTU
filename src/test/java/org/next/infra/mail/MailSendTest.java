package org.next.infra.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.mail.template.JoinMailVerifyTemplate;
import org.next.infra.util.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.next.infra.util.CommonUtils.makeUUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class MailSendTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private EnvUtils envUtils;

    @Autowired
    private Environment environment;

}
