package ru.sumenkov.dae;

import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        String launchARG = null;
        if (args.length > 0) {
            switch (args[0]) {
                case "--dbftoexcel" -> launchARG = "dbf";
                case "--exceltodbf" -> launchARG = "xls";
                case "--saveStructureDBF" -> {
                    StructureTableDBF.saveStructure(ProcessingPath.requestDirectory());
                    System.exit(0);
                }
                default -> {
                    System.out.println("""
                            Доступные агрументы запуска:
                            --dbftoexcel - для конвертации DBF таблиц в Excel
                            --exceltodbf - для конвертации Excel таблиц в DBF
                            --saveStructureDBF - сохранение спецификация файла DBF в .ini
                            
                            дополнительные аргументы:
                            --charset - кодировка для чтения DBF
                            --path - путь до файла или директории
                                                        
                            После указания аргумента можно сразу добавить кодировку для чтения из DBF:
                            Пример: java -jar DBFandExcel.jar --dbftoexcel --charset IBM866
                            
                            По умолчанию используется кодировка CP866 (IBM866), она же DOS""");
                    System.exit(0);
                }
            }
            // Проверяем наличие аргумента с указанием кодировки
            int findCharset = Arrays.asList(args).indexOf("--charset");
            String charsetName = (findCharset > 0) ? args[findCharset + 1] : "IBM866";
            // Проверяем наличие аргумента с указанием пути до файла или директории
            int findPath = Arrays.asList(args).indexOf("--path");
            Path uploadDir = (findPath > 0) ? Path.of(args[findPath + 1]) : ProcessingPath.requestDirectory();
            // Создаем директорию для сохранения новых файлов
            ProcessingPath.createDirectoryToSave(uploadDir);
            // Запускаем обработку
            ProcessingFiles.processingFiles(uploadDir, launchARG, charsetName);

        } else {
            System.out.println("Не указан аргумент для запуска.");
            System.exit(0);
        }
    }
}
