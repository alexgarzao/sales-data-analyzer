package sales_data_analyzer;

public class RecordInvalidTokenException extends Exception {
    public RecordInvalidTokenException() { super(); }
    public RecordInvalidTokenException(String message) { super(message); }
    public RecordInvalidTokenException(String message, Throwable cause) { super(message, cause); }
    public RecordInvalidTokenException(Throwable cause) { super(cause); }
}
