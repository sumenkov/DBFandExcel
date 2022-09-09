package ru.sumenkov.dae;

import com.linuxense.javadbf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

public class WriterDBF {
   public static void writeDBF() throws FileNotFoundException {
       // создадим определения полей
       DBFField[] fields = new DBFField[10];

       fields[0] = new DBFField();
       fields[0].setName("OUT");
       fields[0].setType(DBFDataType.CHARACTER);
       fields[0].setLength(1);

       fields[1] = new DBFField();
       fields[1].setName("FINISH");
       fields[1].setType(DBFDataType.CHARACTER);
       fields[1].setLength(1);

       fields[2] = new DBFField();
       fields[2].setName("GROUP");
       fields[2].setType(DBFDataType.CHARACTER);
       fields[2].setLength(20);

       fields[3] = new DBFField();
       fields[3].setName("FAM");
       fields[3].setType(DBFDataType.CHARACTER);
       fields[3].setLength(40);

       fields[4] = new DBFField();
       fields[4].setName("NAME");
       fields[4].setType(DBFDataType.CHARACTER);
       fields[4].setLength(30);

       fields[5] = new DBFField();
       fields[5].setName("OTCH");
       fields[5].setType(DBFDataType.CHARACTER);
       fields[5].setLength(30);

       fields[6] = new DBFField();
       fields[6].setName("VUZ");
       fields[6].setType(DBFDataType.CHARACTER);
       fields[6].setLength(50);

       fields[7] = new DBFField();
       fields[7].setName("ANKETA");
       fields[7].setType(DBFDataType.CHARACTER);
       fields[7].setLength(50);

       fields[8] = new DBFField();
       fields[8].setName("VUZ_ID");
       fields[8].setType(DBFDataType.CHARACTER);
       fields[8].setLength(40);

       fields[9] = new DBFField();
       fields[9].setName("PERSON_ID");
       fields[9].setType(DBFDataType.CHARACTER);
       fields[9].setLength(50);

       try (DBFWriter writer = new DBFWriter(new FileOutputStream("E:\\temp\\dbffiles\\test.dbf"), Charset.forName("Cp866"))){
           writer.setFields(fields);
           // теперь заполняем DBFWriter
           Object[] rowData = new Object[10];
           rowData[0] = "0";
           rowData[1] = "1";
           rowData[2] = "фг-11";
           rowData[3] = "ёйцукенгшщзхъфывапролджэячсмитьбю";
           rowData[4] = "ЁЙЦУКЕНГШЩЗХЪФЫВАПР";
           rowData[5] = "ОЛДЖЭЯЧСМИТЬБЮ №";
           rowData[6] = "АСПИРАНТ";
           rowData[7] = "0271292";
           rowData[8] = "127959068";
           rowData[9] = "164827999";

           writer.addRecord(rowData);
       }
   }
}
