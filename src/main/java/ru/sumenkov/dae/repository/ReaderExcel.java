package ru.sumenkov.dae.repository;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderExcel {

    /**
     * Считать данные из файла Excel.
     *
     * @param filePath Расположение файла DBF
     * @return массив данных, полученных из файла
     */
    public List<Object> readExcel(String filePath) throws BiffException, IOException {
        Workbook workbook = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        List<Object> rowsData = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            Object[] rowData = new Object[columns];
            for (int j = 0; j < columns; j++) {
                rowData[j] = sheet.getCell(j, i).getContents();
            }
            rowsData.add(rowData);
        }

        return rowsData;
    }
}
