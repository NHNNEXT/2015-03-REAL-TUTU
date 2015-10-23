package org.next.subject.service;

import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.next.common.response.JsonResponse;
import org.next.subject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public JsonResponse getSubjectInfo(Long subjectId) {
        return new JsonResponse(null, subjectRepository.findOne(subjectId));
    }
}
