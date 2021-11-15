package server;

/********************
 *  Class writerThread is the producer
 */

import java.util.Random;

public class writerThread extends Thread {
	// Shared buffer
	private static threadUsingWait sharedQueue;

	// Random number generator
	private Random rand;

	// Thread number
	private int id;

	/*************************
	 * Constructor
	 * 
	 * @param sharedQueue: Shared buffer id: thread number
	 *************************/
	public writerThread(threadUsingWait sharedQueue, int id) {
		// Create the random number generator
		this.rand = new Random();

		this.id = id;
		writerThread.sharedQueue = sharedQueue;
	}

	/*************************
	 * While true, write in the shared buffer
	 * 
	 * @param
	 *************************/
	public void run() {
		System.out.println("The writer " + id + " is running");
		// write a new element in each iteration
		while (true) {
			this.write();
		}
	}

	/*****************************
	 * This method generates and write a new Integer in the shared buffer
	 * 
	 * @param
	 *****************************/
	private void write() {
		int min = 100;
		int max = 1000;

		// Generate a new random number
		Integer value = (rand.nextInt((max - min) + 1) + min);

		// Write value in the shared buffer
		sharedQueue.addObject(value, this.id);

		// Sleep for a random time
		try {
			sleep(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
