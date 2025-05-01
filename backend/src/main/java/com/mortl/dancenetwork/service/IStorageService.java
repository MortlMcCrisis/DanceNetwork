package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.ImageDTO;
import java.awt.image.BufferedImage;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService
{

  //used in old frontend
  @Deprecated(forRemoval = true)
  void storeUploadedImage(MultipartFile file);

  String storeUploadedImageNew(MultipartFile file);

  void storeUploadedImage(BufferedImage image, String path, String filaName, String fileType);

  List<ImageDTO> listUserImages();
}
