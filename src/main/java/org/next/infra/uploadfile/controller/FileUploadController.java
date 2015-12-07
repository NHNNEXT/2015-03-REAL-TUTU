package org.next.infra.uploadfile.controller;

import org.next.infra.result.Result;
import org.next.infra.result.UploadResult;
import org.next.infra.uploadfile.service.FileService;
import org.next.lms.user.domain.User;
import org.next.lms.user.control.inject.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @RequestMapping
    public UploadResult uploadFile(MultipartFile file, @Logged User user){
        return fileService.upload(file);
    }

    @RequestMapping(value="/attachment")
    public Result uploadContentFile(MultipartFile file, @Logged User user){
        return fileService.upload(file, user);
    }
}
