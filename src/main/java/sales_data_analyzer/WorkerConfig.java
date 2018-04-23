package sales_data_analyzer;

/**
* @author: Alex S. Garzão
*
* WorkerConfig class. A unit of work to the Worker.
*/
public class WorkerConfig
{
    // TODO: must be private, with get/set
    public String inFilename;
    public String outFilename;
    public String bkpFilename;

    public WorkerConfig(String inFilename, String outFilename, String bkpFilename)
    {
        this.inFilename = inFilename;
        this.outFilename = outFilename;
        this.bkpFilename = bkpFilename;
    }
}
