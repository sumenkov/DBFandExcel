package ru.sumenkov.dae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProcessingPath {

    /**
     * Если выбрали файл, исправляем путь на директорию где он лежит
     *
     * @param uploadDir Директория расположения файлов
     */
    public static Path fixDirectoryPath(Path uploadDir) {
        if (Files.isRegularFile(uploadDir)) {
            uploadDir = uploadDir.getParent();
        }
        return uploadDir;
    }

    /**
     * Создаем директорию для сохранения файлов
     *
     * @param uploadDir Директория расположение файлов DBF
     */
    public static void createDirectoryToSave(Path uploadDir) throws IOException {
        Path dirOut = Path.of(uploadDir.toString() + "\\new Files");
        if (!Files.exists(dirOut)) {
           try {
               Files.createDirectory(dirOut);
           }catch (java.nio.file.NoSuchFileException e) {
               System.out.println("Директория с файлами указана не верно, проверти написание пути. Смотрите --help");
               System.exit(0);
           }
        }
    }

    /**
     * Запрашиваем директорию с файлами
     */
    public static Path requestingDirectory() throws Exception {
        System.out.println("Укажите полный путь к директории с файлами:");
        Path dirIn;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            dirIn = Path.of(reader.readLine());
        }
        return dirIn;
    }
}
