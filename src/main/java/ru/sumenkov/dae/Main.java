package ru.sumenkov.dae;

import java.nio.file.Path;
import java.util.Map;

public class Main {
    public static void main(String[] args)  {
        try {
            // Проверяем параметры запуска
            Map<String, String> parameters = ParametersChecking.parametersChecking(args);
            // Преобразуем путь до файла в Path
            Path uploadDir = Path.of(parameters.get("uploadDir"));
            // Создаем директорию для сохранения новых файлов
            ProcessingPath.createDirectoryToSave(uploadDir);
            // Запускаем обработку
            ProcessingFiles.processingFiles(uploadDir, parameters.get("fileExtension"), parameters.get("charsetName"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
