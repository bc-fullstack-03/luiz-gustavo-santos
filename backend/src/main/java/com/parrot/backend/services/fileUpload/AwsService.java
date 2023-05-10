package com.parrot.backend.services.fileUpload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class AwsService {
  @Autowired
  private AmazonS3 amazonS3;

  @Value("${app-config.api-env}")
  private String apiEnv;

  public String upload(MultipartFile file, String fileName) throws Exception {
    var fileUrl = "";
    try {
      var fileConverted = convertMultiPartFile(file);
      amazonS3.putObject(new PutObjectRequest("parrot-bucket", fileName, fileConverted).withCannedAcl(CannedAccessControlList.PublicRead));

      fileUrl = apiEnv.equals("local")
          ? "http://s3.localhost.localstack.cloud:4566"+"/"+"parrot-bucket"+"/"+fileName
          : "http://localhost:4566"+"/"+"parrot-bucket"+"/"+fileName ;
      fileConverted.delete();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

    return fileUrl;
  }

  private File convertMultiPartFile(MultipartFile file) throws IOException {
    var convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

    var fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();

    return convFile;
  }
}
