package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.service.IStorageService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class StorageServiceImpl implements IStorageService {

  private final Path rootLocation;

  public StorageServiceImpl() {
    this.rootLocation = Paths.get(
        Paths.get("").toAbsolutePath() + "/../frontend/public/users")
        .normalize();
  }

  @Override
  public void store(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        //throw new StorageException("Failed to store empty file.");
      }
      Path destinationFile = this.rootLocation.resolve(
              Paths.get(file.getOriginalFilename()))
          .normalize().toAbsolutePath();
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile,
            StandardCopyOption.REPLACE_EXISTING);
      }
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to store file.", e);
    }
  }

}
