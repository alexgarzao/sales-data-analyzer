package sales_data_analyzer;

import java.io.IOException;
import java.io.FileNotFoundException;

public class WorkerThread implements Runnable {

    private WorkerConfig config;

    public WorkerThread(WorkerConfig config)
    {
        this.config = config;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+" Start. Command = " + config.inFilename);
            processCommand();
            System.out.println(Thread.currentThread().getName()+" End. Command = " + config.outFilename);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    private void processCommand() throws FileNotFoundException, IOException {
        try {
            SalesFile t = new SalesFile(config.inFilename, config.outFilename, config.bkpFilename);
            t.process();
            // t.saveFileStats();
            // System.out.println("TotalSalesman: " + t.getTotalSalesman());
            // System.out.println("TotalClients: " + t.getTotalClients());
            // System.out.println("MostExpensiveSaleId: " + t.getMostExpensiveSaleId());
            // System.out.println("WorstSalesman: " + t.getWorstSalesman());
        } catch(FileNotFoundException e) {
            throw e;
        } catch(IOException e) {
            throw e;
        }
    }

    @Override
    public String toString(){
        return config.inFilename;
    }
}
