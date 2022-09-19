package ru.sumenkov.dae;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

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
     * @param launchARG код агрумента выбора обработки, полученного от пользователя
     */
    public static void processingFiles(Path uploadDir, String launchARG, String charsetName) {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(uploadDir)) {
            for (Path file : files) {
                String substring = file.toString().substring(file.toString().lastIndexOf(".") + 1);
                if (Files.isRegularFile(file) && substring.equalsIgnoreCase(launchARG)) {
                    System.out.println("Конвертируем файл: " + file.getFileName());
                    RunProcessing runProcessing = new RunProcessing(launchARG, file.toString(), charsetName);
                    new Thread(runProcessing).start();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обработка каждого файла в отдельном потоке
     *
     * @param launchARG код агрумента выбора обработки, полученного от пользователя
     * @param filePath полный путь файла
     */
    private record RunProcessing(String launchARG, String filePath, String charsetName) implements Runnable {
        @Override
        public void run() {
            try {
                switch (launchARG) {
                    case "dbf" -> {
                        List<Object> dataDBF = ReaderDBF.readDBF(filePath, charsetName);
                        WriterExcel.saveFileExcel(filePath, dataDBF);
                    }
                    case "xls" -> {
                        List<Object> dataExcel = ReaderExcel.readExcel(filePath);
                        WriterDBF.saveFileDBF(filePath, dataExcel);
                    }
                }
            } catch (BiffException | WriteException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
