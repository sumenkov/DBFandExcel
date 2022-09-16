package ru.sumenkov.dae;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProcessingFiles implements Runnable {
    private final Path uploadDir;
    private final String launchARG;

    /**
     * Собираем и обрабатываем файлы
     *
     * @param uploadDir Директория расположения файлов DBF
     * @param launchARG код агрумента выбора обработки, полученного от пользователя
     */
    public ProcessingFiles(Path uploadDir, String launchARG) {
        this.uploadDir = uploadDir;
        this.launchARG = launchARG;
    }

    @Override
    public void run() {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(uploadDir)) {
            for (Path file : files) {
                String substring = file.toString().substring(file.toString().lastIndexOf(".") + 1);
                if (Files.isRegularFile(file) && substring.equalsIgnoreCase(launchARG)) {
                    System.out.println("Конвертируем файл: " + file.getFileName());
                    String filePath = file.toString();
                    switch (launchARG) {
                        case "dbf" -> {
                            List<Object> dataDBF = ReaderDBF.readDBF(filePath);
                            WriterExcel.saveFileExcel(filePath, dataDBF);
                        }
                        case "xls" -> {
                            List<Object> dataExcel = ReaderExcel.readExcel(filePath);
                            WriterDBF.saveFileDBF(filePath, dataExcel);
                        }
                    }
                }
            }
        } catch (WriteException | BiffException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
