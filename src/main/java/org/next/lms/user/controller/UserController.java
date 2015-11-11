package org.next.lms.user.controller;

import org.next.infra.view.JsonView;
import org.next.lms.user.User;
import org.next.lms.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

import static org.next.infra.view.JsonView.successJsonResponse;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public JsonView getUserInfo(HttpSession session, String id) {
        if (id == null)
            return userService.getSessionUser(session);
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonView register(User user) {
        return userService.register(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView updateUserInfo(User user, HttpSession session) {
        return userService.updateUserInfo(user, session);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView withdrawalUser(HttpSession session) {
        return userService.withdrawal(session);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonView userLogin(User user, HttpSession session) {
        return userService.login(user, session);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonView userLogout(HttpSession session) {
        session.invalidate();
        return successJsonResponse();
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public JsonView userAutoComplete(String keyword) {
        return successJsonResponse(userService.findByNameLike(keyword));
    }
}