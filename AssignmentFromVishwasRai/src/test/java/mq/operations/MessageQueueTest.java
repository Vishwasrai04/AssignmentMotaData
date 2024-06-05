package mq.operations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class MessageQueueTest {


    @Test
    void testPutAndTake() throws InterruptedException {
        MessageQueue queue = new MessageQueue();
        queue.put("Test Message");
        assertEquals("Test Message", queue.take());
    }

    @Test
    void testTakeEmptyQueue() {
        MessageQueue queue = new MessageQueue();
        assertThrows(InterruptedException.class, () -> queue.take());
    }

}
