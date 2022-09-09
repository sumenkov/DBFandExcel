package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReaderDBF {
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

        saveFileExcel(filePath, headName, data);
    }

    /**
     * Сохранить данные, прочитанные из файла DBF, как Excel
     * @param filePath Полный путь до прочитанного файла
     * @param headName коллекция имен полей
     * @param data коллекция данных
     */
    public static void saveFileExcel(String filePath, List<String> headName, List<Object> data) throws IOException {
        // Создаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(Path.of(filePath).getParent() + "\\xls");
        if (!Files.exists(dirOut)) {
            Files.createDirectory(dirOut);
        }
        // вытаскиваем имя файла и листа
        String name = filePath.substring(filePath.lastIndexOf(SLASH_CHARACTER) + 1, filePath.lastIndexOf("."));
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + name + ".xls";
        //записываем файл
        WriterExcel.writeExcel(saveFilePath, name, headName, data);
    }
}
