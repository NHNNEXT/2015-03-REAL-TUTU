package org.next.infra.file.controller;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/download")
public class FileDownloadController {

    @Autowired
    private UserInfoBroker userInfoBroker;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/{uglifiedFileName}.{fileExtension}")
    public ModelAndView downloadFile(@PathVariable String uglifiedFileName, @PathVariable String fileExtension){
        String fileName = uglifiedFileName + "." + fileExtension;
        return fileService.downloadFile(fileName);
    }
}
