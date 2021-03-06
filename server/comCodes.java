package server;

public class comCodes {

  // Buffer size
  public static final int BUFFER_SIZE = 1024;

  // Data transfer codes-------------------------------------------
  // OK
  public static final String OK = "HTTP/1.1 200 OK \r\n";

  // Get
  public static final int GET = 2;

  // Put
  public static final int PUT = 3;

  // Close connection
  public static final String CLOSECONNECTION = "HTTP/1.1 200 CLOSECONNECTION \r\n";

  // Error messages---------------------------------------------------
  // File not found
  public static final int FILENOTFOUND = 20;

  // The intended file already exists
  public static final int EXISTINGFILE = 21;

  // No valid command
  public static final int WRONGCOMMAND = 30;

}
