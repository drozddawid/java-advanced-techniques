package processors;

import processing.Processor;
import processing.Status;
import processing.StatusListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UpperCaser implements Processor {
    private Integer taskID = 0;
    private String result = null;
    private boolean busy = false;

    //for testing
//    public static void main(String[] args) {
//        UpperCaser dotter = new UpperCaser();
//        Listener listener = new Listener();
//
//        dotter.submitTask("  ala ma       kota ", listener);
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(() -> {
//            System.out.println("Adder: busy = " + dotter.busy + ", result = " + dotter.getResult() + ", taskID = "+ dotter.taskID);
//            dotter.submitTask("65+9", listener);
//        }, 0, 1, TimeUnit.SECONDS);
//
//        try {
//            Thread.sleep(21 * 1000);
//            service.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public UpperCaser() {
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

                    result = task.toUpperCase();
                    listener.statusChanged(new Status(taskID, counter.get()));
                    service.shutdown();
                    waiter.shutdown();
                }
            }, 0, 10, TimeUnit.MILLISECONDS);
            return true;
        } else return false;
    }

    @Override
    public String getInfo() {
        return "UpperCaser processor returns string with upper case..\n" +
                "Example:\n Input:\"put some  words here\"\nOutput: \"PUT SOME  WORDS HERE\"";
    }

    @Override
    public String getResult() {
        if (result != null) busy = false;
        return result;
    }
}
