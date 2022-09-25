package com.ger.logsif.api;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ger.logsif.service.LogFileService;
import com.ger.logsif.service.UploadFileResponse;


@RestController
@RequestMapping(value = "/logsif")
public class LogFileController {
	
	@Autowired
	LogFileService storageService;
	
	@PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile logfile) {
        String fileName = storageService.storeLogFile(logfile);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
        		logfile.getContentType(), logfile.getSize());
    }
	
//	Compile-time Polymorphism
	@PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile logfile, String key) {
        String fileName = storageService.storeLogFile(logfile, key);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
        		logfile.getContentType(), logfile.getSize());
    }

}


//TODO find out how to create REST API that accepts java files