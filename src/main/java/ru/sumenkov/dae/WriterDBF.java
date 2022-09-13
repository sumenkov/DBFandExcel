package ru.sumenkov.dae;

import com.linuxense.javadbf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriterDBF {
    /**
     * char: 92 равно знаку '/'
     */
    public static final int SLASH_CHARACTER = 92;

    /**
     * Сохранить данные прочитанные из excel в формат dbf
     * @param filePath Полный путь до прочитанного файла
     * @param rowsData коллекция данных
     */
    public static void saveFileDBF(String filePath, List<Object> rowsData) throws IOException {
        // Создаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(Path.of(filePath).getParent() + "\\newDBF");
        if (!Files.exists(dirOut)) {
           Files.createDirectory(dirOut);
        }
        // вытаскиваем имя файла и листа
        String name = filePath.substring(filePath.lastIndexOf(SLASH_CHARACTER) + 1, filePath.lastIndexOf("."));
        // создаем полный пусть с именем нового файла
        String saveFilePath = dirOut + "\\" + name + ".dbf";

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
