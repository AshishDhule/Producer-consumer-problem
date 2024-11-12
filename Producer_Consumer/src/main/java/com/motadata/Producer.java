package com.motadata;

public class Producer implements Runnable {
	private final MessageQueue messageQueue;
	private final int messageCount;

	public Producer(MessageQueue messageQueue, int messageCount) {
		this.messageQueue = messageQueue;
		this.messageCount = messageCount;
	}

	@Override
	public void run() {
		for (int i = 1; i <= messageCount; i++) {
			String message = "Message " + i;
			try {
				messageQueue.produce(message);
				System.out.println("Produced: " + message);
			} catch (InterruptedException e) {
				System.err.println("Error producing message: " + message);
				Thread.currentThread().interrupt();
			}
		}

		// Add a termination message to signal the Consumer to stop
		try {
			messageQueue.produce("STOP");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
