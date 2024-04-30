package com.literandltx.assignment.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtil {
    private static final String tempFileLocation = "src/main/resources/targetFile.tmp";

    public static File toFile(final MultipartFile multipartFile) {
        File file;

        try {
            file = new File(tempFileLocation);
            writeToFile(file, multipartFile.getBytes());
        } catch (final IOException e) {
            throw new RuntimeException("Error creating or writing to file: " + tempFileLocation, e);
        }

        return file;
    }

    private static void writeToFile(final File file, final byte[] content) throws IOException {
        try (final OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content);
        }
    }
}
