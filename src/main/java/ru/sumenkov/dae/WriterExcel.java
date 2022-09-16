package ru.sumenkov.dae;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class WriterExcel {
    /**
     * Сохранить данные, прочитанные из файла DBF, как Excel
     *
     * @param filePath Полный путь до прочитанного файла
     * @param headName коллекция имен полей
     * @param data коллекция данных
     */
    public void saveFileExcel(String filePath, List<String> headName, List<Object> data) throws IOException, WriteException {
        // директория для сохранения файлов Excel
        Path dirOut = Path.of(ProcessingPath.fixDirectoryPath(Path.of(filePath)).toString() + "\\new Files");
        // Получаем имя файла
        String fileName = ProcessingPath.getFileName(filePath);
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + fileName + ".xls";

        // Создаем книгу и рабочий лист
        WritableWorkbook book = Workbook.createWorkbook(new File(saveFilePath));
        WritableSheet sheet = book.createSheet(fileName, 0);

        fillWorksheet(sheet, headName);
        fillWorksheet(sheet, data);

        // Записываем файл
        book.write();
        book.close();
    }

    /**
     * Записываем данные в Рабочий лист
     *
     * @param sheet рабочий лист
     * @param data коллекция данных
     */
    private void fillWorksheet(WritableSheet sheet, List<?> data) throws WriteException {
        if (data.get(0) instanceof String){
            for (int i = 0; i < data.size(); i++) {
            Label label = new Label(i,0, data.get(i).toString()); // Метка (номер столбца, номер строки, содержимое)
            sheet.addCell(label);
            }
        } else {
            for (int j = 0; j < data.size(); j++) {
                Object[] rowObjects = (Object[]) data.get(j);
                for (int k = 0; k < rowObjects.length; k++) {
                    String dataString = rowObjects[k] == null ? "" : rowObjects[k].toString();
                    Label label = new Label(k, j + 1, dataString); // Метка (номер столбца, номер строки, содержимое)
                    sheet.addCell(label);
                }
            }
        }
    }
}
