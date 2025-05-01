package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.ImageDTO;
import com.mortl.dancenetwork.service.IStorageService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/closed/v1/files")
public class FileClosedController
{

  private static final Logger log = LoggerFactory.getLogger(FileClosedController.class);

  private final IStorageService storageService;

  public FileClosedController(IStorageService storageService)
  {
    this.storageService = storageService;
  }

  //used in old frotend
  @Deprecated(forRemoval = true)
  @PostMapping("/photo-upload")
  public ResponseEntity<Void> uploadPhoto(@RequestParam("file") MultipartFile file)
  {
    storageService.storeUploadedImage(file);
    log.info("Uploaded {}", file.getOriginalFilename());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/photo-upload-new")
  public ResponseEntity<String> uploadPhotoNew(@RequestParam("file") MultipartFile file)
  {
    String filePath = storageService.storeUploadedImageNew(file);
    log.info("Uploaded {}", file.getOriginalFilename());
    return ResponseEntity.ok(filePath);
  }

  @GetMapping("/list-user-images")
  public ResponseEntity<List<ImageDTO>> listUserImages()
  {
    return ResponseEntity.ok(storageService.listUserImages());
  }
}
