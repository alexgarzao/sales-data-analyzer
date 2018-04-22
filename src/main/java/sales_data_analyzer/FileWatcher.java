package sales_data_analyzer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
* TODO class is responsible to parser data records.
*
* @author  Alex S. Garzão
* @version 0.1
*/
public class FileWatcher
{
    private Path path;
    private ExecutorService executor;

    public FileWatcher(Path path, ExecutorService executor)
    {
        this.path = path;
        this.executor = executor;
    }

    private void addWork(String inFilename)
    {
        Path p = Paths.get(inFilename);
        String file = p.getFileName().toString();

        String outFilename = "./data/out/" + file.replace(".dat", ".done.dat");
        String bkpFilename = "./data/proc/" + file;
        WorkerConfig config = new WorkerConfig(inFilename, outFilename, bkpFilename);
        Runnable worker = new WorkerThread(config);
        executor.execute(worker);
    }

    /**
    * TODO is responsible to extract the data from a file line.
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

            // Read files from path (only once, on the startup).
            try {
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("./data/in"), "*.dat");
                for (Path path : directoryStream) {
                    addWork(path.toString());
                }
            } catch (IOException ex) {
                // TODO
            }

            // Loop forever to watch directory.
            while (true) {
                WatchKey watchKey;
                watchKey = watchService.take();

                // Poll for file system events on the WatchKey.
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    if (event.context().toString().endsWith(".dat")) {
                        addWork("./data/in/" + event.context());
                    }
                }

                // If the watched directed gets deleted, get out of run method.
                if (!watchKey.reset()) {
                    System.out.println("No longer valid");
                    watchKey.cancel();
                    watchService.close();
                    break;
                }
           }
       } catch (InterruptedException ex) {
            System.out.println("interrupted. Goodbye");
            return;
        } catch (IOException ex) {
            ex.printStackTrace();  // TODO: don't do this in production code. Use a loggin framework
            return;
        }
    }

    private void createDefaultDataDirs()
    {
        try {
            Files.createDirectories(Paths.get("data/in"));
            Files.createDirectories(Paths.get("data/out"));
            Files.createDirectories(Paths.get("data/proc"));
            Files.createDirectories(Paths.get("data/log"));
        } catch(IOException ex)
        {
            // TODO
        }
    }
}
