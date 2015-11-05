package org.next.infra.file.controller;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.file.service.FileUploadService;
import org.next.infra.user.domain.LoginAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    @Autowired
    private UserInfoBroker userInfoBroker;

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping
    public CommonJsonResponse uplaodFile(MultipartFile file, HttpSession session){
        LoginAccount userAccount = userInfoBroker.getLoginAccount(session);
        return fileUploadService.upload(file, userAccount);
    }
}
