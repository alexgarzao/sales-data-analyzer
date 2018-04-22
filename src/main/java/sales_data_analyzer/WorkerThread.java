package sales_data_analyzer;

import java.io.IOException;
import java.io.FileNotFoundException;

public class WorkerThread implements Runnable {

    private String inFilename;
    private String outFilename;

    public WorkerThread(String inFilename, String outFilename){
        this.inFilename = inFilename;
        this.outFilename = outFilename;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+" Start. Command = " + inFilename);
            processCommand();
            System.out.println(Thread.currentThread().getName()+" End. Command = " + outFilename);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    private void processCommand() throws FileNotFoundException, IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            SalesFile t = new SalesFile(inFilename, outFilename);
            t.process();
            // TODO: nome do arquivo = trocar .dat por .done.dat
            t.saveFileStats();
            System.out.println("TotalSalesman: " + t.getTotalSalesman());
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
        return this.inFilename;
    }
}
