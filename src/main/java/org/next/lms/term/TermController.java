package org.next.lms.term;

import org.next.config.AppConfig;
import org.next.infra.repository.TermRepository;
import org.next.infra.result.Result;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/term")
public class TermController {

    @Autowired
    TermRepository termRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Result find(String keyword) {
        PageRequest pageRequest = new PageRequest(0, AppConfig.pageSize);
        return Result.success(termRepository.findByNameContaining(keyword, pageRequest).stream().map(TermDto::new).collect(Collectors.toList()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(Term term, @Logged User user) {
        termRepository.save(term);
        return Result.success(term);
    }

}
