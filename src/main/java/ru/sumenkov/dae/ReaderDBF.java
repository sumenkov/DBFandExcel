package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReaderDBF implements Runnable {
    private final String filePath;

    /**
     * Считать данные из файла DBF.
     *
     * @param filePath Расположение файла DBF
     */
    public ReaderDBF (String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        List<String> headName = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        try {
            inputStream = new FileInputStream(filePath);
            DBFReader reader = new DBFReader(inputStream, Charset.forName("Cp866"));
            int numberOfFields = reader.getFieldCount();
            DBFField filed;
            for (int i = 0; i < numberOfFields; i++) {
                filed = reader.getField (i); // Считать значение поля
                headName.add(filed.getName());
            }

            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord ()) != null) {// Чтение данных
                data.add(rowObjects);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            WriterExcel.saveFileExcel(filePath, headName, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
