package ru.sumenkov.dae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ProcessingPath {
    /**
     * char: 92 равно знаку '/'
     */
    public static final int SLASH_CHARACTER = 92;
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
    public static void createDirectoryToSave(Path uploadDir) throws IOException {
        Path dirOut = Path.of(uploadDir.toString() + "\\new Files");
        if (!Files.exists(dirOut)) {
           try {
               Files.createDirectory(dirOut);
           // нодо обсудить...
           }catch (java.nio.file.NoSuchFileException e) {
               System.out.println("Директория с файлами указана не верно, проверти написание пути.");
               System.exit(0);
           }
        }
    }

    /**
     * Запрашиваем директорию с файлами
     */
    public static Path requestDirectory() throws Exception {
        System.out.println("Укажите полный путь к директории с файлами:");

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
