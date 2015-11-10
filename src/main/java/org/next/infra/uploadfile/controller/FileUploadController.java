package org.next.infra.uploadfile.controller;

import org.next.infra.uploadfile.service.FileService;
import org.next.infra.util.SessionUtil;
import org.next.infra.view.JsonView;
import org.next.lms.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private FileService fileService;

    @RequestMapping
    public JsonView uploadFile(MultipartFile file, HttpSession session){
        User user = sessionUtil.getLoggedUser(session);
        return fileService.upload(file, user);
    }
}
