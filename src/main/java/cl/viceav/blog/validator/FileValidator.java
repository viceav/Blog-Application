package cl.viceav.blog.validator;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if (file == null || file.isEmpty()) {
      return false;
    }

    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("text/")) {
      return false;
    }

    return true;
  }
}
