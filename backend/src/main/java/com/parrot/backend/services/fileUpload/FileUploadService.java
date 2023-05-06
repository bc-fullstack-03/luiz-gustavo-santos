package com.parrot.backend.services.fileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService implements IFileUploadService{
  @Autowired
  private AwsService awsService;
  public String upload(MultipartFile file, String fileName) throws Exception {
    var fileUrl = "";
    try {
      fileUrl = awsService.upload(file, fileName);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

    return fileUrl;
  }
}
