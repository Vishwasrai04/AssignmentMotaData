package mq.operations;

public class Consumer implements Runnable {
    private final MessageQueue queue;

    public Consumer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            String message = queue.take();
            Logger.logMessageConsumed(message);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Logger.logError(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}