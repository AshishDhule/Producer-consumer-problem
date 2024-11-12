package com.motadata;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProducerConsumerTest {
	@Test
	public void testSuccessfulProcessing() throws InterruptedException {
		MessageQueue messageQueue = new MessageQueue();
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger errorCount = new AtomicInteger(0);

		int messageCount = 5;
		Producer producer = new Producer(messageQueue, messageCount);
		Consumer consumer = new Consumer(messageQueue, successCount, errorCount);

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();

		producerThread.join();
		consumerThread.join();

		assertEquals(messageCount, successCount.get());
		assertEquals(0, errorCount.get());
	}

	@Test
	public void testWithErrorScenario() throws InterruptedException {
		MessageQueue messageQueue = new MessageQueue();
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger errorCount = new AtomicInteger(0);

		int messageCount = 3;
		Producer producer = new Producer(messageQueue, messageCount);
		Consumer consumer = new Consumer(messageQueue, successCount, errorCount);

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();

		// Interrupt consumer thread to simulate an error
		consumerThread.start();
		consumerThread.interrupt();
		consumerThread.join();

		assertEquals(0, successCount.get());
		assertEquals(1, errorCount.get()); 
	}
}
