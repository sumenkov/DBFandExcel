package ru.sumenkov.dae;

import com.linuxense.javadbf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

public class WriterDBF {
    /**
     * Сохранить данные прочитанные из excel в формат dbf
     *
     * @param filePath Полный путь до прочитанного файла
     * @param rowsData коллекция данных
     */
    public void saveFileDBF(String filePath, List<Object> rowsData) throws IOException {
        // Получаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(ProcessingPath.fixDirectoryPath(Path.of(filePath)).toString() + "\\new Files");
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + ProcessingPath.getFileName(filePath) + ".dbf";

        // создадим определения полей
        DBFField[] fields = StructureTableDBF.tableStructure();

        //записываем файл
        try (DBFWriter writer = new DBFWriter(new FileOutputStream(saveFilePath), Charset.forName("Cp866"))){
           writer.setFields(fields);
           // теперь заполняем DBFWriter
           for (Object rowData: rowsData) {
               writer.addRecord((Object[]) rowData);
           }
        }
    }
}
