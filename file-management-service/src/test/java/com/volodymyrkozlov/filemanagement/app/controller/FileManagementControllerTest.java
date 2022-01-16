package com.volodymyrkozlov.filemanagement.app.controller;

import com.volodymyrkozlov.filemanagement.app.AbstractMvcTestIT;
import com.volodymyrkozlov.filemanagement.app.dto.FileMetaDto;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.repository.filter.QFileEntityFilter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileManagementControllerTest extends AbstractMvcTestIT {

    @Test
    @SneakyThrows
    void testUploadFileLocal() {
        final var fileMeta = this.uploadFileLocal();

        FileUtils.deleteDirectory(new File(fileMeta.bucket()));
    }

    @Test
    @SneakyThrows
    void testGetFileByGuidLocal() {
        //given
        final var fileMeta = this.uploadFileLocal();

        //when
        final var response = mvc.perform(get(FileManagementController.ROOT + "/" + fileMeta.guid()))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn()
            .getResponse();

        //then
        assertHeaders(response, fileMeta);
        assertArrayEquals(response.getContentAsByteArray(), new FileInputStream(fileMeta.path()).readAllBytes());
        FileUtils.deleteDirectory(new File(fileMeta.bucket()));
    }

    @SneakyThrows
    private FileMetaDto uploadFileLocal() {
        //given
        final var storage = StorageType.LOCAL;
        final var bucket = "temp-test-files-bucket";
        final var directory = "test/test1";
        final var mockMultipartFile = new MockMultipartFile("file", "file.json", "text/plain", "some".getBytes());

        when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of("MOCK_USER"));

        //when
        final var fileMetaDto = super.readJson(mvc.perform(multipart(FileManagementController.ROOT + "/multipart")
                .file(mockMultipartFile)
                .param("storage", storage.toString())
                .param("bucket", bucket)
                .param("directory", directory))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn(), FileMetaDto.class);

        //then
        assertFileMetaDto(fileMetaDto,
            mockMultipartFile.getOriginalFilename(),
            directory,
            bucket,
            StorageType.LOCAL,
            mockMultipartFile.getContentType(),
            mockMultipartFile.getSize()
        );

        final var optionalFileMetaEntity = fileMetaRepository.findOne(QFileEntityFilter.builder().guid(fileMetaDto.guid()).toPredicate());
        assertTrue(optionalFileMetaEntity.isPresent());
        final var fileMetaEntity = optionalFileMetaEntity.get();
        assertFileMetaEntity(fileMetaEntity, fileMetaDto);

        return fileMetaDto;
    }

}
