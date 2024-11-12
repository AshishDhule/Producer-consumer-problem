package com.motadata;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
	private final Queue<String> queue = new LinkedList<>();

	public synchronized void produce(String message) throws InterruptedException {
		queue.add(message);
		notify(); // Notify a waiting consumer
	}

	public synchronized String consume() throws InterruptedException {
		while (queue.isEmpty()) {
			wait(); // Wait if no messages are available
		}
		return queue.poll();
	}
}
