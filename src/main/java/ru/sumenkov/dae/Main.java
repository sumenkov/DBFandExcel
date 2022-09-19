package ru.sumenkov.dae;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        String launchARG = null;
        if (args.length > 0) {
            switch (args[0]) {
                case "--dbftoexcel" -> launchARG = "dbf";
                case "--exceltodbf" -> launchARG = "xls";
                default -> {
                    System.out.println("""
                            Доступные агрументы запуска:
                            --dbftoexcel - для конвертации DBF таблиц в Excel
                            --exceltodbf - для конвертации Excel таблиц в DBF
                                                        
                            После указания аргумента можно сразу добавить кодировку для чтения из DBF:
                            Пример: java -jar DBFandExcel.jar --dbftoexcel IBM866
                            
                            По умолчанию используется кодировка CP866 (IBM866), она же DOS""");
                    System.exit(0);
                }
            }
        } else {
            System.out.println("Не указан аргумент для запуска.");
            System.exit(0);
        }

        String charsetName = (args.length > 1) ? args[1] : "IBM866";

        Path uploadDir = ProcessingPath.requestDirectory();
        ProcessingPath.createDirectoryToSave(uploadDir);

        ProcessingFiles.processingFiles(uploadDir, launchARG, charsetName);
    }
}
