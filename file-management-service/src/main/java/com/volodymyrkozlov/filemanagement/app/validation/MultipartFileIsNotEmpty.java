package com.volodymyrkozlov.filemanagement.app.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MultipartFileIsNotEmpty.MultipartFileValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartFileIsNotEmpty {
    String message() default "Multipart file is empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MultipartFileValidator implements ConstraintValidator<MultipartFileIsNotEmpty, MultipartFile> {

        @Override
        public boolean isValid(final MultipartFile value,
                               final ConstraintValidatorContext context) {
            return value != null && !value.isEmpty();
        }
    }
}
