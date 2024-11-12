package com.motadata;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
	private final MessageQueue messageQueue;
    private final AtomicInteger successCount;
    private final AtomicInteger errorCount;

    public Consumer(MessageQueue messageQueue, AtomicInteger successCount, AtomicInteger errorCount) {
        this.messageQueue = messageQueue;
        this.successCount = successCount;
        this.errorCount = errorCount;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = messageQueue.consume();
                
                // Check for termination message
                if ("STOP".equals(message)) {
                    System.out.println("Consumer received STOP message. Exiting.");
                    break;
                }

				System.out.println("Consumed: " + message);
                successCount.incrementAndGet();
            } catch (InterruptedException e) {
                errorCount.incrementAndGet();
                System.err.println("Error consuming message");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
