package ru.sumenkov.dae.service;

import com.linuxense.javadbf.*;
import ru.sumenkov.dae.repository.ReadStructureDBF;
import ru.sumenkov.dae.mapper.ProcessingPath;

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
    public void saveFileDBF(String filePath, List<Object> rowsData, String charsetName) throws IOException {
        // Получаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(ProcessingPath.fixDirectoryPath(Path.of(filePath)).toString() + "\\new Files");
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + ProcessingPath.getFileName(filePath) + ".dbf";
        // Получаем структуру таблицы и создадим определения полей
        DBFField[] fields = new ReadStructureDBF().readStructure((Object[]) rowsData.get(0));
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
