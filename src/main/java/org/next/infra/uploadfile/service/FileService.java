package org.next.infra.uploadfile.service;

import lombok.extern.slf4j.Slf4j;
import org.next.infra.repository.ContentRepository;
import org.next.infra.uploadfile.UploadedFile;
import org.next.infra.uploadfile.dto.GroupedUploadFileDto;
import org.next.infra.view.JsonView;
import org.next.infra.repository.UploadFileRepository;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.view.UploadView;
import org.next.lms.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.next.infra.view.JsonView.successJsonResponse;
import static org.next.infra.view.DownloadView.downloadView;
import static org.next.infra.util.MultipartFileUtils.getNormalizedFileName;

@Slf4j
@Service
@Transactional
@PropertySource("classpath:config.properties")
public class FileService {

    @Value("${FILE_UPLOAD_PATH}")
    private String FILE_STORAGE_DIRECTORY;

    @Value("${RELATIVE_PATH}")
    private String RELATIVE_PATH;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private ContentRepository contentRepository;

    public UploadView upload(MultipartFile file, User userAccount) {
        if (file.isEmpty())
            return new UploadView(ResponseCode.FileUpload.FILE_NOT_ATTACHED);

        Objects.requireNonNull(FILE_STORAGE_DIRECTORY);
        ensureFileSaveDirectoryExist(FILE_STORAGE_DIRECTORY);

        String uglifiedFileName = uglifyFileName(file);

        File fileStorePath = new File(FILE_STORAGE_DIRECTORY + uglifiedFileName);

        try {
            file.transferTo(fileStorePath);
        } catch (IllegalStateException | IOException e) {
            log.debug("{}", e.getCause());
            return new UploadView(ResponseCode.FileUpload.ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT);
        }

        return new UploadView(RELATIVE_PATH + uglifiedFileName);
    }


    public JsonView upload(MultipartFile file, User userAccount, Long contentId) {
        if (file.isEmpty())
            return new JsonView(ResponseCode.FileUpload.FILE_NOT_ATTACHED);

        Objects.requireNonNull(FILE_STORAGE_DIRECTORY);
        ensureFileSaveDirectoryExist(FILE_STORAGE_DIRECTORY);

        String uglifiedFileName = uglifyFileName(file);

        File fileStorePath = new File(FILE_STORAGE_DIRECTORY + uglifiedFileName);

        try {
            file.transferTo(fileStorePath);
        } catch (IllegalStateException | IOException e) {
            log.debug("{}", e.getCause());
            return new JsonView(ResponseCode.FileUpload.ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT);
        }
        saveFileInfo(file, uglifiedFileName, contentId, userAccount);
        return successJsonResponse(uglifiedFileName);
    }

    private void saveFileInfo(MultipartFile file, String fileName, Long contentId, User user) {
        UploadedFile fileInfo = new UploadedFile();
        fileInfo.setOriginalFileName(getNormalizedFileName(file));
        fileInfo.setUglyFileName(fileName);
        fileInfo.setContent(contentRepository.findOne(contentId));
        fileInfo.setUploadUser(user);
        uploadFileRepository.save(fileInfo);
    }

    private String uglifyFileName(MultipartFile file) {
        return System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "") + extractFileExtention(file.getOriginalFilename());
    }

    private void ensureFileSaveDirectoryExist(String fileSaveDirectoryPath) {
        File fileSaveDirectory = new File(fileSaveDirectoryPath);

        if (!fileSaveDirectory.exists()) {
            fileSaveDirectory.mkdirs();
        }
    }

    private String extractFileExtention(String fileName) {
        int lastPeriod = fileName.lastIndexOf(".");
        return fileName.substring(lastPeriod, fileName.length());
    }

    public ModelAndView downloadFile(String uglifiedFileName) {
        File fileInStorage = new File(FILE_STORAGE_DIRECTORY + uglifiedFileName);
        UploadedFile fileInfoFromDb = uploadFileRepository.findByUglyFileName(uglifiedFileName);

        return downloadView(fileInStorage, fileInfoFromDb.getOriginalFileName());
    }

    public GroupedUploadFileDto getUploadFileList(User user) {
        List<UploadedFile> uploadedFileList = uploadFileRepository.findByUploadUserId(user.getId());

        return new GroupedUploadFileDto(uploadedFileList);
    }
}
