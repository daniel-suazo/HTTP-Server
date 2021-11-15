package server;

import java.util.Random;

/********************
 * Class writerThread implements the consumer
 **/

public class readerThread extends Thread {
	// Shared buffer
	private threadUsingWait sharedQueue;
	// Random number generator
	private Random rand;
	// Reader id
	private int id;

	/**********************
	 * Constructor
	 * 
	 * @param sharedQueue: Shared buffer
	 * 
	 ***********************/
	public readerThread(threadUsingWait sharedQueue, int readerId) {
		this.rand = new Random();
		this.id = readerId;
		this.sharedQueue = sharedQueue;
	}

	/****************************************
	 * While true, read from the shared buffer
	 * 
	 * @param
	 ****************************************/
	public void run() {
		System.out.println("the reader" + id + " is running...");
		while (true) {
			this.read();
		}
	}

	/*****************************************
	 * This method reads the first element from the buffer and print it
	 * 
	 * @param
	 *****************************************/
	private void read() {
		// Read from the buffer - this method is synchronized
		System.out.println("Reader " + id + " reading value " + this.sharedQueue.useObject(id));

		// Generate a random number and sleep that time
		int value = rand.nextInt((500) + 1);
		try {
			sleep(value);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
