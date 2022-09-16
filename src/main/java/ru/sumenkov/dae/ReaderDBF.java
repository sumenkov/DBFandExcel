package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import jxl.write.WriteException;

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
        try (InputStream inputStream = new FileInputStream(filePath)) {
            DBFReader reader = new DBFReader(inputStream, Charset.forName("Cp866"));
            int numberOfFields = reader.getFieldCount();
            DBFField filed;
            List<String> headName = new ArrayList<>();
            for (int i = 0; i < numberOfFields; i++) {
                filed = reader.getField (i); // Считать значение поля
                headName.add(filed.getName());
            }

            List<Object> data = new ArrayList<>();
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord ()) != null) {// Чтение данных
                data.add(rowObjects);
            }

            new WriterExcel().saveFileExcel(filePath, headName, data);

        }  catch (IOException | WriteException e) {
            throw new RuntimeException(e);
        }
    }
}
