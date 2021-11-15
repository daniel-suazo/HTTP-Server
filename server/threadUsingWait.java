package server;

import java.util.LinkedList;

/*****
 *  
 *  Basic thread using wait and notify methods
 * 
 * This project solves the producer-consumer problem using Wait and Notify methods
 * 
 */

/******************
 * Class: threadUsingWait
 * 
 * Main class Contains the shared buffer and the methods to add and remove
 * elements
 *
 */

public class threadUsingWait {

	// Current buffer count
	private int objectCount;

	// Buffer capacity
	private int maxCapacity;

	// Actual buffer
	private LinkedList<Integer> objectList;

	/********************
	 * Constructor
	 * 
	 * @param maxCapacity: buffer Capacity
	 */
	public threadUsingWait(int maxCapacity) {
		// Initial object count
		objectCount = 0;

		// Set object capacity
		this.maxCapacity = maxCapacity;

		// Create the actual buffer
		objectList = new LinkedList<Integer>();
	}

	/*********************
	 * This method returns the buffer count
	 * 
	 * @return objectCount
	 */
	public int getObjectCount() {
		return objectCount;
	}

	/*********************
	 * This method returns the buffer capacity
	 * 
	 * @return maxCapacity
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/*********************
	 * This method adds a new object to the buffer
	 * 
	 * @param value: actual object
	 */
	public synchronized void addObject(Integer value, int writerId) {
		try {
			// Check for empty spots
			while (objectCount == maxCapacity) {
				// Wait While the buffer is full
				System.out.println("-------> Writer " + writerId + " is waiting... -------> ");
				wait();
				// Wakes because there is an empty spot
				System.out.println("-------> Writer " + writerId + " wakes up -------> ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Add the new object to the buffer
		objectList.add(value);
		System.out.println("Writer " + writerId + " - wrote " + value);
		objectCount++;
		// Notify the first in the block queue
		notify();
		System.out.println("Writer " + writerId + " - Notify");
	}

	/*********************
	 * This method returns the buffer count
	 * 
	 * @return resp: first object in the buffer
	 */
	public synchronized Integer useObject(int readerId) {
		try {
			// Check for full spots
			while (objectCount == 0) {
				// Wait while the buffer is empty
				System.out.println("***************** Reader " + readerId + " waits.. ******************");
				wait();
				// Wake up because there is a new element
				System.out.println("***************** Consumer " + readerId + " wakes up... **************");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Remove the first object from the buffer
		Integer resp = objectList.poll();
		objectCount--;
		// Notify the first in the blocked queue
		notify();
		return resp;
	}

	public static void main(String[] args) {
		// Create the actual shared buffer
		threadUsingWait tuw = new threadUsingWait(3);

		int readerId = 0;
		// Create the consumer
		readerThread theReader = new readerThread(tuw, readerId);

		for (int i = 0; i < 5; i++) {// Create the producer
			writerThread theWriter = new writerThread(tuw, i);
			theWriter.start();
		}
		// Start the reader thread
		theReader.start();

	}

}
