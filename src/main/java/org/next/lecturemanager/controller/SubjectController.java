package org.next.lecturemanager.controller;

import org.next.common.dto.JsonResponse;
import org.next.lecturemanager.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Secured({"ROLE_STUDENT", "ROLE_TEACHING_ASSISTANT", "ROLE_PROFESSOR", "ROLE_SYSTEM_MANAGER"})
    public JsonResponse getSubjectInfo(@PathVariable(value = "id") Long subjectId) {
        return subjectService.getSubjectInfo(subjectId);
    }
}

