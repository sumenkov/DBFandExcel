package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReaderDBF {

    /**
     * Считать данные из файла DBF.
     *
     * @param filePath Расположение файла DBF
     * @return массив данных, полученных из файла
     */
    public List<Object> readDBF(String filePath, String charsetName) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            DBFReader reader = new DBFReader(inputStream, Charset.forName(charsetName));
            // Собираем название столбцов
            int numberOfFields = reader.getFieldCount();
            List<String> headName = new ArrayList<>();
            for (int i = 0; i < numberOfFields; i++) {
                String head = reader.getField(i).getName() + ","
                            + typeMatching(String.valueOf(reader.getField(i).getType())) + ","
                            + reader.getField(i).getLength() + ","
                            + reader.getField(i).getDecimalCount();
                headName.add(head);
            }
            // Создаем массив для хранения данных
            List<Object> data = new ArrayList<>();
            // Добавляем заголовки столбцов
            data.add(headName.toArray());
            // Чтение данных и добавление их в массив
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord ()) != null) {
                data.add(rowObjects);
            }

            return data;
        }
    }

    /**
     * Сопоставление типов
     *
     * @param type наименование типа из файла DBF
     *
     * @return тип в виде символа
     */
    private String typeMatching(String type) {
        return switch (type) {
            case "CHARACTER" -> "C";
            case "NUMERIC" -> "N";
            case "FLOATING_POINT" -> "F";
            case "LOGICAL" -> "L";
            case "DATE" -> "D";
            case "CURRENCY" -> "Y";
            case "LONG" -> "I";
            case "TIMESTAMP" -> "T";
            case "TIMESTAMP_DBASE7" -> "@";
            case "AUTOINCREMENT" -> "+";
            case "MEMO" -> "M";
            case "BINARY" -> "B";
            case "BLOB" -> "W";
            case "GENERAL_OLE" -> "G";
            case "PICTURE" -> "P";
            case "VARBINARY" -> "Q";
            case "VARCHAR" -> "V";
            case "DOUBLE" -> "O";
            default -> throw new IllegalStateException("Неожиданное значение");
        };
    }
}
