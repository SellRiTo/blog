package com.blog.blog.module.fileupload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileSerrvice {
   Map<String,Object> upload(MultipartFile file);
   void deleteAll();
   Resource loadFileName(String fileName);
}
