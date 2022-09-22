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

public final class WriterExcel {
    private WriterExcel() {
        throw new AssertionError("Instantiating WriterExcel class.");
    }
    /**
     * Сохранить данные, прочитанные из файла DBF, как Excel
     *
     * @param filePath Полный путь до прочитанного файла
     * @param data коллекция данных
     */
    public static void saveFileExcel(String filePath, List<Object> data) throws IOException, WriteException {
        // Создаем книгу и рабочий лист
        WritableWorkbook book = Workbook.createWorkbook(new File(getNewFileName(filePath)));
        WritableSheet sheet = book.createSheet(ProcessingPath.getFileName(filePath), 0);
        // Заполняем рабочий лист
        fillWorksheet(sheet, data);
        // Записываем файл
        book.write();
        book.close();
    }

    /**
     * возвращаем полный пусть с именем нового файла
     *
     * @param filePath Полный путь до прочитанного файла
     */
    private static String getNewFileName(String filePath) {
        return ProcessingPath.fixDirectoryPath(Path.of(filePath)).toString() + "\\new Files"
                + "\\" + ProcessingPath.getFileName(filePath) + ".xls";
    }

    /**
     * Записываем данные в Рабочий лист
     *
     * @param sheet рабочий лист
     * @param data коллекция данных
     */
    private static void fillWorksheet(WritableSheet sheet, List<Object> data) throws WriteException {
        for (int j = 0; j < data.size(); j++) {
            Object[] rowObjects = (Object[]) data.get(j);
            for (int k = 0; k < rowObjects.length; k++) {
                String dataString = rowObjects[k] == null ? "" : rowObjects[k].toString();
                // label: номер столбца, номер строки, содержимое
                Label label = new Label(k, j, dataString);
                sheet.addCell(label);
            }
        }
    }
}
