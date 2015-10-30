package org.next.infra.user.controller;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.DetailUserInfo;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.service.InfraUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class InfraUserController {

    private static final Logger logger = LoggerFactory.getLogger(InfraUserController.class);

    @Autowired
    private InfraUserService infraUserService;

    @Secured({"ROLE_NOT_AUTHORIZED", "ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.GET)
    public CommonJsonResponse getUserInfo(HttpSession session) {
        return infraUserService.getUserInfo(session);
    }

    @PermitAll
    @RequestMapping(method = RequestMethod.POST)
    public CommonJsonResponse joinService(LoginToken loginToken) {
        return infraUserService.join(loginToken);
    }

    @Secured({"ROLE_NOT_AUTHORIZED", "ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.PUT)
    public CommonJsonResponse editUserAccountAndInfo(LoginToken loginToken, UserInfo userInfo, DetailUserInfo detailUserInfo, HttpSession session) {
        return infraUserService.edit(loginToken, userInfo, detailUserInfo, session);
    }

    @Secured({"ROLE_NOT_AUTHORIZED", "ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.DELETE)
    public CommonJsonResponse withdrawalUser(HttpSession session) {
        return infraUserService.withdrawal(session);
    }

    @PermitAll
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonJsonResponse userLogin(LoginToken loginToken, HttpSession session) {
        return infraUserService.login(loginToken, session);
    }
}
