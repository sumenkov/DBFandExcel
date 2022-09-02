import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBFtoExcel {

    /**
     * Считать данные из файла DBF и сохранить данные в headName, данные.
     * @param filePath Расположение файла DBF
     * @param headName коллекция имен полей
     * @param сбор данных
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
                headName.add(new String(filed.getName()));
            }
            Object[] rowObjects = null;
            while ((rowObjects = reader.nextRecord ())! = null) {// Чтение данных
                data.add(rowObjects);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
     * @param sheetNamePosts Превосходное имя листа и позиция заказа.
     * @param headName коллекция имен полей
     * @param сбор данных
     */
    public void writeExcel(String saveFilePath,String[] sheetNamePosts,List headName,List data){
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(new File(saveFilePath));
            Label label = null;
            WritableSheet sheet = null; // Рабочий лист
            Object[] rowObjects = null;
            String [] sheetNamePost = null; // Имя и местоположение листа
            for (int i = 0; i < sheetNamePosts.length; i++) {
                sheetNamePost = sheetNamePosts[i].split(":");
                sheet = book.createSheet (sheetNamePost [0], Integer.parseInt (sheetNamePost [1])); // Создать рабочий лист
                for (int j = 0; j < headName.size(); j++) {
                    // Метка (номер столбца, номер строки, содержимое)
                    label = new Label(j,0,new String(headName.get(j).toString().getBytes("ISO-8859-1"),"GBK"));
                    sheet.addCell(label);
                }
                for (int j = 0; j < data.size(); j++) {
                    rowObjects = (Object[]) data.get(j);
                    for (int k = 0; k < rowObjects.length; k ++) {
                        // Метка (номер столбца, номер строки, содержимое)
                        label = new Label(k,j+1,rowObjects[k] == null ? "" : new String(rowObjects[k].toString().getBytes("ISO-8859-1"),"GBK"));
                        sheet.addCell(label);
                    }
                }
            }
            book.write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (WriteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        DBFtoExcel toExcel = new DBFtoExcel();
        String filePath = "F:\\dbffile\\13-s.dbf";
        List headName = new ArrayList();
        List data = new ArrayList();
        String saveFilePath = "f: \\ dbffile \\ test dbfData.xls";
        String [] sheetNamePosts = {"Первая страница: 0"};
        toExcel.readDBFFile(filePath, headName, data);
        toExcel.writeExcel(saveFilePath, sheetNamePosts, headName, data);
    }

}