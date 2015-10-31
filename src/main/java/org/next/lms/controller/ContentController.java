package org.next.lms.controller;

import org.next.infra.broker.SessionBroker;
import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.dto.ClientUserInfoDto;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.service.InfraUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    @Autowired
    private UserInfoBroker userInfoBroker;

    @Autowired
    private SessionBroker sessionBroker;

    @PermitAll
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommonJsonResponse userLogout(HttpSession session) {
        session.invalidate();
        return successJsonResponse();
    }
}
