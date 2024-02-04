package concurrency.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadPriority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws Exception {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        threadDump();
        notEnd = false;
        for (Job job : jobs) {
            System.out.println("Job Priority: " + job.priority + " Count: " + job.jobCount);
        }
    }

    static class Job implements Runnable {

        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }

    private static void threadDump() {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
            System.out.println("pid: " + pid);
            Process process = Runtime.getRuntime().exec("jstack " + pid);
            is = process.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
