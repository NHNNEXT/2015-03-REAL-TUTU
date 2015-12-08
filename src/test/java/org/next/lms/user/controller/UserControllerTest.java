package org.next.lms.user.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.reponse.ResponseCode;
import org.next.lms.user.control.inject.LoggedUserInjector;
import org.next.infra.repository.UserRepository;
import org.next.lms.user.domain.AccountState;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
        User user = new User();
        user.setEmail("test1@test.com");
        user.setPassword("password");
        user.setName("테스트유저");
        user.encryptPassword(passwordEncoder);
        userRepository.save(user);
    }

    @Test
    public void testGetUser() throws Exception {
        this.mockMvc.perform(get("/api/v1/user")
                .param("id", "1"))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.SUCCESS))
                .andExpect(jsonPath("$.result.email").value("test1@test.com"));
    }


    @Test
    public void testGetSessionUser() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/session")
                .sessionAttr(LoggedUserInjector.LOGIN_ACCOUNT_ID, 1L))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.SUCCESS))
                .andExpect(jsonPath("$.result.id").value(1));
    }

    @Test
    public void testGetSessionUserEmpty() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/session"))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.GetSessionUser.EMPTY));
    }

    @Test
    public void testEmailPatternWrongRegister() throws Exception {
        this.mockMvc.perform(post("/api/v1/user")
                .param("email", "email")
                .param("password", "password"))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.PATTERN_NOT_MATCHED));
    }

    @Test
    public void testPasswordPatternWrongRegister() throws Exception {
        this.mockMvc.perform(post("/api/v1/user").param("email", "email").param("password", "1"))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.PATTERN_NOT_MATCHED));
    }

    @Test
    public void testSuccessRegister() throws Exception {
        this.mockMvc.perform(post("/api/v1/user")
                .param("name", "testName")
                .param("email", "email@email.com")
                .param("password", "password"))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.SUCCESS));
    }

    @Test
    public void testUpdateUser() throws Exception {
        this.mockMvc.perform(put("/api/v1/user")
                .sessionAttr(LoggedUserInjector.LOGIN_ACCOUNT_ID, 2L)
                .param("name", "diffName")
                .param("email", "changedEmail@email.com"))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.SUCCESS))
                .andExpect(jsonPath("$.result.name").value("diffName"))
                .andExpect(jsonPath("$.result.email").value("changedEmail@email.com"));
    }


    @Test
    public void testUserLoginNotExistEmail() throws Exception {
        this.mockMvc.perform(post("/api/v1/user/login")
                .param("email", "test1123@test.com")
                .param("password", "password"))
                .andExpect(okResponse())
                .andExpect(jsonResponse())
                .andExpect(responseCode(ResponseCode.Login.NOT_EXIST_EMAIL));
    }

    private ResultMatcher okResponse() {
        return status().isOk();
    }

    private ResultMatcher responseCode(int expectResponseCode) {
        return jsonPath("$.code").value(expectResponseCode);
    }

    private ResultMatcher jsonResponse() {
        return content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8");
    }
}