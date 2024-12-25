package cl.viceav.blog.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ValidFile {
  String message() default "Invalid file";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
