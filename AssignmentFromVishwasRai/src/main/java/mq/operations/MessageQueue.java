package mq.operations;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
    private String message;
    private boolean messageAvailable = false;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public void put(String message) throws InterruptedException {
        lock.lock();
        try {
            while (messageAvailable) {
                notFull.await();
            }
            this.message = message;
            messageAvailable = true;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (!messageAvailable) {
                notEmpty.await();
            }
            messageAvailable = false;
            notFull.signal();
            return message;
        } finally {
            lock.unlock();
        }
    }
}