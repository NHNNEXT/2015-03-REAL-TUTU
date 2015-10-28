package org.next.infra.user.controller;

import org.next.infra.common.dto.JsonResponse;
import org.next.infra.user.domain.AccountType;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.service.InfraUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class InfraUserController {

    private static final Logger logger = LoggerFactory.getLogger(InfraUserController.class);

    @Autowired
    private InfraUserService infraUserService;

    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_STUDENT", "ROLE_TEACHING_ASSISTANT", "ROLE_PROFESSOR", "ROLE_SYSTEM_MANAGER"})
    public JsonResponse getUserInfo(HttpSession session) {
        return infraUserService.getUserInfo(session);
    }

    @PermitAll
    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse joinService(LoginToken loginToken, AccountType accountType, UserInfo userInfo) {
        return infraUserService.join(loginToken, accountType, userInfo);
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
        return infraUserService.withdrawal(session);
    }

    @PermitAll
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse userLogin(LoginToken loginToken, HttpSession session) {
        return infraUserService.login(loginToken, session);
    }
}
