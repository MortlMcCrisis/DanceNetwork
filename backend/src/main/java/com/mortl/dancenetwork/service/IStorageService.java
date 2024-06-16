package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.ImageDTO;
import java.awt.image.BufferedImage;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

  void storeUploadedImage(MultipartFile file);

  void storeUploadedImage(BufferedImage image, String path, String filaName, String fileType);

  List<ImageDTO> listUserImages();
}
