package ru.sumenkov.dae;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderExcel implements Runnable {
    private final String filePath;

    /**
     * Считать данные из файла Excel.
     *
     * @param filePath Расположение файла DBF
     */
    public ReaderExcel (String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        Workbook workbook;
        try {
            workbook = Workbook.getWorkbook(new File(filePath));
        } catch (IOException | BiffException e) {
            throw new RuntimeException(e);
        }

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

        try {
            new WriterDBF().saveFileDBF(filePath, rowsData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
