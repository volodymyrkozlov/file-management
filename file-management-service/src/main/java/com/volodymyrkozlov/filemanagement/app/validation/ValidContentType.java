package com.volodymyrkozlov.filemanagement.app.validation;

import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidContentType.ContentTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidContentType {
    String message() default "Invalid content type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ContentTypeValidator implements ConstraintValidator<ValidContentType, String> {

        @Override
        public boolean isValid(final String contentType,
                               final ConstraintValidatorContext constraintValidatorContext) {
            try {
                MediaType.parseMediaType(contentType);
                return true;
            } catch (final InvalidMediaTypeException ex) {
                return false;
            }
        }
    }

}
