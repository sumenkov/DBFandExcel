package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.*;
import java.util.List;

public class DBFtoExcel {

    /**
     * Считать данные из файла DBF и сохранить данные в headName, данные.
     * @param filePath Расположение файла DBF
     * @param headName коллекция имен полей
     * @param data данных
     */
    public void readDBFFile(String filePath, List headName, List data) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            DBFReader reader = new DBFReader(inputStream);
            int numberOfFields = reader.getFieldCount();
            DBFField filed = null;
            for (int i = 0; i < numberOfFields; i++) {
                filed = reader.getField (i); // Считать значение поля
                headName.add(filed.getName());
            }
            Object[] rowObjects = null;
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
    }

    /**
     * Сохранить данные, прочитанные из файла DBF, как Excel
     * @param saveFilePath - путь сохранения файла
     * @param sheetName Имя листа и позиция курсора.
     * @param headName коллекция имен полей
     * @param data данных
     */
    public void writeExcel(String saveFilePath, String sheetName, List<String> headName, List<Object> data){
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(new File(saveFilePath));
            Label label;
            WritableSheet sheet; // Рабочий лист
            sheet = book.createSheet(sheetName, 0); // Создать рабочий лист
            for (int j = 0; j < headName.size(); j++) {
                // Метка (номер столбца, номер строки, содержимое)
                label = new Label(j,0,headName.get(j));
                sheet.addCell(label);
            }
            for (int j = 0; j < data.size(); j++) {
                Object[] rowObjects = (Object[]) data.get(j);
                for (int k = 0; k < rowObjects.length; k++) {
                    String dataString = rowObjects[k] == null ? ""
                            : processingCyrillic(new String(rowObjects[k].toString().getBytes(), "Cp866"));
                    // Метка (номер столбца, номер строки, содержимое)
                    label = new Label(k, j + 1, dataString);
                    sheet.addCell(label);
                }
            }
            book.write();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (WriteException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String processingCyrillic(String line){
        // Пересобераем строку, исправляем баги от кодировки OEM 866
        return line.replaceAll("┬", "") // Знак перед верхним регистром
                .replaceAll("├▒", "ё")
                .replaceAll("├а", "р")
                .replaceAll("├б", "с")
                .replaceAll("├в", "т")
                .replaceAll("├г", "у")
                .replaceAll("├д", "ф")
                .replaceAll("├е", "х")
                .replaceAll("├ж", "ц")
                .replaceAll("├з", "ч")
                .replaceAll("├и", "ш")
                .replaceAll("├й", "щ")
                .replaceAll("├к", "ъ")
                .replaceAll("├л", "ы")
                .replaceAll("├м", "ь")
                .replaceAll("├н", "э")
                .replaceAll("├о", "ю")
                .replaceAll("├п", "я")
                .replaceAll("├░", "Ё")
                .replaceAll("├╝", "№");
    }
}
