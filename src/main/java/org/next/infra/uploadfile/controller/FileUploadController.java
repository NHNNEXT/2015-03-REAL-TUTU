package org.next.infra.uploadfile.controller;

import org.next.infra.uploadfile.service.FileService;
import org.next.infra.util.SessionUtil;
import org.next.infra.view.JsonView;
import org.next.infra.view.UploadView;
import org.next.lms.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private FileService fileService;

    @RequestMapping
    public UploadView uploadFile(MultipartFile file, HttpSession session){
        User user = sessionUtil.getLoggedUser(session);
        return fileService.upload(file, user);
    }

    @RequestMapping(value="/content")
    public JsonView uploadFile(MultipartFile file, Long contentId, HttpSession session){
        User user = sessionUtil.getLoggedUser(session);
        return fileService.upload(file, user, contentId);
    }
}
