package org.next.lms.submit;

import org.next.infra.result.Result;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/submit")
public class SubmitController {


    @Autowired
    private SubmitService submitService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody SubmitParameterDto submitParameterDto, @Logged User user) {
        return submitService.save(submitParameterDto, user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody SubmitParameterDto submitParameterDto, @Logged User user) {
        return submitService.update(submitParameterDto, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(Long id, @Logged User user) {
        return submitService.delete(id, user);
    }
}
