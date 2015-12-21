package org.next.infra.uploadfile.controller;


import org.next.infra.uploadfile.dto.GroupedUploadFileDto;
import org.next.infra.uploadfile.service.FileService;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/file/list")
public class FileListController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/me")
    public GroupedUploadFileDto listMyUploadFile(@Logged User user) {
        return fileService.getUploadFileList(user);
    }
}
