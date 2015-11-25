package org.next.justtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.reponse.ResponseCode;
import org.next.lms.user.inject.LoggedUserInjector;
import org.next.lms.user.repository.UserRepository;
import org.next.lms.user.state.AccountState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class ExceptionTest {

    @Autowired
    private UserRepository userRepository;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 인자가_널이면_InvalidDataAccessApiUsageException_발생1() throws Exception {
        userRepository.getOne(null);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 인자가_널이면_InvalidDataAccessApiUsageException_발생2() throws Exception {
        userRepository.findOne(null);
    }
}