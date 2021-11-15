package server;

/**************************************************
 *  Project: multithreadingServer
 *  Description: this project implements a trivial multithreading server for accepting concurrent
 *  connections from several clients. Clients may send three "commands" to the server: get, put, exit.
 *
 *	@files:
 *		mainServer: main program that implements the serverSocket and accept client's connections
 * 		connHandler: extends the Thread classes and manage the actual connection from the client
 * 		comCodes: defines the message types
 * 
 *****************************************************/

//Import sections
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class mainServer {

  public static void main(String[] args) {
    // int port= (new Integer(args[0])).intValue();
    int port = 8082;

    try {
      // Create the socket
      ServerSocket serverSocket = new ServerSocket(port);
      System.out.println("Socket created on port: " + port);
      System.out.println("Waiting for connections");

      int clientCount = 0;
      while (true) {
        // Wait for connections
        Socket clientSocket = serverSocket.accept();
        // Increase client count
        clientCount++;

        System.out.println("New Connection: client " + clientCount + clientSocket.getInetAddress() + "Port: "
            + clientSocket.getPort());

        // Create the handler for each client connection
        connHandler handler = new connHandler(clientSocket, clientCount);

        // Do data transfer
        handler.start();
      }
      // Close the server socket
      //

    } catch (IOException e) {
      System.err.println("Could not listen on port: " + port);
      System.exit(-1);
    } finally {
      System.out.println("Server stopped");
      // serverSocket.close();
    }
  }

}
