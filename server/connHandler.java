package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.io.File;
import java.nio.file.Paths;

public class connHandler extends Thread {
  // Data stream and output streams for data transfer
  private DataInputStream clientInputStream;
  private DataOutputStream clientOutputStream;

  // Client socket for maintaining connection with the client
  private Socket clientSocket;

  // Client Id
  private int clientId;

  /***************************************************************************
   * Constructor
   * 
   * @param clientSocket: client socket created when the client connects to the
   *                      server
   */

  public connHandler(Socket clientSocket, int clientId) {
    try {
      this.clientSocket = clientSocket;
      this.clientId = clientId;
      clientInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
      clientOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void run() {
    dataTransfer();
  }

  /*****************************************************************************
   * Data transfer method Tasks: Manage connection with the client. Receive
   * commands from the client Invoke the appropriate method depending on the
   * received command
   * 
   *****************************************************************************/
  public void dataTransfer() {
    try {
      ArrayList<String> command = new ArrayList<String>();
      String nextLine;
      System.out.println("Client " + 1);
      do {
        // Wait for command

        nextLine = clientInputStream.readLine();
        command.add(nextLine);

        System.out.println(nextLine);
      } while (nextLine != null && !nextLine.isBlank());
      // System.out.println(command);

      // Parse the command
      requestParser parser = new requestParser(command);
      String commandType = parser.getMethod();
      ArrayList<String> params = parser.getParams();

      switch (commandType) {
        case "GET":
          getCommand(params);
          break;
      }
      // System.out.println(commandType);
      // for (int i = 0; i < params.size(); i++) {
      // System.out.println(params.get(i));
      // }

      // Close connection and socket
      clientInputStream.close();
      clientOutputStream.close();
      clientSocket.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /***************************************************************************
   * exitCommand Method Tasks: Sends OK confirmation code to the client for
   * closing connection
   * 
   **************************************************************************/

  private void exitCommand() {
    try {
      // clientOutputStream.writeInt(comCodes.OK);
      clientOutputStream.flush();
      System.out.println("The connection has been closed! \n client: " + clientId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /***************************************************************************
   * getCommand Method Tasks: Performs get command Acknowledges to the client for
   * the received command Receives the file name Acknowledges to the client for
   * the received file name Checks for the correctness of the file name if not
   * found, send the FILENOTFOUND error code the client if found, transfer the
   * file to the client
   * 
   **************************************************************************/
  private void getCommand(ArrayList<String> params) {
    byte[] buffer = new byte[comCodes.BUFFER_SIZE];
    int totalRead = 0;

    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

    System.out.println("Get Command");

    try {

      // // Write the OK code: make acknowledgment of GET command
      byteOutputStream.write(comCodes.OK.getBytes());

      // clientOutputStream.flush();

      // print the file name
      System.out.println("get " + params.get(0));

      // Send confirmation to the client
      // clientOutputStream.writeInt(comCodes.OK);
      // clientOutputStream.flush();

      // Save current time for computing transmission time
      long startTime = System.currentTimeMillis();

      /*********************************************************
       * Write here your code for data transfer
       **********************************************************/

      // Open the file

      String currentDir = System.getProperty("user.dir");
      System.out.println(Paths.get(currentDir + "/files/" + params.get(0)));
      byte[] bytes = Files.readAllBytes(Paths.get(currentDir + "/files/" +
          params.get(0)));

      // Send the file
      byteOutputStream.write(bytes);
      byte[] allBytes = byteOutputStream.toByteArray();
      System.out.print(new String(comCodes.OK.getBytes()));
      System.out.print(new String(("Content-Length: " + bytes.length + "\r\n").getBytes()));
      clientOutputStream.write(comCodes.OK.getBytes());
      clientOutputStream.write(("Content-Length: " + bytes.length + "\r\n").getBytes());
      clientOutputStream.write("\r\n".getBytes());
      clientOutputStream.write(bytes);
      clientOutputStream.flush();

      long endTime = System.currentTimeMillis();
      System.out.println(totalRead + " bytes read in " + (endTime - startTime) + " ms.");
      System.out.println("Successful Data transfer");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /***************************************************************************
   * putCommand Method Tasks: Performs put command Acknowledges to the client for
   * the received command Receives the file name Acknowledges to the client for
   * the received file name Checks for the correctness of the file name if found,
   * send the EXISTINGFILE error code the client if found, receive file from the
   * client
   * 
   **************************************************************************/
  private void putCommand() {
    byte[] buffer = new byte[comCodes.BUFFER_SIZE];
    int totalRead = 0;

    System.out.println("Put Command");

    try {
      int read;

      // Write the OK code: make acknowledgment of PUT command
      // clientOutputStream.writeInt(comCodes.OK);
      clientOutputStream.flush();

      // Wait for file name
      System.out.println("Waiting for the file name");
      read = clientInputStream.read(buffer);

      // print the file name
      System.out.println("put " + (new String(buffer)));

      // Send confirmation to the client
      // clientOutputStream.writeInt(comCodes.OK);
      clientOutputStream.flush();

      // Save current time for computing transmission time
      long startTime = System.currentTimeMillis();

      /*********************************************************
       * Write here your code for data transfer
       **********************************************************/

      long endTime = System.currentTimeMillis();
      System.out.println(totalRead + " bytes read in " + (endTime - startTime) + " ms.");
      System.out.println("Successful Data transfer");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
