package org.next.infra.uploadfile.controller;


import org.next.infra.uploadfile.dto.GroupedUploadFileDto;
import org.next.infra.uploadfile.service.FileService;
import org.next.infra.util.SessionUtil;
import org.next.lms.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/file/list")
public class FileListController {

    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/me")
    public GroupedUploadFileDto listMyUploadFile(HttpSession session){
        User user = sessionUtil.getLoggedUser(session);
        return fileService.getUploadFileList(user);
    }
}
