package org.next.infra.file.service;

import lombok.extern.slf4j.Slf4j;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.file.domain.UploadedFile;
import org.next.infra.file.repository.UploadFileRepository;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.user.domain.LoginAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;
import static org.next.infra.util.MultipartFileUtils.getNormalizedFileName;

@Slf4j
@Service
@Transactional
@PropertySource("classpath:config.properties")
public class FileUploadService {

    @Value("${FILE_UPLOAD_PATH}")
    private String FILE_STORAGE_DIRECTORY;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    public CommonJsonResponse upload(MultipartFile file, LoginAccount userAccount) {
        if(file.isEmpty())
            return new CommonJsonResponse(ResponseCode.FileUpload.FILE_NOT_ATTACHED);

        Objects.requireNonNull(FILE_STORAGE_DIRECTORY);
        ensureFileSaveDirectoryExist(FILE_STORAGE_DIRECTORY);

        String uglifiedFileName = uglifyFileName(file);

        File fileStorePath = new File(FILE_STORAGE_DIRECTORY + uglifiedFileName);

        try {
            file.transferTo(fileStorePath);
        } catch (IllegalStateException | IOException e) {
            return new CommonJsonResponse(ResponseCode.FileUpload.ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT);
        }

        saveFileInfo(file, userAccount, uglifiedFileName);

        return successJsonResponse(uglifiedFileName);
    }

    private void saveFileInfo(MultipartFile file, LoginAccount userAccount, String fileName) {
        UploadedFile fileInfo = new UploadedFile();
        fileInfo.setOriginalFileName(getNormalizedFileName(file));
        fileInfo.setUglyFileName(fileName);
        fileInfo.setUploadUser(userAccount);
        uploadFileRepository.save(fileInfo);
    }

    private String uglifyFileName(MultipartFile file) {
        return System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "") + extractFileExtention(file.getOriginalFilename());
    }

    private void ensureFileSaveDirectoryExist(String fileSaveDirectoryPath) {
        File fileSaveDirectory = new File(fileSaveDirectoryPath);

        if(!fileSaveDirectory.exists()) {
            fileSaveDirectory.mkdirs();
        }
    }

    private String extractFileExtention(String fileName) {
        int lastPeriod = fileName.lastIndexOf(".");
        return fileName.substring(lastPeriod, fileName.length());
    }
}
