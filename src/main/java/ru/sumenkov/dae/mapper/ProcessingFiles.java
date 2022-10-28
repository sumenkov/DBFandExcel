package ru.sumenkov.dae.mapper;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import ru.sumenkov.dae.repository.ReaderDBF;
import ru.sumenkov.dae.repository.ReaderExcel;
import ru.sumenkov.dae.service.WriterDBF;
import ru.sumenkov.dae.service.WriterExcel;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class ProcessingFiles {

    private ProcessingFiles() {
        throw new AssertionError("Instantiating ProcessingFiles class.");
    }

    /**
     * Собираем и обрабатываем файлы
     *
     * @param uploadDir Директория расположения файлов DBF
     * @param fileExtension код аргумента выбора обработки, полученного от пользователя
     */
    public static void processingFiles(Path uploadDir, String fileExtension, String charsetName) throws IOException {
        // Создаем директорию для сохранения новых файлов
        ProcessingPath.createDirectoryToSave(uploadDir);
        // Если указан один файл
        if (Files.isRegularFile(uploadDir)) {
            String filePath = uploadDir.toFile().toString();
            String substring = filePath.substring(filePath.lastIndexOf(".") + 1);
            if (substring.equalsIgnoreCase(fileExtension)) {
                System.out.println("Конвертируем файл: " + uploadDir.getFileName());
                runProcessing runProcessing = new runProcessing(fileExtension, filePath, charsetName);
                new Thread(runProcessing).start();
            }
        }
        // Если указали директорию
        else {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(uploadDir)) {
                for (Path file : files) {
                    String substring = file.toString().substring(file.toString().lastIndexOf(".") + 1);
                    if (Files.isRegularFile(file) && substring.equalsIgnoreCase(fileExtension)) {
                        System.out.println("Конвертируем файл: " + file.getFileName());
                        runProcessing runProcessing = new runProcessing(fileExtension, file.toString(), charsetName);
                        new Thread(runProcessing).start();
                    }
                }
            }
        }
    }

    /**
     * Обработка каждого файла в отдельном потоке
     *
     * @param fileExtension код аргумента выбора обработки, полученного от пользователя
     * @param filePath полный путь файла
     */
    private record runProcessing(String fileExtension, String filePath, String charsetName) implements Runnable {
        @Override
        public void run() {
            try {
                switch (fileExtension) {
                    case "dbf" -> {
                        List<Object> dataDBF = new ReaderDBF().readDBF(filePath, charsetName);
                        new WriterExcel().saveFileExcel(filePath, dataDBF);
                    }
                    case "xls" -> {
                        List<Object> dataExcel = new ReaderExcel().readExcel(filePath);
                        new WriterDBF().saveFileDBF(filePath, dataExcel, charsetName);
                    }
                }
            } catch (BiffException | WriteException | IOException e) {
                System.out.println("Ошибка обработки файла: " + filePath);
                throw new RuntimeException(e);
            }
        }
    }
}
