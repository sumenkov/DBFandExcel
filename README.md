# DBFandExcel
Конвертация DBF файлов в Excel и обратно (за один запуск делается конвертация только в одну сторону).

Имя, тип и размер столбцов DBF описываются в заголовках фала Excel (первая строка), в формате:

Name,Type,Length,Decimal_count (живой пример можно увидеть, при конвертации DBF в Excel, в файде .xls)

### Доступные аргументы для запуска:

--dbftoexcel - для конвертации DBF таблиц в Excel

--exceltodbf - для конвертации Excel таблиц в DBF


#### дополнительные аргументы (не обязательные):

--charset - кодировка для чтения DBF

--path - путь до файла или директории

После указания аргумента можно сразу добавить кодировку для чтения/записи DBF и/или путь до файла или директории с файлами:

    java -jar DBFandExcel.jar --dbftoexcel --charset windows-1251 --path  C:\Files_DBF

По умолчанию используется кодировка CP866 (IBM866), она же DOS

### Поддерживаемые типы для чтения и записи DBF

| XBase Type     | XBase Symbol | Java Type used in JavaDBF  | Write Supported |
|----------------|--------------|----------------------------|-----------------|
| Character      | C            | java.lang.String           | True            |
| Numeric        | N            | java.math.BigDecimal       | True            |
| Floating Point | F            | java.math.BigDecimal       | True            |
| Logical        | L            | java.lang.Boolean          | True            |
| Date           | D            | java.util.Date             | True            |
| Currency       | Y            | java.math.BigDecimal       | False           |
| Long           | I            | java.lang.Integer          | False           |
| Date Type      | T            | java.util.Date             | False           |
| Timestamp      | @            | java.util.Date             | False           |
| AutoIncrement  | +            | java.lang.Integer          | False           |
| Memo           | M            | java.lang.String or byte[] | False           |
| Binary         | B            | byte[] or java.lang.Double | False           |
| Blob           | W            | byte[]                     | False           |
| General        | G            | byte[]                     | False           |
| Picture        | P            | byte[]                     | False           |
| VarBinary      | Q            | byte[]                     | False           |
| Varchar        | V            | java.lang.String           | False           |
| Double         | O            | java.lang.Double           | False           |
