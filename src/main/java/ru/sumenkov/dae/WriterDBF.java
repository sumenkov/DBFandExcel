package ru.sumenkov.dae;

import com.linuxense.javadbf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

public final class WriterDBF {
    private WriterDBF() {
        throw new AssertionError("Instantiating WriterDBF class.");
    }

    /**
     * Сохранить данные прочитанные из excel в формат dbf
     *
     * @param filePath Полный путь до прочитанного файла
     * @param rowsData коллекция данных
     */
    public static void saveFileDBF(String filePath, List<Object> rowsData) throws IOException {
        // Получаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(ProcessingPath.fixDirectoryPath(Path.of(filePath)).toString() + "\\new Files");
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + ProcessingPath.getFileName(filePath) + ".dbf";
        // Получаем структуру таблицы
        Object[] structureTableDBF = StructureTableDBF.tableStructure((Object[]) rowsData.get(0));
        // создадим определения полей
        DBFField[] fields = (DBFField[]) structureTableDBF[0];
        // кодировка для записи данных
        String charsetName = (String) structureTableDBF[1];
        //записываем файл
        try (DBFWriter writer = new DBFWriter(new FileOutputStream(saveFilePath), Charset.forName(charsetName))){
           writer.setFields(fields);
           // теперь заполняем DBFWriter
           for (Object rowData: rowsData.subList(1, rowsData.size())) {
               writer.addRecord((Object[]) rowData);
           }
        }
    }
}
