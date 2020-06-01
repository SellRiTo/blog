package com.blog.blog.module.fileupload.controller;

import com.blog.blog.module.fileupload.service.FileSerrvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

/**
 * @program: blog
 * @description: 文件上传
 * @author: txr
 * @create: 2020-05-28 15:20
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

    private final FileSerrvice fileSerrvice;

    @Autowired
    public FileUploadController(FileSerrvice fileSerrvice){
        this.fileSerrvice = fileSerrvice;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile file){
        Map<String, Object> upload = fileSerrvice.upload(file);
        return new ResponseEntity<Object>(upload, HttpStatus.OK);
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<Object> deleteAll(){
        fileSerrvice.deleteAll();
        return new ResponseEntity<Object>(null,HttpStatus.OK);
    }


    @GetMapping("/load/{fileName:.+}")
    public ResponseEntity<Resource> loadFileName(@PathVariable String fileName){
        Resource file = fileSerrvice.loadFileName(fileName);
        return  ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

}
