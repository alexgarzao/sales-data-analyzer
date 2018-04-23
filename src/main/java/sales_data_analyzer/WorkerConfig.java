package sales_data_analyzer;

public class WorkerConfig {

    public WorkerConfig(String inFilename, String outFilename, String bkpFilename)
    {
        this.inFilename = inFilename;
        this.outFilename = outFilename;
        this.bkpFilename = bkpFilename;
    }

    // TODO: must be private, with get/set
    public String inFilename;
    public String outFilename;
    public String bkpFilename;
}
