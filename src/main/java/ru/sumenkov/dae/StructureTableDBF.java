package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.ini4j.Wini;

import java.io.*;
import java.nio.file.Path;

public final class StructureTableDBF {
    private StructureTableDBF() {
        throw new AssertionError("Instantiating StructureTableDBF class");
    }

    /**
     * Описания структуры DBF таблицы
     *
     * @param namesOfColumns имена столбцов, первая строка из файла Excel
     * @return Спецификация полей в файле DBF и кодировка для записи, указанная в .ini
     */
    public static Object[] readStructure(Object[] namesOfColumns) {
        try {
//            Раскоментировать для сборки JAR файла:
//                File currentDirectory = new File(new File(Main.class
//                    .getProtectionDomain()
//                    .getCodeSource()
//                    .getLocation()
//                    .toURI()).getParent());
//            Wini ini = new Wini(new File(currentDirectory + "\\StructureTableDBF.ini"));

            Wini ini = new Wini(new File("StructureTableDBF.ini"));
            int numberOfColumns = ini.get("NUMBER_OF_COLUMNS", "NUMBER", int.class);

            DBFField[] fields = new DBFField[numberOfColumns];

            for (int i = 0; i < numberOfColumns; i++) {
                String field = "FIELD_" + (i + 1);
                fields[i] = new DBFField();
                fields[i].setName(namesOfColumns[i].toString());
                fields[i].setType(typeMatching(ini.get(field,"TYPE", String.class)));
                fields[i].setLength(ini.get(field, "LENGTH", int.class));

                int decimalCount = ini.get(field, "DECIMAL_COUNT", int.class);
                if (decimalCount != 0) fields[i].setDecimalCount(decimalCount);
            }

            Object[] result = new Object[2];
            result[0] = fields;
            result[1] = ini.get("ENCODING","CHARSET", String.class);

            return result;
//      Раскоментировать для сборки JAR файла:
//        } catch (IOException | URISyntaxException e) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveStructure(Path filePath) throws IOException {
        System.out.println("В разработке...");
        System.out.println(filePath.toFile());
        try (InputStream inputStream = new FileInputStream(filePath.toFile())) {
            DBFReader reader = new DBFReader(inputStream);
            System.out.println("Charset: " + reader.getCharset());

            int numberOfFields = reader.getFieldCount();
            System.out.println("NumOfColumn: " + reader.getFieldCount());

            for (int i = 0; i < numberOfFields; i++) {
                System.out.println(reader.getField(i).getName());
                System.out.println(reader.getField(i).getType());
                System.out.println(reader.getField(i).getLength());
                System.out.println(reader.getField(i).getDecimalCount());
            }
        }
    }

    /**
     * Спопоставленеи типов
     *
     * @param type наименование типа из файла .ini
     *
     * @return тип в формате DBFDataType
     */
    private static DBFDataType typeMatching(String type) {
        return switch (type) {
            case "CHARACTER" -> DBFDataType.CHARACTER;
            case "NUMERIC" -> DBFDataType.NUMERIC;
            case "FLOATING_POINT" -> DBFDataType.FLOATING_POINT;
            case "LOGICAL" -> DBFDataType.LOGICAL;
            case "DATE" -> DBFDataType.DATE;
            default -> throw new IllegalStateException("Unexpected value");
        };
    }
}
