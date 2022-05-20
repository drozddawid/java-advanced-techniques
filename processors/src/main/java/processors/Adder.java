package processors;

import processing.Processor;
import processing.Status;
import processing.StatusListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Adder implements Processor {
    private Integer taskID = 0;
    private String result = null;
    private boolean busy = false;

    //for testing
//    public static void main(String[] args) {
//        Adder adder = new Adder();
//        Listener listener = new Listener();
//
//        adder.submitTask("  1  +2 ", listener);
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(() -> {
//            System.out.println("Adder: busy = " + adder.busy + ", result = " + adder.getResult() + ", taskID = "+ adder.taskID);
//            adder.submitTask("65+9", listener);
//        }, 0, 1, TimeUnit.SECONDS);
//
//        try {
//            Thread.sleep(21 * 1000);
//            service.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public Adder() {
    }

    @Override
    public boolean submitTask(String task, StatusListener listener) {
        if (!busy) {
            busy = true;
            taskID++;
            result = null;

            AtomicInteger counter = new AtomicInteger(0);
            listener.statusChanged(new Status(taskID, counter.get()));
            // emulates task processing
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(() -> {
                listener.statusChanged(new Status(taskID, counter.incrementAndGet()));
            }, 0, 50, TimeUnit.MILLISECONDS);

            // waits until the task is done and sets the result
            ScheduledExecutorService waiter = Executors.newSingleThreadScheduledExecutor();
            waiter.scheduleAtFixedRate(() -> {
                if (counter.get() >= 100) {
                    String[] args = task.strip().split(" *\\+ *");

                    if (args.length == 2) {
                        result = String.valueOf(Integer.parseInt(args[0]) + Integer.parseInt(args[1]));
                        service.shutdown();
                        waiter.shutdown();
                    } else {
                        result = "#### WRONG INPUT ####";
                        service.shutdown();
                        waiter.shutdown();
                    }
                    listener.statusChanged(new Status(taskID, counter.get()));
                }
            }, 0, 10, TimeUnit.MILLISECONDS);
            return true;
        } else return false;
    }

    @Override
    public String getInfo() {
        return "Adder processor returns sum of two integer arguments.\n" +
                "Example:\n Input:\"10 + 22\"\nOutput: \"32\"";
    }

    @Override
    public String getResult() {
        if (result != null) busy = false;
        return result;
    }
}
