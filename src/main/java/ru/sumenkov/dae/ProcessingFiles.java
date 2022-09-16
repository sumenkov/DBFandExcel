package ru.sumenkov.dae;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ProcessingFiles {
    private ProcessingFiles() {
        throw new AssertionError("Instantiating ProcessingFiles class.");
    }

    /**
     * Собираем и обрабатываем файлы
     *
     * @param uploadDir Директория расположения файлов DBF
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
                    } else {
                        System.out.println(file.getFileName() + " ... Не правильное расширение.");
                    }
                }
            }
        }
    }
}
