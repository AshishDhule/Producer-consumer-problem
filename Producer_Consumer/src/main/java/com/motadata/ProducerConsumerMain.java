package com.motadata;

import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerMain {

	public static void main(String[] args) {
		int messageCount = 50; // Number of messages to produce

		// Shared resources
		MessageQueue messageQueue = new MessageQueue();
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger errorCount = new AtomicInteger(0);

		// Initialize Producer and Consumer
		Producer producer = new Producer(messageQueue, messageCount);
		Consumer consumer = new Consumer(messageQueue, successCount, errorCount);

		// Start producer and consumer threads
		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();

		// Wait for threads to finish
		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Print final counts
		System.out.println("Total messages processed successfully: " + successCount.get());
		System.out.println("Total errors encountered: " + errorCount.get());
	}

}
