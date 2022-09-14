package ru.sumenkov.dae;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriterExcel {
    /**
     * char: 92 равно знаку '/'
     */
    public static final int SLASH_CHARACTER = 92;

    /**
     * Сохранить данные, прочитанные из файла DBF, как Excel
     *
     * @param filePath Полный путь до прочитанного файла
     * @param headName коллекция имен полей
     * @param data коллекция данных
     */
    public static void saveFileExcel(String filePath, List<String> headName, List<Object> data) throws IOException {
        // Создаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(Path.of(filePath).getParent() + "\\xls");
        if (!Files.exists(dirOut)) {
            Files.createDirectory(dirOut);
        }
        // вытаскиваем имя файла и листа
        String name = filePath.substring(filePath.lastIndexOf(SLASH_CHARACTER) + 1, filePath.lastIndexOf("."));
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + name + ".xls";

        //записываем файл
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(new File(saveFilePath));
            Label label;
            WritableSheet sheet = book.createSheet(name, 0); // Создать рабочий лист
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
