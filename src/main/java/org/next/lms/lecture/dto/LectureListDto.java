package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureListDto {
    List<LectureDto> hosted;
    List<LectureDto> enrolled;

    public LectureListDto(User user) {
        hosted = user.getHostLectures().stream().map(LectureDto::new).collect(Collectors.toList());
        enrolled = user.getEnrolledLectures().stream().map(relation -> new LectureDto(relation.getLecture())).collect(Collectors.toList());
    }
}
