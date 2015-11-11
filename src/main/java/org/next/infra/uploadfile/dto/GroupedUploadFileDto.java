package org.next.infra.uploadfile.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.next.infra.uploadfile.UploadedFile;

import java.util.*;

@Getter
public class GroupedUploadFileDto {

    @JsonIgnore
    private Map<Long, List<UploadedFileDto>> lectureIdAndAttachedFileListMap = new TreeMap<>();
    @JsonIgnore
    private Map<Long, String> lectureIdAndNameMappingDic = new HashMap<>();

    private List<GroupedByLectureDto> groupedUploadFiles = new ArrayList<>();

    public GroupedUploadFileDto(List<UploadedFile> uploadedFileList) {
        organizeUploadFileToMap(uploadedFileList);
        organizeUploadFileToList();
    }

    private void organizeUploadFileToMap(List<UploadedFile> uploadedFileList) {
        uploadedFileList.stream().map(UploadedFileDto::new).forEach(uploadFile -> {
            organizeLectureIdAndNameMappingDictionary(uploadFile);
            organizeLectureIdAndAttachedFileListMap(uploadFile);
        });
    }

    private void organizeLectureIdAndNameMappingDictionary(UploadedFileDto uploadfile) {
        lectureIdAndNameMappingDic.put(uploadfile.getLectureId(), uploadfile.getLectureName());
    }

    private void organizeLectureIdAndAttachedFileListMap(UploadedFileDto uploadfile) {
        Long lectureId = uploadfile.getLectureId();

        List<UploadedFileDto> uploadInSameLecture = lectureIdAndAttachedFileListMap.get(lectureId);

        if(uploadInSameLecture == null) {
            lectureIdAndAttachedFileListMap.put(lectureId, new ArrayList<>());
            uploadInSameLecture = lectureIdAndAttachedFileListMap.get(lectureId);
        }

        uploadInSameLecture.add(uploadfile);
    }

    private void organizeUploadFileToList() {
        lectureIdAndAttachedFileListMap.keySet().forEach(lectureId -> {
            GroupedByLectureDto groupedByLectureDto = new GroupedByLectureDto(lectureId, lectureIdAndNameMappingDic.get(lectureId));

            lectureIdAndAttachedFileListMap.get(lectureId).forEach(groupedByLectureDto::addFile);
            groupedByLectureDto.sortList();

            groupedUploadFiles.add(groupedByLectureDto);
        });
    }
}

@Getter
class GroupedByLectureDto {

    private Long lectureId;
    private String lectureName;
    List<UploadedFileDto> uploadInSameLecture = new ArrayList<>();

    public GroupedByLectureDto(Long lectureId, String lectureName) {
        this.lectureId = lectureId;
        this.lectureName = lectureName;
    }

    public void addFile(UploadedFileDto fileDto) {
        uploadInSameLecture.add(fileDto);
    }

    public void sortList() {
        uploadInSameLecture.sort((o1, o2) -> (int) (o1.getUploadTime().toInstant().toEpochMilli() - o2.getUploadTime().toInstant().toEpochMilli()));
    }
}
