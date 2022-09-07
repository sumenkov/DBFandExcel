import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        //Запрашиваем директорию с файлами
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Path dirIn = Path.of(reader.readLine());

        // Если выбрали файл, исправляем путь до последней директории
        if (!Files.isDirectory(dirIn)){
            int beginDelString = dirIn.toString().lastIndexOf(92); // char: 92 равно знаку '/'
            dirIn = Path.of(dirIn.toString().substring(0, beginDelString));
        }

        // Собираем список файлов формата DBF
        List namesFiles = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(dirIn)) {
            for (Path file : files)
                if (file.toString().substring(file.toString().lastIndexOf(".") + 1).equals("dbf")) {
                    namesFiles.add(file.getFileName());
                }
        }

        // Создаем директорию для сохранения файлов Excel
        Path dirOut = Path.of(dirIn + "\\xls");
        if (!Files.exists(dirOut)) {
            Files.createDirectory(dirOut);
        }

        // Обработка DBF файлов
        for (Object nameFile: namesFiles) {
            DBFtoExcel toExcel = new DBFtoExcel();
            String filePath = dirIn + "\\" + nameFile.toString();
            List headName = new ArrayList();
            List data = new ArrayList();
            // отделяем имя файла от расширения
            String name = nameFile.toString().substring(0, nameFile.toString().lastIndexOf("."));

            String saveFilePath = dirOut + "\\" + name + ".xls";
            String [] sheetNamePosts = {name + ": 0"};

            toExcel.readDBFFile(filePath, headName, data);
            toExcel.writeExcel(saveFilePath, sheetNamePosts, headName, data);
        }
    }
}
// E:\temp\dbffiles