package com.sunglowsys.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {


        // File name
        String name = file.getOriginalFilename();

        // to generate random file name
         String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        // Full Path
        String filePath =path+ File.separator+fileName;

        // create folder if not exist
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // file copy

            Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;



    }
}
