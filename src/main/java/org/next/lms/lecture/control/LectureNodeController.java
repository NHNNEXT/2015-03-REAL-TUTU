package org.next.lms.lecture.control;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.LectureNodeHasLectureRepository;
import org.next.infra.repository.LectureNodeRepository;
import org.next.infra.repository.LectureRepository;
import org.next.infra.result.Result;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.LectureNode;
import org.next.lms.lecture.domain.LectureNodeHasLecture;
import org.next.lms.lecture.domain.dto.LectureNodeChildrenDto;
import org.next.lms.lecture.domain.dto.LectureNodeDto;
import org.next.lms.lecture.domain.dto.LectureSummaryDto;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.util.CommonUtils.assureNotNull;

@RestController
@RequestMapping("/api/v1/lecture/node")
public class LectureNodeController {

    @Autowired
    private LectureNodeHasLectureRepository lectureNodeHasLectureRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureNodeRepository lectureNodeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Result getChildren(Long parentId) {
        List<LectureSummaryDto> lectures = lectureNodeHasLectureRepository.findByLectureNodeId(parentId).stream()
                .map(lectureNodeHasLecture -> new LectureSummaryDto(lectureNodeHasLecture.getLecture())).collect(Collectors.toList());
        List<LectureNodeDto> children = lectureNodeRepository.findByParentId(parentId).stream().map(LectureNodeDto::new).collect(Collectors.toList());
        return Result.success(new LectureNodeChildrenDto(lectures, children));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(LectureNode lectureNode) {
        lectureNodeRepository.save(lectureNode);
        return Result.success(new LectureNodeDto(lectureNode));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/relation")
    public Result addLecture(Long nodeId, Long lectureId) {
        LectureNodeHasLecture lectureNodeHasLecture = new LectureNodeHasLecture();
        lectureNodeHasLecture.setLecture(assureNotNull(lectureRepository.findOne(lectureId)));
        lectureNodeHasLecture.setLectureNode(assureNotNull(lectureNodeRepository.findOne(nodeId)));
        lectureNodeHasLectureRepository.save(lectureNodeHasLecture);
        return Result.success();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/relation")
    public Result deleteLecture(Long nodeId, Long lectureId) {
        lectureNodeHasLectureRepository.deleteByLectureNodeIdAndLectureId(nodeId, lectureId);
        return Result.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(Long lectureNodeId) {
        if (lectureNodeHasLectureRepository.findByLectureNodeId(lectureNodeId).size() != 0)
            return new Result(ResponseCode.Node.CHILD_EXIST);
        if (lectureNodeRepository.findByParentId(lectureNodeId).size() != 0)
            return new Result(ResponseCode.Node.CHILD_EXIST);
        lectureNodeRepository.delete(lectureNodeId);
        return Result.success();
    }
}
