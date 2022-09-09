package ru.sumenkov.dae;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderExcel {
    public void readExcel(String fileLocation) throws IOException, BiffException {

        Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
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

        WriterDBF.writeDBF(rowsData);
    }
}
