package cl.viceav.blog.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.viceav.blog.exceptions.FileException;

@Service
public class FileService {
  private final Path rootLocation;

  public FileService(@Value("${file.upload-dir}") String uploadDir) {
    this.rootLocation = Paths.get(uploadDir);
  }

  public Map<String, String> store(MultipartFile file) throws FileException {
    String fileName = file.getOriginalFilename();
    String fileExtension = fileName.substring(fileName.lastIndexOf("."));
    String newFileName = UUID.randomUUID().toString() + fileExtension;

    Path destinationFile = this.rootLocation.resolve(Paths.get(newFileName)).normalize().toAbsolutePath();

    try {
      Files.copy(file.getInputStream(), destinationFile);
    } catch (IOException e) {
      throw new FileException("Failed to store file " + newFileName + "\n" + e.toString());
    }

    Map<String, String> response = Map.of("fileName", newFileName, "route", destinationFile.toString());
    return response;
  }

  public void delete(String fileName) throws FileException {
    Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

    try {
      Files.delete(destinationFile);
    } catch (IOException e) {
      throw new FileException("Failed to delete file " + fileName + "\n" + e.toString());
    }
  }
}
