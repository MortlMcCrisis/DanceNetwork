package com.mortl.dancenetwork.service.impl

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class StoreServiceSpec extends Specification{

    @TempDir
    Path tempDir

    def "test store"(){
        given:
        StorageServiceImpl storageService = new StorageServiceImpl()
        storageService.root = tempDir.toString() + File.separator + "users"
        Files.createDirectory(Path.of(storageService.root))

        Path tempFile = Files.createFile(tempDir.resolve("test.txt"))
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                Files.readAllBytes(tempFile))

        multipartFile.transferTo(new File (tempDir.toString() + File.separator + "users"))

        when:
        storageService.storeImage(multipartFile)

        then:
        Files.list(Path.of(storageService.root))
                .anyMatch(file -> file.fileName == "test.txt")
    }
}
