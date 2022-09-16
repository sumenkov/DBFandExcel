package ru.sumenkov.dae;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

public final class StructureTableDBF {
    private StructureTableDBF() {
        throw new AssertionError("Instantiating StructureTableDBF class");
    }
    public static final int NUMBER_OF_COLUMNS = 10;

    /**
     * Описания структуры DBF таблицы
     */
    public static DBFField[] tableStructure() {
        DBFField[] fields = new DBFField[NUMBER_OF_COLUMNS];

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

        return fields;
    }
}
