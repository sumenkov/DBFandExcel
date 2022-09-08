package ru.sumenkov.dae;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * char: 92 равно знаку '/'
     */
    public static final int SLASH_CHARACTER = 92;

    public static void main(String[] args) throws Exception {
        //Запрашиваем директорию с файлами
        Path dirIn;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            dirIn = Path.of(reader.readLine());
        }

        // Если выбрали файл, исправляем путь до последней директории *** java.nio поискать F
        if (!Files.isDirectory(dirIn)){
            int beginDelString = dirIn.toString().lastIndexOf(SLASH_CHARACTER);
            dirIn = Path.of(dirIn.toString().substring(0, beginDelString));
        }

        // Собираем список файлов формата DBF
        List<String> namesFiles = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(dirIn)) {
            for (Path file : files)
                if (file.toString().substring(file.toString().lastIndexOf(".") + 1)
                        .equalsIgnoreCase("dbf")) {
                    namesFiles.add(file.getFileName().toString());
                }
        }

        // Обработка DBF файлов
        for (String nameFile: namesFiles) {
            String filePath = dirIn + "\\" + nameFile;
            readerDBF readDBF = new readerDBF();
            readDBF.readDBFFile(filePath);
        }
    }
}
// E:\temp\dbffiles