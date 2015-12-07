package org.next.infra.uploadfile.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.next.infra.uploadfile.UploadedFile;

import java.util.Date;

@Getter
public class UploadedFileDto {

    public static final String API_V1_DOWNLOAD = "/api/v1/download/";

    @JsonIgnore
    private Long lectureId;
    @JsonIgnore
    private String lectureName;

    private Long id;
    private String originalFileName;
    private String downloadUrl;
    private Date uploadTime;

    public UploadedFileDto(UploadedFile uploadedFile) {
        this.id = uploadedFile.getId();
        this.originalFileName = uploadedFile.getOriginalFileName();
        this.downloadUrl = API_V1_DOWNLOAD + uploadedFile.getUglyFileName();
        this.uploadTime = uploadedFile.getUploadTime();
    }
}
