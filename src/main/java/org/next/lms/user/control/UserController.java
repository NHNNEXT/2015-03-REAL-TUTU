package org.next.lms.user.control;

import org.next.infra.result.Result;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMailService userMailService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getUser(String email) {
        return userService.getUser(email);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/session")
    public Result getSessionUser(@Logged(makeLoginNeededException = false) User user) {
        return userService.getSessionUser(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result register(User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/resendMailVerify", method = RequestMethod.POST)
    public Result resendMailVerify(String email) {
        return userMailService.resendMailVerify(email);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result updateUser(User passed, @Logged User fromSession) {
        return userService.updateUser(passed, fromSession);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result withdrawalUser(@Logged User user) {
        return userService.withdrawal(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result userLogin(User user, HttpSession session) {
        return userService.login(user, session);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result userLogout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Result userAutoComplete(String keyword) {
        return userService.findByNameLike(keyword);
    }

    @RequestMapping(value = "/sendChangePwMail", method = RequestMethod.POST)
    public Result sendChangePasswordMail(String email) {
        return userMailService.sendChangePasswordMail(email);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public Result changePassword(String email, String key, String password) {
        return userMailService.changePassword(email, key, password);
    }

    @RequestMapping(value = "/mailVerify", method = RequestMethod.POST)
    public Result mailVerify(String key, String email) {
        return userMailService.verifyMail(key, email);
    }
}
