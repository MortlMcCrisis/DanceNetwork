package com.mortl.dancenetwork.service;

import java.awt.image.BufferedImage;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

  void storeImage(MultipartFile file);

  void storeImage(BufferedImage image, String fileType, String fileName);
}
