package mq.operations;

import mq.operations.Logger;

public class Producer implements Runnable {
    private final MessageQueue queue;

    public Producer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            String message = "Message " + i;
            try {
                queue.put(message);
                Logger.logMessageProduced(message);
            } catch (InterruptedException e) {
                Logger.logError("Error producing message: " + e.getMessage());
            }
        }
    }
}
