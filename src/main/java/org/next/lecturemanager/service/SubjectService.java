package org.next.lecturemanager.service;

import org.next.common.dto.JsonResponse;
import org.next.lecturemanager.repository.SubjectRepository;
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
