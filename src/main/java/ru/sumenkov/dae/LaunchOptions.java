package ru.sumenkov.dae;


import org.apache.commons.cli.*;

public class LaunchOptions {
    /**
     * Обрабатываем аргументы запуска программы
     *
     * @return параметры запуска
     */
    public Options launchOptions() {
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(new Option("d", "dbftoexcel", false, "Конвертация DBF таблиц в Excel"));
        optionGroup.addOption(new Option("e", "exceltodbf", false, "Конвертация Excel таблиц в DBF"));

        Options options = new Options();
        options.addOptionGroup(optionGroup);
        options.addOption(Option.builder("c")
                .longOpt("charset")
                .argName("charset Name")
                .hasArg(true)
                .desc("Кодировка для чтения/записи DBF")
                .build());
        options.addOption(Option.builder("p")
                .longOpt("path")
                .argName("path")
                .hasArg(true)
                .desc("Путь до файла или директории")
                .build());

        return options;
    }
}
