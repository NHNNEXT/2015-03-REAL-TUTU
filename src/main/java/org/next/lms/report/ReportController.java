package org.next.lms.report;

import org.next.infra.repository.ReportRepository;
import org.next.infra.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Result get() {
        return Result.success(reportRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(Report report) {
        reportRepository.save(report);
        return Result.success();
    }
}
