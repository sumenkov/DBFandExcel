package ru.sumenkov.dae.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ProcessingPath {
    /**
     * char: 92 - равно знаку '/'
     */
    private static final int SLASH_CHARACTER = 92;
    private ProcessingPath() {
        throw new AssertionError("Instantiating ProcessingPath class.");
    }

    /**
     * Если выбрали файл, исправляем путь на директорию где он лежит
     *
     * @param uploadDir Директория расположения файлов
     */
    public static Path fixDirectoryPath(Path uploadDir) {
        if (Files.isRegularFile(uploadDir)) {
            return uploadDir.getParent();
        }
        return uploadDir;
    }

    /**
     * Создаем директорию для сохранения файлов
     *
     * @param uploadDir Директория расположение файлов DBF
     */
    public static void createDirectoryToSave(Path uploadDir) {
        // Если путь до файла, берем каталог в котором он лежит
        if (!Files.isDirectory(uploadDir)) uploadDir = uploadDir.getParent();
        // Создаем новый каталог
        Path dirOut = Path.of(uploadDir.toString() + "\\new Files");
        new File(dirOut.toString()).mkdir();
    }

    /**
     * Запрашиваем директорию с файлами
     */
    public static Path requestPath() throws Exception {
        System.out.println("Укажите полный путь к директории с файлами или к конкретному файлу:");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return Path.of(reader.readLine());
        }
    }

    /**
     * Получаем имя файла из полученного пути
     *
     * @param filePath полный путь до файла
     */
    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf(SLASH_CHARACTER) + 1, filePath.lastIndexOf("."));
    }
}
