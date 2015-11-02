package org.next.lms.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.repository.UserInfoRepository;
import org.next.lms.common.domain.Term;
import org.next.lms.common.repository.TermRepository;
import org.next.lms.dto.LectureDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.repository.LectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;
import static org.next.infra.util.CommonUtils.stringIdArrayToIdList;

@Service
public class LectureService {

    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TermRepository termRepository;

    public CommonJsonResponse save(Lecture lecture, Long termId, String managerIds, UserInfo userInfo) {
        List<Long> managerIdList = stringIdArrayToIdList(managerIds);
        List<UserInfo> managers = managerIdList.stream().map(userInfoRepository::findOne).collect(Collectors.toList());

        lecture.setHostUser(userInfo);
        lecture.addManagers(managers);
        lecture.setTerm(termRepository.findOne(termId));

        lectureRepository.save(lecture);
        return successJsonResponse();
    }

    public LectureDto getDtoById(Long lectureId) {
        return new LectureDto(lectureRepository.getOne(lectureId));
    }
}
