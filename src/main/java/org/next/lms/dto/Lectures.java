package org.next.lms.dto;

import lombok.Getter;
import org.next.infra.user.domain.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Lectures {
    List<LectureDto> hosted;
    List<LectureDto> enrolled;
    List<LectureDto> managed;

    public Lectures(UserInfo userInfo) {
        hosted = userInfo.getHostLectures().stream().map(LectureDto::new).collect(Collectors.toList());
        enrolled = userInfo.getEnrolledLectures().stream().map(relation -> new LectureDto(relation.getLecture())).collect(Collectors.toList());
        managed = userInfo.getManageLectures().stream().map(relation -> new LectureDto(relation.getLecture())).collect(Collectors.toList());
    }
}
