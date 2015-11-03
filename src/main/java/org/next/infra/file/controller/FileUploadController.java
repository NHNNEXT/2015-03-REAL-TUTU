package org.next.infra.file.controller;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    private static final String FILE_STORAGE_DIRECTORY = "./../../../client/app/uploads/";

    private static String SERVLET_CONTEXT_ABSOLUTE_PATH;
    private static String FILE_SAVE_DIRECTORY_PATH;

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void postConstruct() {
        SERVLET_CONTEXT_ABSOLUTE_PATH = servletContext.getRealPath("/");
        FILE_SAVE_DIRECTORY_PATH = SERVLET_CONTEXT_ABSOLUTE_PATH + FILE_STORAGE_DIRECTORY;
    }

    @RequestMapping
    public CommonJsonResponse uplaodFile(MultipartFile file, HttpSession session){
        if(file.isEmpty())
            return new CommonJsonResponse(ResponseCode.FileUpload.FILE_NOT_ATTACHED);

        ensureFileSaveDirectoryExist(FILE_SAVE_DIRECTORY_PATH);

        String fileName = getName(file);

        File fileStorePath = new File(FILE_SAVE_DIRECTORY_PATH + fileName);

        try {
            file.transferTo(fileStorePath);
        } catch (IllegalStateException | IOException e) {
            return new CommonJsonResponse(ResponseCode.FileUpload.ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT);
        }

        return successJsonResponse(fileName);
    }

    private String extractRelativePath(File pathTobeSaved) {
        return pathTobeSaved.toString().replace(SERVLET_CONTEXT_ABSOLUTE_PATH, "");
    }

    private String getName(MultipartFile file) {
        return FILE_SAVE_DIRECTORY_PATH + System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "") + extractFileExtention(file.getOriginalFilename());
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
