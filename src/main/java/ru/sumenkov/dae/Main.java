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
                                                        
                            После указания аргумента можно сразу добавить путь до директории с файлами, или сделать это позднее.
                            Пример: java -jar DBFandExcel.jar --dbftoexcel C:\\Users\\user\\DFBfiles""");
                    System.exit(0);
                }
            }
        } else {
            System.out.println("Не указан аргумент для запуска.");
            System.exit(0);
        }

        Path uploadDir = (args.length > 1) ? ProcessingPath.fixDirectoryPath(Path.of(args[1])) : ProcessingPath.requestDirectory();
        ProcessingPath.createDirectoryToSave(uploadDir);

        ProcessingFiles processingFiles = new ProcessingFiles(uploadDir, launchARG);
        new Thread(processingFiles).start();
    }
}
