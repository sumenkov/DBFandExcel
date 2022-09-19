# DBFandExcel
Конвертация DBF файлов в Excel и обратно (работает в кодировке CP866).

Доступные аргументы для запуска:

--dbftoexcel - для конвертации DBF таблиц в Excel

--exceltodbf - для конвертации Excel таблиц в DBF

После указания аргумента можно сразу добавить кодировку для чтения из DBF:

    java -jar DBFandExcel.jar --dbftoexcel windows-1251

По умолчанию используется кодировка CP866 (IBM866), она же DOS