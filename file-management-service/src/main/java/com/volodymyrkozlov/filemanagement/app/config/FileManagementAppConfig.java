package com.volodymyrkozlov.filemanagement.app.config;

import com.volodymyrkozlov.filemanagement.app.property.FileManagementProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FileManagementProperties.class)
public class FileManagementAppConfig {
}
