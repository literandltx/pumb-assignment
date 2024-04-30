package com.literandltx.assignment.service.animal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.literandltx.assignment.exception.custom.UnsupportedFileExtensionException;
import com.literandltx.assignment.repository.AnimalRepository;
import com.literandltx.assignment.service.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class AnimalServiceV1Test {

    private AnimalServiceV1 animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private Parser csvParser;

    @Mock
    private Parser xmlParser;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        animalService = new AnimalServiceV1(animalRepository, null, null, csvParser, xmlParser, null);
    }

    @Test
    public void testUploadWithCSVFile() {
        // ??
    }

    @Test
    public void testUploadWithXMLFile() {
        // ??
    }

    @Test
    public void testUploadWithUnsupportedFile() {
        // Given
        multipartFile = new MockMultipartFile("file", new byte[1]);
        when(multipartFile.isEmpty()).thenReturn(false);

        // When
        UnsupportedFileExtensionException exception = Assertions.assertThrows(UnsupportedFileExtensionException.class,
                () -> animalService.upload(multipartFile)
        );

        // Then
        assertEquals("Invalid multipartFile format. Only .csv and .xml files are supported", exception.getMessage());
    }

    @Test
    public void testUploadWithEmptyFile() {
        // Given
        when(multipartFile.isEmpty()).thenReturn(true);

        // When
        UnsupportedFileExtensionException exception = org.junit.jupiter.api.Assertions.assertThrows(UnsupportedFileExtensionException.class,
                () -> animalService.upload(multipartFile)
        );

        // Then
        assertEquals("File is empty", exception.getMessage());
        }

    @Test
    public void testUploadWithNullFile() {
        // When
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> animalService.upload(null)
        );

        // Then
        assertEquals("File cannot be null", exception.getMessage());
    }

}
