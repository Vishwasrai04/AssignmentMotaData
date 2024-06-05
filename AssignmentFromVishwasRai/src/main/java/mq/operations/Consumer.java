package mq.operations;

import mq.operations.Logger;

public class Consumer implements Runnable {
    private final MessageQueue queue;

    public Consumer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                String message = queue.take();
                Logger.logMessageConsumed(message);
                // Simulate time taken to process the message
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Logger.logError("Error consuming message: " + e.getMessage());
            }
        }
    }
}