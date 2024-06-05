package mq.operations;

import java.util.concurrent.atomic.AtomicInteger;
public class Logger {

    private static final AtomicInteger messagesProcessed = new AtomicInteger(0);
    private static final AtomicInteger errorsEncountered = new AtomicInteger(0);

    public static synchronized void logMessageProduced(String message) {
        System.out.println("Produced: " + message);
    }

    public static synchronized void logMessageConsumed(String message) {
        System.out.println("Consumed: " + message);
        messagesProcessed.incrementAndGet();
    }

    public static synchronized void logError(String error) {
        System.err.println("Error: " + error);
        errorsEncountered.incrementAndGet();
    }

    public static void logStats() {
        System.out.println("Total messages processed: " + messagesProcessed.get());
        System.out.println("Total errors encountered: " + errorsEncountered.get());
    }

    public static int getMessagesProcessed() {
        return messagesProcessed.get();
    }

    public static int getErrorsEncountered() {
        return errorsEncountered.get();
    }
}
