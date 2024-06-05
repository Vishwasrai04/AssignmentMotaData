package mq.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class ProducerConsumerTest {
    private MessageQueue queue;
    private Producer producer;
    private Consumer consumer;

    @BeforeEach
    void setUp() {
        queue = new MessageQueue();
        producer = new Producer(queue);
        consumer = new Consumer(queue);
    }

    @Test
    void testProducerConsumerSuccess() throws InterruptedException {
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        assertEquals(10, Logger.getMessagesProcessed());
        assertEquals(0, Logger.getErrorsEncountered());
    }

    @Test
    void testConsumerFailure() throws InterruptedException {
        Consumer failingConsumer = new Consumer(queue) {
            @Override
            public void run() {
                try {
                    String message = queue.take();
                    if (message == null) {
                        throw new InterruptedException("Queue is empty");
                    }
                } catch (InterruptedException e) {
                    Logger.logError(e.getMessage());
                }
            }
        };

        Thread consumerThread = new Thread(failingConsumer);
        consumerThread.start();
        consumerThread.join();

        assertEquals(0, Logger.getMessagesProcessed());
        assertEquals(1, Logger.getErrorsEncountered());
    }
}
