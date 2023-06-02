package com.caloriecatching.caloriecatching.config;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileUtils {

    public byte[] convertMultipartFileToBytes(MultipartFile file) throws IOException {

        return file.getBytes();
    }
}
