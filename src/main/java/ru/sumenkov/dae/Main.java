package ru.sumenkov.dae;

import jxl.read.biff.BiffException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        String launchARG = null;
        try {
            switch (args[0]) {
                case "dbftoexcel", "exceltodbf" -> launchARG = args[0];
                default -> {
                    System.out.println("""
                            Не правильно указан аргумент запуска.
                            Доступные агрументы:
                            dbftoexcel - для конвертации DBF таблиц в Excel
                            exceltodbf - для конвертации Excel таблиц в DBF""");
                    System.exit(0);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не указан аргумент запуска");
            System.exit(0);
        }

        Path uploadDir = requestingDirectory();
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
    public static void processingFiles(Path uploadDir, String launchARG) throws IOException, BiffException {

        try (DirectoryStream<Path> files = Files.newDirectoryStream(uploadDir)) {
            for (Path file : files) {
                String substring = file.toString().substring(file.toString().lastIndexOf(".") + 1);
                if (launchARG.equals("dbftoexcel")) {
                    ReaderDBF reader = new ReaderDBF();
                    if (substring.equalsIgnoreCase("dbf")) {
                        reader.readDBFFile(file.toString());
                    }
                } else if (launchARG.equals("exceltodbf")) {
//                    ReaderExcel readerExcel = new ReaderExcel();
                    if (substring.equalsIgnoreCase("xls")) {
                        ReaderExcel reader = new ReaderExcel();
                        System.out.println("Найден файл: " + file);
                        reader.readExcel(file.toString());
                    }
                }
            }
        }
    }
}
