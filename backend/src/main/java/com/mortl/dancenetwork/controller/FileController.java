package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.service.StorageService;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

  private final StorageService storageService;

  @PostMapping("/photo-upload")
  public ResponseEntity uploadPhoto(@RequestParam("file") MultipartFile file) {
    log.info("Uploaded " + file.getOriginalFilename());
    storageService.store(file);
    return ResponseEntity.ok().build();
  }
}
