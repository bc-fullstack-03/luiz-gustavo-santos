package com.parrot.backend.services.fileUpload;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
  public String upload(MultipartFile file, String fileName) throws Exception;
}
