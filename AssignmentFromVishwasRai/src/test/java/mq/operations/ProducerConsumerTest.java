package mq.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
    public void testProducerFailure() {
        MessageQueue queue = new MessageQueue();
        Producer producer = new Producer(queue) {
            @Override
            public void run() {

                throw new RuntimeException("Producer failed to produce message");
            }
        };

        Thread producerThread = new Thread(producer);
        producerThread.start();

        try {
            producerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, queue.take());
        assertTrue(Logger.getErrorsEncountered() > 0);
    }

    @Test
    public void testConsumerFailure() {
        MessageQueue queue = new MessageQueue();
        queue.put("Message 1");

        Consumer consumer = new Consumer(queue) {
            @Override
            public void run() {
                throw new RuntimeException("Consumer failed to consume message");
            }
        };

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        try {
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(1, queue.take());
        assertTrue(Logger.getErrorsEncountered() > 0);
    }


}

