package ru.sumenkov.dae;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WriterExcel {
    /**
     * Сохранить данные, прочитанные из файла DBF, как Excel
     * @param saveFilePath - путь сохранения файла
     * @param sheetName Имя листа и позиция курсора.
     * @param headName коллекция имен полей
     * @param data данных
     */
    public static void writeExcel(String saveFilePath, String sheetName, List<String> headName, List<Object> data){
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(new File(saveFilePath));
            Label label;
            WritableSheet sheet = book.createSheet(sheetName, 0); // Создать рабочий лист
            for (int i = 0; i < headName.size(); i++) {
                label = new Label(i,0,headName.get(i)); // Метка (номер столбца, номер строки, содержимое)
                sheet.addCell(label);
            }
            for (int j = 0; j < data.size(); j++) {
                Object[] rowObjects = (Object[]) data.get(j);
                for (int k = 0; k < rowObjects.length; k++) {
                    String dataString = rowObjects[k] == null ? ""
                            : rowObjects[k].toString();
                    label = new Label(k, j + 1, dataString); // Метка (номер столбца, номер строки, содержимое)
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
}
