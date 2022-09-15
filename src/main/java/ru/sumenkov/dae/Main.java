package ru.sumenkov.dae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        String launchARG = null;
        Path uploadDir = null;
        try {
            switch (args[0]) {
                case "--dbftoexcel" -> {
                    launchARG = "dbf";
                    uploadDir = (args.length > 1) ? Path.of(args[1]) : requestingDirectory();
                    // Если выбрали файл, исправляем путь на директорию где он лежит
                    if (!Files.isDirectory(uploadDir)) {
                        uploadDir = uploadDir.getParent();
                    }
                    // Создаем директорию для сохранения файлов Excel
                    Path dirOut = Path.of(uploadDir.toString() + "\\xls");
                    if (!Files.exists(dirOut)) {
                        Files.createDirectory(dirOut);
                    }
                }
                case "--exceltodbf" -> {
                    launchARG = "xls";
                    uploadDir = (args.length > 1) ? Path.of(args[1]) : requestingDirectory();
                    // Если выбрали файл, исправляем путь на директорию где он лежит
                    if (!Files.isDirectory(uploadDir)) {
                        uploadDir = uploadDir.getParent();
                    }
                    // Создаем директорию для сохранения файлов Excel
                    Path dirOut = Path.of(uploadDir.toString() + "\\dbf");
                    if (!Files.exists(dirOut)) {
                        Files.createDirectory(dirOut);
                    }
                }
                default -> {
                    System.out.println("""
                            Не правильно указан аргумент запуска.
                            Доступные агрументы:
                            --bftoexcel - для конвертации DBF таблиц в Excel
                            --exceltodbf - для конвертации Excel таблиц в DBF
                                                        
                            После указания аргумента можно сразу добавить путь до директории с файлами, или сделать это позднее.
                            Пример: java -jar DBFandExcel.jar --dbftoexcel C:\\Users\\user\\DFBfiles""");
                    System.exit(0);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не указан аргумент запуска. Смотрите --help");
            System.exit(0);
        }

        processingFiles(uploadDir, launchARG);
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

    /**
     * Собираем и обрабатываем файлы
     *
     * @param uploadDir Директория расположение файлов DBF
     * @param launchARG код агрумента выбора обработки, полученного от пользователя
     */
    public static void processingFiles(Path uploadDir, String launchARG) throws IOException {

        try (DirectoryStream<Path> files = Files.newDirectoryStream(uploadDir)) {
            for (Path file : files) {
                if (Files.isRegularFile(file)) {
                    String substring = file.toString().substring(file.toString().lastIndexOf(".") + 1);
                    if (substring.equalsIgnoreCase(launchARG)) {
                        System.out.println("Конвертируем файл: " + file.getFileName());
                        switch (launchARG) {
                            case "dbf" -> {
                                ReaderDBF readerDBF = new ReaderDBF(file.toString());
                                new Thread(readerDBF).start();
                            }
                            case "xls" -> {
                                ReaderExcel readerExcel = new ReaderExcel(file.toString());
                                new Thread(readerExcel).start();
                            }
                        }
                    } else System.out.println(file.getFileName() + " ... Не правильно расширение.");
                }
            }
        }
    }
}
