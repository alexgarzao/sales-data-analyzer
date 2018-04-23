package sales_data_analyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfig {
    public static final String inPath = "./data/in";
    public static final String outPath = "./data/out";
    public static final String procPath = "./data/proc";
    public static final String bkpPath = "./data/bkp";
    public static final String logPath = "./data/log";
    public static final int maxWorkers = 20;
    public static final String logFilename = "sda.log";
    public static final String fileSuffixToProcess = ".dat";
    public static final String fileSuffixWhenDone = ".done.dat";
    public static final String recordDelimiter = "ç";

    public static String toLog()
    {
        return(String.format(
            "inPath: %s, outPath: %s, procPath: %s " +
            "bkpPath: %s maxWorkers: %d fileSuffixToProcess: %s " +
            "fileSuffixWhenDone: %s logFilename: %s",
            inPath, outPath, procPath,
            bkpPath, maxWorkers, fileSuffixToProcess,
            fileSuffixWhenDone, logFilename)
        );
    }

    public static String getOutFilename(String inFilename)
    {
        Path p = Paths.get(inFilename);
        String file = p.getFileName().toString();

        // TODO: se der para padronizar todos os paths com / no final na config, essa barra abaixo eh inutil
        return(outPath + "/" + file.replace(fileSuffixToProcess, fileSuffixWhenDone));
    }

    public static String getBkpFilename(String inFilename)
    {
        Path p = Paths.get(inFilename);
        String file = p.getFileName().toString();

        return(procPath + "/" + file);
    }

    public static String getMaskToWatch()
    {
        return("*" + fileSuffixToProcess);
    }
}
