package ru.sumenkov.dae;

import org.apache.commons.cli.*;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new LaunchOptions().launchOptions();
        CommandLine commandLine = commandLineParser.parse(options, args);
        String fileExtension = null;

        if (commandLine.hasOption("dbftoexcel")) {
            fileExtension = "dbf";
        } else if (commandLine.hasOption("exceltodbf")) {
            fileExtension = "xls";
        } else if (commandLine.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("dbf-and-excel", options, true);
        }

        if (fileExtension != null) {
            String charsetName = (commandLine.hasOption("charset")) ?
                    commandLine.getOptionValue("charset") : "IBM866";
            Path path = (commandLine.hasOption("path")) ?
                    Path.of(commandLine.getOptionValue("path")) : ProcessingPath.requestPath();

            ProcessingFiles.processingFiles(path, fileExtension, charsetName);
        }
    }
}
