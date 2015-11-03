package org.next.lms.service;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.repository.UserInfoRepository;
import org.next.lms.dto.LectureDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.repository.LectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    private UserInfoBroker userInfoBroker;


    public CommonJsonResponse save(Lecture lecture, String managerIds, HttpSession session) {
        UserInfo userInfo = userInfoBroker.getUserInfo(session);
        lecture.setHostUser(userInfo);

        List<Long> managerIdList = stringIdArrayToIdList(managerIds);
        if (managerIdList != null) {
            List<UserInfo> managers = managerIdList.stream().map(userInfoRepository::findOne).collect(Collectors.toList());
            lecture.addManagers(managers);
        }

        lectureRepository.save(lecture);
        return successJsonResponse();
    }

    public LectureDto getDtoById(Long lectureId) {
        return new LectureDto(lectureRepository.getOne(lectureId));
    }
}
