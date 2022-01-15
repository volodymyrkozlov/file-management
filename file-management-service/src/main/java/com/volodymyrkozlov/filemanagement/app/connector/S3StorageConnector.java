package com.volodymyrkozlov.filemanagement.app.connector;

import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import com.volodymyrkozlov.filemanagement.app.enums.StorageConfigOption;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.exception.InvalidArgumentException;
import com.volodymyrkozlov.filemanagement.app.property.FileManagementProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class S3StorageConnector implements StorageConnector {
    private final Map<String, S3Client> regionS3ClientMap;
    private final Map<String, String> bucketRegionsMap;

    public S3StorageConnector(final FileManagementProperties fileManagementProperties) {
        final var s3ConfigOptions = fileManagementProperties.getStorages()
            .get(StorageType.S3);
        this.regionS3ClientMap = s3ConfigOptions
            .stream()
            .map(FileManagementProperties.StorageConfigProperty::getOptions)
            .collect(Collectors.toMap(options -> options.get(StorageConfigOption.BUCKET_REGION), options -> {
                final var credentials = AwsBasicCredentials.create(
                    options.get(StorageConfigOption.ACCESS_KEY_ID), options.get(StorageConfigOption.ACCESS_KEY_SECRET)
                );

                return S3Client.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
                    .region(Region.of(options.get(StorageConfigOption.BUCKET_REGION)))
                    .build();
            }));
        this.bucketRegionsMap = s3ConfigOptions
            .stream()
            .collect(Collectors.toMap(FileManagementProperties.StorageConfigProperty::getBucket,
                config -> config.getOptions().get(StorageConfigOption.BUCKET_REGION)));

    }

    @Override
    public StorageType getStorageType() {
        return StorageType.S3;
    }

    @Override
    public InputStream getFileRequest(final FileMetaEntity fileMeta) {
        final var fileName = fileMeta.getFileName();
        try {
            final var bucket = fileMeta.getBucket();
            final var bucketRegion = bucketRegionsMap.get(bucket);
            return regionS3ClientMap.get(bucketRegion).getObject(GetObjectRequest.builder()
                .bucket(bucket)
                .key(fetchFileKey(fileMeta.getDirectory(), fileName))
                .build());
        } catch (final S3Exception ex) {
            throw new InvalidArgumentException(ex);
        }
    }

    @Override
    public void uploadFileRequest(final String fileName,
                                  final String directory,
                                  final String bucket,
                                  final String fullPath,
                                  final InputStream inputStream,
                                  final Long contentLength,
                                  final String contentType) {
        try {
            final var bucketRegion = bucketRegionsMap.get(bucket);
            regionS3ClientMap.get(bucketRegion).putObject(PutObjectRequest.builder()
                .bucket(bucket)
                .key(fetchFileKey(directory, fileName))
                .contentLength(contentLength)
                .contentType(contentType)
                .build(), RequestBody.fromInputStream(inputStream, contentLength));

            inputStream.close();
        } catch (final IOException | S3Exception e) {
            throw new InvalidArgumentException(e);
        }
    }

    private static String fetchFileKey(final String targetDirectory,
                                       final String fileName) {
        return String.format("%s/%s", targetDirectory, fileName);
    }
}
