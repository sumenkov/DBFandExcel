package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class ReaderDBF {
    private ReaderDBF() {
        throw new AssertionError("Instantiating ReaderDBF class.");
    }

    /**
     * Считать данные из файла DBF.
     *
     * @param filePath Расположение файла DBF
     * @return массив данных, полученных из файла
     */

    public static List<Object> readDBF(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            DBFReader reader = new DBFReader(inputStream, Charset.forName("Cp866"));

            // Собираем насвание столбцов
            int numberOfFields = reader.getFieldCount();
            List<String> headName = new ArrayList<>();
            for (int i = 0; i < numberOfFields; i++) {
                headName.add(reader.getField(i).getName());
            }

            List<Object> data = new ArrayList<>();
            // Добавляем заголовки столбцов
            data.add(headName.toArray());

            Object[] rowObjects;
            // Чтение данных
            while ((rowObjects = reader.nextRecord ()) != null) {
                data.add(rowObjects);
            }

            return data;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
