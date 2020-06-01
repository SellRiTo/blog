package com.blog.blog.module.fileupload.service.impl;

import com.blog.blog.config.FileProperties;
import com.blog.blog.module.fileupload.service.FileSerrvice;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: blog
 * @description: 文件上传实现类
 * @author: txr
 * @create: 2020-05-28 15:20
 */
@Service
public class FileServiceImpl implements FileSerrvice {

    private final Path rootLocation;

    @Autowired
    public FileServiceImpl(FileProperties fileProperties){
        this.rootLocation = Paths.get(fileProperties.getLocation());
    }


    @Override
    public Map<String, Object> upload(MultipartFile file) {
        try {
            File fileDir = this.rootLocation.toFile();
            File faceFile = new File(fileDir, "face");
            Path path = faceFile.toPath();
            if (!faceFile.exists()){
                faceFile.mkdir();
            }
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
            Map<String,Object> result = new HashMap<>();
           result.put("id", UUID.randomUUID().toString().replaceAll("-",""));
           return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("上传失败");
        }
    }

    @Override
    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(this.rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("删除文件失败");
        }
    }

    @Override
    public Resource loadFileName(String fileName) {
        Path path = this.rootLocation.resolve(fileName);
        try {
            Resource fileResource = new UrlResource(path.toUri());
            if (!fileResource.exists() || !fileResource.isReadable()){
                throw  new RuntimeException("文件下载失败！");
            }
            return fileResource;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("文件下载失败！");
        }
    }
}
