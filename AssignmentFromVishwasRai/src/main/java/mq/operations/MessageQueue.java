package mq.operations;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
    private String message;
    private boolean messageAvailable = false;

    public synchronized void put(String message) {
        while (messageAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.message = message;
        messageAvailable = true;
        notifyAll();
    }

    public synchronized String take() {
        while (!messageAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        messageAvailable = false;
        notifyAll();
        return message;
    }
}