package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.ImageDTO;
import com.mortl.dancenetwork.service.IStorageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/closed/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileClosedController {

  private final IStorageService storageService;

  @PostMapping("/photo-upload")
  public ResponseEntity<Void> uploadPhoto(@RequestParam("file") MultipartFile file) {
    log.info("Uploaded " + file.getOriginalFilename());
    storageService.storeUploadedImage(file);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/list-user-images")
  public ResponseEntity<List<ImageDTO>> listUserImages(){
    return ResponseEntity.ok(storageService.listUserImages());
  }
}