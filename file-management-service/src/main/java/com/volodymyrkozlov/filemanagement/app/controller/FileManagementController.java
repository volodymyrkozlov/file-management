package com.volodymyrkozlov.filemanagement.app.controller;

import com.volodymyrkozlov.filemanagement.app.connector.StorageConnectorsProcessor;
import com.volodymyrkozlov.filemanagement.app.dto.FileMetaDto;
import com.volodymyrkozlov.filemanagement.app.dto.web.request.UploadFileRequestDto;
import com.volodymyrkozlov.filemanagement.app.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RequestMapping(FileManagementController.ROOT)
@RestController
@RequiredArgsConstructor
public class FileManagementController {
    public static final String ROOT = "/api/v1/files";
    private final StorageConnectorsProcessor storageConnectorsProcessor;

    @Operation(summary = "Find file by guid")
    @GetMapping("/{guid}")
    public ResponseEntity<Resource> find(@PathVariable("guid") final String guid) {
        final var fileDto = storageConnectorsProcessor.getFileByGuid(guid);

        return ResponseUtils.fetchResponse(fileDto);
    }

    @Operation(summary = "Upload multipart file")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileMetaDto upload(@NotNull @Valid final UploadFileRequestDto request) {
        return storageConnectorsProcessor.uploadFile(request);
    }

}
