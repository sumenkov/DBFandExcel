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

    public static List<Object> readDBF(String filePath, String charsetName) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            DBFReader reader = new DBFReader(inputStream, Charset.forName(charsetName));
            // Собираем насвание столбцов
            int numberOfFields = reader.getFieldCount();
            List<String> headName = new ArrayList<>();
            for (int i = 0; i < numberOfFields; i++) {
                headName.add(reader.getField(i).getName());
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
