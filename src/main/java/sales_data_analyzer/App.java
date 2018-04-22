package sales_data_analyzer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.nio.file.Paths;

/**
* @author: Alex S. Garzão
*
* Main class. The data analyzer start here.
*/
public class App
{
    public static void main( String[] args )
    {
        System.out.println("Starting Sales Data Analyzer 0.1");

        // TODO: define the correct thread pool size bellow.
        ExecutorService executor = Executors.newFixedThreadPool(2);
        FileWatcher fileWatcher = new FileWatcher(Paths.get("./data/in"), executor);
        fileWatcher.start();
    }
}
