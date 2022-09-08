package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class readerDBF {
    /**
     * char: 92 равно знаку '/'
     */
    public static final int SLASH_CHARACTER = 92;

    /**
     * Считать данные из файла DBF и сохранить данные в headName, данные.
     * @param filePath Расположение файла DBF
     */
    public void readDBFFile(String filePath) throws IOException {
        InputStream inputStream = null;
        List<String> headName = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        try {
            inputStream = new FileInputStream(filePath);
            DBFReader reader = new DBFReader(inputStream);
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

        // исправляем путь до последней директории *** java.nio поискать F
        int indexSlashCharacter = filePath.lastIndexOf(SLASH_CHARACTER);
        Path dirIn = Path.of(filePath.substring(0, indexSlashCharacter));
        // Создаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(dirIn + "\\xls");
        if (!Files.exists(dirOut)) {
            Files.createDirectory(dirOut);
        }
        // отделяем имя файла
        String name = filePath.substring(indexSlashCharacter + 1, filePath.lastIndexOf("."));
        // создаем имя нового файла
        String saveFilePath = dirOut + "\\" + name + ".xls";
        //записываем файл
        writerExcel toExcel = new writerExcel();
        toExcel.writeExcel(saveFilePath, name, headName, data);
    }
}
