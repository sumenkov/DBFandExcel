package ru.sumenkov.dae;

import com.linuxense.javadbf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class WriterDBF {
   public static void writeDBF(String saveFilePath, List<Object> rowsData) throws FileNotFoundException {
       // создадим определения полей
       DBFField[] fields = StructureTableDBF.tableStructure();

       try (DBFWriter writer = new DBFWriter(new FileOutputStream(saveFilePath), Charset.forName("Cp866"))){
           writer.setFields(fields);
           // теперь заполняем DBFWriter
           for (Object rowData: rowsData) {
               writer.addRecord((Object[]) rowData);
           }
       }
   }
}
