package org.next.core.user;

import org.next.common.response.JsonResponse;
import org.next.core.user.domain.AccountType;
import org.next.core.user.domain.LoginAccount;
import org.next.core.user.domain.UserInfo;
import org.next.core.user.dto.LoginToken;
import org.next.core.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_STUDENT", "ROLE_TEACHING_ASSISTANT", "ROLE_PROFESSOR", "ROLE_SYSTEM_MANAGER"})
    public JsonResponse getUserInfo(HttpSession session) {
        return userService.getUserInfo(session);
    }

    @PermitAll
    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse joinService(LoginToken loginToken, AccountType accountType, UserInfo userInfo) {
        return userService.join(loginToken, accountType, userInfo);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Secured({"ROLE_NOT_AUTHORIZED", "ROLE_STUDENT", "ROLE_TEACHING_ASSISTANT", "ROLE_PROFESSOR", "ROLE_SYSTEM_MANAGER"})
    public void editUserAccountAndInfo(LoginAccount loginAccount) {
        // TODO 정보가 어떻게 넘어올지 논의후 결정
        // userService.edit(loginAccount);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Secured({"ROLE_STUDENT", "ROLE_TEACHING_ASSISTANT", "ROLE_PROFESSOR", "ROLE_SYSTEM_MANAGER"})
    public JsonResponse withdrawalUser(HttpSession session) {
        return userService.withdrawal(session);
    }

    @PermitAll
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse userLogin(LoginToken loginToken, HttpSession session) {
        return userService.login(loginToken, session);
    }
}

