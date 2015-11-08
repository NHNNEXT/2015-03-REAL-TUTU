package org.next.infra.user.controller;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.service.InfraUserService;
import org.next.lms.controller.LectureController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@RestController
@RequestMapping("/api/v1/user")
public class InfraUserController {

    private static final Logger logger = LoggerFactory.getLogger(InfraUserController.class);

    @Autowired
    private InfraUserService infraUserService;

    @PermitAll
    @RequestMapping(method = RequestMethod.GET)
    public CommonJsonResponse getUserInfo(HttpSession session, String id) {
        if (id == null)
            return infraUserService.getSessionUser(session);
        return infraUserService.getUser(id);
    }

    @PermitAll
    @RequestMapping(method = RequestMethod.POST)
    public CommonJsonResponse joinService(LoginToken loginToken, String name) {
        return infraUserService.join(loginToken, name);
    }

    @PermitAll
    @RequestMapping(method = RequestMethod.PUT)
    public CommonJsonResponse updateUserInfo(UserInfo userInfo, HttpSession session) {
        return infraUserService.updateUserInfo(userInfo, session);
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

    @PermitAll
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommonJsonResponse userLogout(HttpSession session) {
        session.invalidate();
        return successJsonResponse();
    }

    @PermitAll
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public CommonJsonResponse userAutoComplete(String keyword) {
        return successJsonResponse(infraUserService.findByNameLike(keyword));
    }
}
