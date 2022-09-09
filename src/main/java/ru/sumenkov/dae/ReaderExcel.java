package ru.sumenkov.dae;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReaderExcel {
    public static final int SLASH_CHARACTER = 92;
    public void readExcel(String filePath) throws IOException, BiffException {

        Workbook workbook = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        List<Object> rowsData = new ArrayList<>();

        for (int i = 1; i < rows; i++) { // Начинаем не с 0 строки, а с 1. Пропускаем заголовки таблиц
            Object[] rowData = new Object[StructureTableDBF.NUMBER_OF_COLUMNS];
            for (int j = 0; j < columns; j++) {
                rowData[j] = sheet.getCell(j, i).getContents();
            }
            rowsData.add(rowData);
        }

        saveFileDBF(filePath, rowsData);
    }

    public static void saveFileDBF(String filePath, List<Object> rowsData) throws IOException {
        // Создаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(Path.of(filePath).getParent() + "\\newDBF");
        if (!Files.exists(dirOut)) {
            Files.createDirectory(dirOut);
        }
        // вытаскиваем имя файла и листа
        String name = filePath.substring(filePath.lastIndexOf(SLASH_CHARACTER) + 1, filePath.lastIndexOf("."));
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + name + ".dbf";
        //записываем файл
        WriterDBF.writeDBF(saveFilePath, rowsData);
    }
}
