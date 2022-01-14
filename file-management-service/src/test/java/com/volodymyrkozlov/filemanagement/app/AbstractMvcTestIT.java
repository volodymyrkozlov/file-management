package com.volodymyrkozlov.filemanagement.app;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.volodymyrkozlov.filemanagement.app.dto.FileMetaDto;
import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.repository.FileMetaRepository;
import com.volodymyrkozlov.filemanagement.app.repository.FileOperationRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractMvcTestIT extends AbstractTestIT {
    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected FileMetaRepository fileMetaRepository;

    @Autowired
    protected FileOperationRepository fileOperationRepository;

    @MockBean
    protected AuditorAware<String> auditorAware;

    @BeforeEach
    void init() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @SneakyThrows
    protected <T> T readJson(final MvcResult mvcResult, final Class<T> clazz) {
        return readJson(mvcResult.getResponse().getContentAsString(), clazz);
    }

    @SneakyThrows
    protected <T> T readJson(final String json, final Class<T> clazz) {
        return this.mapper.readValue(json, clazz);
    }

    protected static void assertFileMetaEntity(final FileMetaEntity fileMetaEntity,
                                               final FileMetaDto fileMetaDto) {
        assertEquals(fileMetaDto.guid(), fileMetaEntity.getGuid());
        assertEquals(fileMetaDto.bucket(), fileMetaEntity.getBucket());
        assertEquals(fileMetaDto.directory(), fileMetaEntity.getDirectory());
        assertEquals(fileMetaDto.storage(), fileMetaEntity.getStorage());
        assertEquals(fileMetaDto.contentType(), fileMetaEntity.getContentType());
        assertEquals(fileMetaDto.contentLength(), fileMetaEntity.getContentLength());
        assertEquals(fileMetaDto.path(), fullPath(fileMetaDto.bucket(), fileMetaEntity.getDirectory(), fileMetaEntity.getFileName()));
    }

    protected static void assertHeaders(final MockHttpServletResponse response,
                                      final FileMetaDto fileMetaDto) {
        assertEquals(fileMetaDto.guid(), response.getHeader("guid"));
        assertEquals(fileMetaDto.fileName(), response.getHeader("fileName"));
        assertEquals(fileMetaDto.directory(), response.getHeader("directory"));
        assertEquals(fileMetaDto.bucket(), response.getHeader("bucket"));
        assertEquals(fileMetaDto.storage().toString(), response.getHeader("storage"));
        assertEquals(fileMetaDto.path(), response.getHeader("path"));
    }

    protected static void assertFileMetaDto(final FileMetaDto fileMetaDto,
                                            final String fileName,
                                            final String directory,
                                            final String bucket,
                                            final StorageType storage,
                                            final String contentType,
                                            final Long contentLength) {
        assertEquals(fileName, fileMetaDto.fileName());
        assertEquals(directory, fileMetaDto.directory());
        assertEquals(bucket, fileMetaDto.bucket());
        assertEquals(storage, fileMetaDto.storage());
        assertEquals(contentType, fileMetaDto.contentType());
        assertEquals(contentLength, fileMetaDto.contentLength());
        assertEquals(fullPath(bucket, directory, fileName), fileMetaDto.path());
    }

    protected static String fullPath(final String bucket,
                                     final String directory,
                                     final String fileName) {
        return String.format("%s/%s/%s", bucket, directory, fileName);
    }

}
