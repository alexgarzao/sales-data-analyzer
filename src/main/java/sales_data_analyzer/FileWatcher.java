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

    private void processEvent(WatchEvent<?> event) {
        Runnable worker = new WorkerThread("Arquivo " + (Path) event.context());
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
        /*
        TODO: configura o watcher e le apenas create file
        carrega todos os arquivos existentes na pasta e envia para executor
        while(le eventos do watcher)
            alimenta executor
        */
        try {
            // INotify config.
            WatchService watchService = path.getFileSystem().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            // Read files from path (only once, on the startup).
            try {
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("./data/in"));
                for (Path path : directoryStream) {
                    Runnable worker = new WorkerThread("Arquivo " + path.toString());
                    executor.execute(worker);
                }
            } catch (IOException ex) {}

            // Loop forever to watch directory.
            while (true) {
                WatchKey watchKey;
                watchKey = watchService.take(); // this call is blocking until events are present

                // poll for file system events on the WatchKey
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    processEvent(event);
                }

                // if the watched directed gets deleted, get out of run method
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
            ex.printStackTrace();  // don't do this in production code. Use a loggin framework
            return;
        }
    }
}
