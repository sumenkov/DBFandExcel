import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
    1. директории для DBF и xls
    2. сравнение списков в директориях + дата создания/изменения
    3. графичиский интерфейс с гридом и термометром
     */
    public static void main(String[] args) {
        DBFtoExcel toExcel = new DBFtoExcel();
        String filePath = "E:\\temp\\dbffiles\\НГМУ_15092021.dbf";
        List headName = new ArrayList();
        List data = new ArrayList();
        String saveFilePath = "E:\\temp\\dbffiles\\13-s.xls";
        String [] sheetNamePosts = {"Первая страница: 0"};
        toExcel.readDBFFile(filePath, headName, data);
        toExcel.writeExcel(saveFilePath, sheetNamePosts, headName, data);
    }
}
