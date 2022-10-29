package ru.sumenkov.dae;

import org.apache.commons.cli.*;
import ru.sumenkov.dae.mapper.ProcessingFiles;
import ru.sumenkov.dae.mapper.ProcessingPath;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new LaunchOptions().launchOptions();

        if (args.length == 0) helper(options);

        CommandLine commandLine = commandLineParser.parse(options, args);
        String fileExtension = null;

        if (commandLine.hasOption("dbftoexcel")) {
            fileExtension = "dbf";
        } else if (commandLine.hasOption("exceltodbf")) {
            fileExtension = "xls";
        } else {
            helper(options);
        }

        if (fileExtension != null) {
            String charsetName = (commandLine.hasOption("charset")) ?
                    commandLine.getOptionValue("charset") : "IBM866";
            Path path = (commandLine.hasOption("path")) ?
                    Path.of(commandLine.getOptionValue("path")) : ProcessingPath.requestPath();

            ProcessingFiles.processingFiles(path, fileExtension, charsetName);
        }
    }

    private static void helper(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("mysql-to-json", options, true);
        System.exit(0);
    }
}
