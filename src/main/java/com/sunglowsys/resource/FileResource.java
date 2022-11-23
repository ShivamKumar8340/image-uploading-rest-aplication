package com.sunglowsys.resource;

import com.sunglowsys.payLoad.FileResponse;
import com.sunglowsys.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileResource {

    private List<String> extensions = Arrays.asList("jpg", "png", "gif");




    @Value("${project.image}")
    private String path;

    private final FileService fileService;
    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image) {


        String fileName;
        if (!extensions.contains(getExtension(image.getOriginalFilename()))) {
            return new ResponseEntity<>(new FileResponse(
                    image.getOriginalFilename(),
                    "this type of format is not supported"),HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            fileName = fileService.uploadImage(path, image);
        } catch (IOException e) {
          e.printStackTrace();
            return new ResponseEntity<FileResponse>(new FileResponse(null,
                    "image  is not uploaded  !!!!!!!!!"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<FileResponse>(new FileResponse(fileName,
                "image uploaded successFully !!!!!!!!!"), HttpStatus.OK);
    }


    // for extension validation
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
}
