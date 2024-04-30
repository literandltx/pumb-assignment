package com.literandltx.assignment.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import java.io.File;

@ExtendWith(MockitoExtension.class)
class FileUtilTest {
    @Mock
    private MockMultipartFile mockMultipartFile;

    @BeforeEach
    void setUp() {
        mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "This is a test content".getBytes());
    }

    @Test
    void toFile_SuccessfullyCreatesFile() {
        // When
        File file = FileUtil.toFile(mockMultipartFile);

        // Then
        assertEquals("src/main/resources/targetFile.tmp", file.getPath());
        assertEquals("targetFile.tmp", file.getName());

        file.delete();
    }
}