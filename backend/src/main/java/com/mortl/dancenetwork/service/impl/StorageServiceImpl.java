package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.ImageDTO;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.service.IStorageService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.Util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageServiceImpl implements IStorageService
{

  private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

  private String root = "../frontend/public/upload";
  private final Path rootLocation;

  private IUserService userService;

  public StorageServiceImpl(IUserService userService)
  {
    this.userService = userService;

    this.rootLocation = Paths.get(
            Paths.get("").toAbsolutePath() + File.separator + root)
        .normalize();
  }

  @Override
  public void storeUploadedImage(MultipartFile file)
  {
    try
    {
      if (file.isEmpty())
      {
        //throw new StorageException("Failed to store empty file.");
      }
      User user = userService.getCurrentUser().get();
      Path destinationFile = getUserImagePath().resolve(Paths.get(file.getOriginalFilename()))
          .normalize().toAbsolutePath();
      try (InputStream inputStream = file.getInputStream())
      {
        log.info("Storing file {} for user {} into directory {} ", file.getOriginalFilename(),
            user.getUsername(), destinationFile);
        Files.createDirectories(destinationFile);
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException("Failed to store uploaded file.", e);
    }
  }

  @Override
  public String storeUploadedImageNew(MultipartFile file)
  {
    try
    {
      String uploadDirPath = System.getProperty("user.dir") + File.separator + "uploads";
      File uploadDir = new File(uploadDirPath);
      if (!uploadDir.exists())
      {
        boolean created = uploadDir.mkdirs();
        if (!created)
        {
          throw new IOException("Could not create upload directory: " + uploadDirPath);
        }
      }

      User user = userService.getCurrentUser().get();
      File userPath = new File(
          uploadDirPath + File.separator + Util.calculateMD5(user.getUsername()));
      if (!userPath.exists())
      {
        boolean created = userPath.mkdirs();
        if (!created)
        {
          throw new IOException("Could not create user directory: " + userPath.getAbsolutePath());
        }
      }
      String filePath = Util.calculateMD5(user.getUsername()) + File.separator
          + file.getOriginalFilename();
      file.transferTo(new File(userPath + File.separator + file.getOriginalFilename()));
      return ("uploads" + File.separator + filePath).replaceAll("\\\\", "/");
    }
    catch (IOException e)
    {
      throw new RuntimeException("Failed to store uploaded file.", e);
    }
  }

  @Override
  public void storeUploadedImage(BufferedImage image, String path, String fileName, String fileType)
  {
    try
    {
      ImageIO.write(image, fileType, new File(Paths.get(
              Paths.get("").toAbsolutePath() + "/../frontend/public/" + path + "/" + fileName)
          .normalize().toString()));
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<ImageDTO> listUserImages()
  {
    try
    {
      Path userImagePath = getUserImagePath();

      if (Files.exists(userImagePath) && Files.isDirectory(userImagePath))
      {
        try (Stream<Path> paths = Files.list(userImagePath))
        {
          return paths.map(image -> new ImageDTO(
                  "/upload/" + Util.calculateMD5(userService.getCurrentUser().get().getUsername())
                      + File.separator + image.getFileName()))
              .collect(Collectors.toList());
        }
      }
      else
      {
        return List.of();
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException("Failed to list files for the user.", e);
    }
  }

  @Deprecated
  private Path getUserImagePath()
  {
    User user = userService.getCurrentUser().get();
    return rootLocation.resolve(Util.calculateMD5(user.getUsername()));
  }
}
