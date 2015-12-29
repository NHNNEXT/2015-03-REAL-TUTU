package org.next.infra.uploadfile.service;

import lombok.extern.slf4j.Slf4j;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.UploadFileRepository;
import org.next.infra.result.Result;
import org.next.infra.uploadfile.UploadedFile;
import org.next.infra.uploadfile.dto.GroupedUploadFileDto;
import org.next.infra.uploadfile.dto.UploadedFileDto;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.MultipartFileUtils.getNormalizedFileName;
import static org.next.infra.view.DownloadView.downloadView;

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


    public Result upload(MultipartFile file, User userAccount) {
        if (file.isEmpty())
            return new Result(ResponseCode.FileUpload.FILE_NOT_ATTACHED);

        // TODO Null Point말고 관리되는 다른 예외를 던져야 할것 같은데..
        Objects.requireNonNull(FILE_STORAGE_DIRECTORY);
        ensureFileSaveDirectoryExist(FILE_STORAGE_DIRECTORY);

        String uglifiedFileName = uglifyFileName(file);

        File fileStorePath = new File(FILE_STORAGE_DIRECTORY + uglifiedFileName);

        try {
            file.transferTo(fileStorePath);
        } catch (IllegalStateException | IOException e) {
            log.debug("{}", e.getCause());
            return new Result(ResponseCode.FileUpload.ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT);
        }

        return success(new UploadedFileDto(saveFileInfo(file, uglifiedFileName, userAccount)));
    }

    private UploadedFile saveFileInfo(MultipartFile file, String fileName, User user) {
        UploadedFile fileInfo = new UploadedFile();
        fileInfo.setOriginalFileName(getNormalizedFileName(file));
        fileInfo.setUglyFileName(fileName);
        fileInfo.setUploadUser(user);
        uploadFileRepository.save(fileInfo);
        return fileInfo;
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
        UploadedFile fileInfoFromDb = assureNotNull(uploadFileRepository.findByUglyFileName(uglifiedFileName));
        File fileInStorage = new File(FILE_STORAGE_DIRECTORY + uglifiedFileName);

        return downloadView(fileInStorage, fileInfoFromDb.getOriginalFileName());
    }

    public GroupedUploadFileDto getUploadFileList(User user) {
        List<UploadedFile> uploadedFileList = uploadFileRepository.findByUploadUserId(user.getId());

        return new GroupedUploadFileDto(uploadedFileList);
    }
}
