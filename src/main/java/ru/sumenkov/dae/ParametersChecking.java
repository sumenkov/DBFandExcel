package ru.sumenkov.dae;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParametersChecking {
    private ParametersChecking() {
        throw new AssertionError("Instantiating ParametersChecking class.");
    }

    /**
     * Обрабатываем аргументы запуска программы
     *
     * @param args Директория расположения файлов DBF
     *
     * @return: fileExtension - расширение файла, который планируем конвертировать
     *          charsetName - кодировка для чтения файла DBF
     *          uploadDir - путь до файла или директории с файлами
     */
    public static Map<String, String> parametersChecking(String[] args) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        if (args.length > 0) {
            switch (args[0]) {
                case "--dbftoexcel" -> parameters.put("fileExtension", "dbf");
                case "--exceltodbf" -> parameters.put("fileExtension", "xls");
                case "--saveStructureDBF" -> {
                    // Проверяем наличие аргумента с указанием пути до файла или директории
                    int findPath = Arrays.asList(args).indexOf("--path");
                    Path filePath = (findPath > 0) ? Path.of(args[findPath + 1]) : ProcessingPath.requestPath();
                    // Обработка файла
                    StructureTableDBF.saveStructure(filePath);
                    // Закрываем программу
                    System.exit(0);
                }
                default -> {
                    System.out.println("""
                            Доступные агрументы запуска:
                            --dbftoexcel - для конвертации DBF таблиц в Excel
                            --exceltodbf - для конвертации Excel таблиц в DBF
                            --saveStructureDBF - сохранение спецификация файла DBF в .ini
                                                        
                            дополнительные аргументы (не обязательные):
                            --charset - кодировка для чтения DBF
                            --path - путь до файла или директории
                                                        
                            После указания аргумента можно сразу добавить кодировку для чтения из DBF:
                            Пример: java -jar DBFandExcel.jar --dbftoexcel --charset IBM866 --path  C:\\Files_DBF
                                                        
                            По умолчанию используется кодировка CP866 (IBM866), она же DOS""");
                    System.exit(0);
                }
            }
            // Проверяем наличие аргумента с указанием кодировки
            int findCharset = Arrays.asList(args).indexOf("--charset");
            parameters.put("charsetName", (findCharset > 0) ? args[findCharset + 1] : "IBM866");
            // Проверяем наличие аргумента с указанием пути до файла или директории
            int findPath = Arrays.asList(args).indexOf("--path");
            parameters.put("uploadDir", String.valueOf((findPath > 0) ? Path.of(args[findPath + 1]) : ProcessingPath.requestPath()));
        } else {
            System.out.println("Не указан аргумент для запуска.");
            System.exit(0);
        }
        return parameters;
    }
}
