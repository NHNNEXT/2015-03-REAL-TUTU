package org.next.lms.user.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.reponse.ResponseCode;
import org.next.lms.user.inject.LoggedUserInjector;
import org.next.lms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

//    @Test
//    public void testGetUser() throws Exception {
//        this.mockMvc.perform(get("/api/v1/user")
//                .param("id", "1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
//                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS))
//                .andExpect(jsonPath("$.result.email").value("test1@test.com"));
//    }

//    @Test
//    public void testGetByEmailUser() throws Exception {
//        this.mockMvc.perform(get("/api/v1/user")
//                .param("id", "test1@test.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
//                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS))
//                .andExpect(jsonPath("$.result.id").value(1));
//    }

//    @Test
//    public void testGetSessionUser() throws Exception {
//        this.mockMvc.perform(get("/api/v1/user/session")
//                .sessionAttr(LoggedUserInjector.LOGIN_ACCOUNT_ID, 1L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
//                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS));
//    }

    @Test
    public void testGetSessionUserEmpty() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/session"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(ResponseCode.GetSessionUser.EMPTY));
    }

    @Test
    public void testEmailPatternWrongRegister() throws Exception {
        this.mockMvc.perform(post("/api/v1/user")
                .param("email", "email")
                .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(ResponseCode.PATTERN_NOT_MATCHED));
    }

    @Test
    public void testPasswordPatternWrongRegister() throws Exception {
        this.mockMvc.perform(post("/api/v1/user").param("email", "email").param("password", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(ResponseCode.PATTERN_NOT_MATCHED));
    }

    @Test
    public void testSuccessRegister() throws Exception {
        this.mockMvc.perform(post("/api/v1/user")
                .param("email", "email@email.com")
                .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS));
    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testWithdrawalUser() throws Exception {

    }

//    @Test
//    public void testUserLoginSuccess() throws Exception {
//        this.mockMvc.perform(post("/api/v1/user/login")
//                .param("email", "test1@test.com")
//                .param("password", "password"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
//                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS));
//    }

    @Test
    public void testUserLoginNotExistEmail() throws Exception {
        this.mockMvc.perform(post("/api/v1/user/login")
                .param("email", "test1123@test.com")
                .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(ResponseCode.Login.NOT_EXIST_EMAIL));
    }

//    @Test
//    public void testUserLoginPasswordWrong() throws Exception {
//        this.mockMvc.perform(post("/api/v1/user/login")
//                .param("email", "test1@test.com")
//                .param("password", "password1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
//                .andExpect(jsonPath("$.code").value(ResponseCode.Login.WRONG_PASSWORD));
//    }


}