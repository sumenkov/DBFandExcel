package ru.sumenkov.dae;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class writerExcel {
    /**
     * Сохранить данные, прочитанные из файла DBF, как Excel
     * @param saveFilePath - путь сохранения файла
     * @param sheetName Имя листа и позиция курсора.
     * @param headName коллекция имен полей
     * @param data данных
     */
    public void writeExcel(String saveFilePath, String sheetName, List<String> headName, List<Object> data){
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(new File(saveFilePath));
            Label label;
            WritableSheet sheet; // Рабочий лист
            sheet = book.createSheet(sheetName, 0); // Создать рабочий лист
            for (int j = 0; j < headName.size(); j++) {
                // Метка (номер столбца, номер строки, содержимое)
                label = new Label(j,0,headName.get(j));
                sheet.addCell(label);
            }
            for (int j = 0; j < data.size(); j++) {
                Object[] rowObjects = (Object[]) data.get(j);
                for (int k = 0; k < rowObjects.length; k++) {
                    String dataString = rowObjects[k] == null ? ""
                            : processingCyrillic(new String(rowObjects[k].toString().getBytes(), "Cp866"));
                    // Метка (номер столбца, номер строки, содержимое)
                    label = new Label(k, j + 1, dataString);
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

    public String processingCyrillic(String line){
        // Пересобераем строку, исправляем баги от кодировки OEM 866
        StringBuilder newLine = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            String sign = String.valueOf(line.charAt(i));
            if (sign.equals("├")) {
                sign = sign + line.charAt(i+1);
                i++;
                switch (sign) {
                    case "├▒" -> newLine.append("ё");
                    case "├а" -> newLine.append("р");
                    case "├б" -> newLine.append("с");
                    case "├в" -> newLine.append("т");
                    case "├г" -> newLine.append("у");
                    case "├д" -> newLine.append("ф");
                    case "├е" -> newLine.append("х");
                    case "├ж" -> newLine.append("ц");
                    case "├з" -> newLine.append("ч");
                    case "├и" -> newLine.append("ш");
                    case "├й" -> newLine.append("щ");
                    case "├к" -> newLine.append("ъ");
                    case "├л" -> newLine.append("ы");
                    case "├м" -> newLine.append("ь");
                    case "├н" -> newLine.append("э");
                    case "├о" -> newLine.append("ю");
                    case "├п" -> newLine.append("я");
                    case "├░" -> newLine.append("Ё");
                    case "├╝" -> newLine.append("№");
                    default -> newLine.append(sign);
                }
            } else if (String.valueOf(line.charAt(i)).equals("┬")) {
                break;
            } else {
                newLine.append(sign);
            }
        }
        return newLine.toString();
    }
}