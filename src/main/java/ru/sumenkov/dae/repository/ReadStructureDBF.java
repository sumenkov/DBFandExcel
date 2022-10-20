package ru.sumenkov.dae.repository;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

public class ReadStructureDBF {

    /**
     * Вычитываем описания структуры DBF таблицы
     *
     * @param headlinesOfColumns заголовки столбцов, первая строка из файла Excel
     * @return Спецификация полей в файле DBF и кодировка для записи, указанная в .ini
     */
    public DBFField[] readStructure(Object[] headlinesOfColumns) {
        int numberOfColumns = headlinesOfColumns.length;
        DBFField[] fields = new DBFField[numberOfColumns];

        for (int i = 0; i < numberOfColumns; i++) {
            String[] head = headlinesOfColumns[i].toString().split(",");
            fields[i] = new DBFField();
            fields[i].setName(head[0]);
            fields[i].setType(typeMatching(head[1]));
            fields[i].setLength(Integer.parseInt(head[2]));
            if (head[1].equalsIgnoreCase("NUMERIC") || head[1].equalsIgnoreCase("FLOATING_POINT"))
                fields[i].setDecimalCount(Integer.parseInt(head[3]));
        }
        return fields;
    }

    /**
     * Сопоставление типов
     *
     * @param type наименование типа из файла EXCEL
     *
     * @return тип в формате DBFDataType
     */
    private DBFDataType typeMatching(String type) {
        return switch (type) {
            case "C", "CHARACTER" -> DBFDataType.CHARACTER;
            case "N", "NUMERIC" -> DBFDataType.NUMERIC;
            case "F", "FLOATING_POINT" -> DBFDataType.FLOATING_POINT;
            case "L", "LOGICAL" -> DBFDataType.LOGICAL;
            case "D", "DATE" -> DBFDataType.DATE;
            default -> throw new IllegalStateException("Неожиданное значение");
        };
    }
}
