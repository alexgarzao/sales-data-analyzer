package sales_data_analyzer;

import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
* FileWatcher class is responsible to find new files to be processed and
*             send that work to the thread pool.
*
* @author  Alex S. Garzão
*/
public class FileWatcher
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Path path;
    private ExecutorService executor;

    public FileWatcher(Path path, ExecutorService executor)
    {
        this.path = path;
        this.executor = executor;
    }

    private void addWork(String inFilename)
    {
        // TODO: nao faz sentido resolver os 3 nomes aqui... deveria ser na WorkerThread???
        Path p = Paths.get(inFilename);
        String file = p.getFileName().toString();

        String outFilename = AppConfig.outPath + "/" + file.replace(AppConfig.fileSuffixToProcess, AppConfig.fileSuffixWhenDone);
        // TODO: se der para padronizar todos os paths com / no final na config, essa barra abaixo eh inutil
        String bkpFilename = AppConfig.procPath + "/" + file;
        WorkerConfig config = new WorkerConfig(inFilename, outFilename, bkpFilename);
        Runnable worker = new WorkerThread(config);
        executor.execute(worker);
    }

    /**
    * start is responsible to monitore the filesystem for new files.
    *
    * @param data contains the data to be parsed.
    * @return true if the data could be parsed,
    *         false otherwise.
    */
    public void start()
    {
        createDefaultDataDirs();

        try {
            // INotify config to watch for files in a specific filepath.
            WatchService watchService = path.getFileSystem().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            readFilesFromPathOnStartup();

            // Loop forever to watch directory.
            while (true) {
                WatchKey watchKey;
                watchKey = watchService.take();

                // Poll for file system events on the WatchKey.
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    if (event.context().toString().endsWith(AppConfig.fileSuffixToProcess)) {
                        addWork(AppConfig.inPath + "/" + event.context());
                    }
                }

                // If the watched directed gets deleted, get out of run method.
                if (!watchKey.reset()) {
                    LOGGER.warning("WatchKey no longer valid");
                    watchKey.cancel();
                    watchService.close();
                    break;
                }
           }
       } catch (InterruptedException ex) {
            LOGGER.warning("WatchKey interrupted... bye...");
            return;
        } catch (IOException ex) {
            LOGGER.severe(ex.getStackTrace().toString());
            return;
        }
    }

    private void createDefaultDataDirs()
    {
        try {
            Files.createDirectories(Paths.get(AppConfig.inPath));
            Files.createDirectories(Paths.get(AppConfig.outPath));
            Files.createDirectories(Paths.get(AppConfig.procPath));
            Files.createDirectories(Paths.get(AppConfig.logPath));
        } catch(IOException ex) {
            LOGGER.severe("When trying to create default paths: " + ex.toString());
        }
    }

    /**
    * readFilesFromPath is responsible to read all the files from path, once
    *                   on the startup.
    */
    private void readFilesFromPathOnStartup()
    {
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
                Paths.get(AppConfig.inPath), "*" + AppConfig.fileSuffixToProcess);
            for (Path path : directoryStream) {
                addWork(path.toString());
            }
        } catch (IOException ex) {
            LOGGER.severe("When trying to scan the folder: " + ex.toString());
        }
    }
}
