package com.volodymyrkozlov.filemanagement.app.validation;

import com.volodymyrkozlov.filemanagement.app.dto.web.request.UploadFileRequestDto;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Constraint(validatedBy = BucketExists.BucketExistenceConstraintValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BucketExists {
    String message() default "Bucket is not registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class BucketExistenceConstraintValidator implements ConstraintValidator<BucketExists, UploadFileRequestDto> {
        private final Map<StorageType, BucketExistenceValidator> bucketExistenceValidators;

        public BucketExistenceConstraintValidator(final Collection<BucketExistenceValidator> validators) {
            this.bucketExistenceValidators = validators
                .stream()
                .collect(Collectors.toMap(BucketExistenceValidator::getStorageType, Function.identity()));
        }

        @Override
        public boolean isValid(final UploadFileRequestDto value,
                               final ConstraintValidatorContext context) {
            final var storage = value.storage();
            if (this.bucketExistenceValidators.containsKey(storage)) {
                return bucketExistenceValidators.get(storage).isBucketExist(value.bucket());
            }

            return false;
        }
    }
}
