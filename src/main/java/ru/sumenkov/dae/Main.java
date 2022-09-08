package ru.sumenkov.dae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        Path uploadDir = requestingDirectory();
        processingFiles(uploadDir);
    }

    /**
     * Запрашиваем директорию с файлами
     */
    public static Path requestingDirectory() throws Exception {
        Path dirIn;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            dirIn = Path.of(reader.readLine());
        }
        // Если выбрали файл, исправляем путь на директорию где он лежит
        if (!Files.isDirectory(dirIn)) {
            dirIn = dirIn.getParent();
        }
        return dirIn;
    }

    /**
     * Собираем и обрабатываем файлы
     * @param uploadDir Директория расположение файлов DBF
     */
    public static void processingFiles(Path uploadDir) throws IOException {
        readerDBF readDBF = new readerDBF();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(uploadDir)) {
            for (Path file : files) {
                if (file.toString().substring(file.toString().lastIndexOf(".") + 1)
                        .equalsIgnoreCase("dbf")) {
                    readDBF.readDBFFile(file.toString());
                }
            }
        }
    }
}
