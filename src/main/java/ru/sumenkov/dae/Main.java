package ru.sumenkov.dae;

import org.apache.commons.cli.*;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args)  {
        try {
            CommandLineParser commandLineParser = new DefaultParser();
            Options options = LaunchOptions.launchOptions();
            CommandLine launchOptions = commandLineParser.parse(options, args);

            String fileExtension = null;
            String charsetName = (launchOptions.hasOption("charset")) ?
                    launchOptions.getOptionValue("charset") : "IBM866";
            Path uploadDir = (launchOptions.hasOption("path")) ?
                    Path.of(launchOptions.getOptionValue("path")) : ProcessingPath.requestPath();
            
            if (launchOptions.hasOption("dbftoexcel")) {
                fileExtension = "dbf";
            } else if (launchOptions.hasOption("exceltodbf")) {
                fileExtension = "xls";
            } else if (launchOptions.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("DBFandExcel", options, true);
            }

            if (fileExtension != null)
                ProcessingFiles.processingFiles(uploadDir, fileExtension, charsetName);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid arguments");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
