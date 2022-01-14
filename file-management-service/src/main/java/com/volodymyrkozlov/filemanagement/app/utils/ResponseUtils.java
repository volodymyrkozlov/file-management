package com.volodymyrkozlov.filemanagement.app.utils;


import com.volodymyrkozlov.filemanagement.app.dto.FileDto;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtils {
    public static ResponseEntity<Resource> fetchResponse(final FileDto fileDto) {
        final var headers = new HttpHeaders();
        final var fileName = fileDto.fileName();
        final var contentType = fileDto.contentType();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileDto.fileName()));
        headers.add(HttpHeaders.CONTENT_TYPE, String.format("%s; charset=utf-8", contentType));
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileDto.contentLength()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("guid", fileDto.guid());
        headers.add("fileName", fileName);
        headers.add("directory", fileDto.directory());
        headers.add("bucket", fileDto.bucket());
        headers.add("storage", fileDto.storage().toString());
        headers.add("path", fileDto.path());

        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.valueOf(contentType))
            .body(new InputStreamResource(fileDto.inputStream()));
    }
}
