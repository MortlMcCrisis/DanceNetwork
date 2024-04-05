package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.service.IStorageService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class StorageServiceImpl implements IStorageService {

  private String root = "../frontend/public/users";

  private final Path rootLocation;

  public StorageServiceImpl() {
    this.rootLocation = Paths.get(
        Paths.get("").toAbsolutePath() + File.separator + root)
        .normalize();
  }

  @Override
  public void storeImage(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        //throw new StorageException("Failed to store empty file.");
      }
      Path destinationFile = this.rootLocation.resolve(
              Paths.get(file.getOriginalFilename()))
          .normalize().toAbsolutePath();
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to store file.", e);
    }
  }

  @Override
  public void storeImage(BufferedImage image, String fileType, String fileName) {
    try {
      ImageIO.write(image, fileType, new File(Paths.get(
              Paths.get("").toAbsolutePath() + "/../frontend/public/qrcodes/" + fileName)
          .normalize().toString()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
