package concurrency.itc;

public class SynchronizedExample2 {
    public static void main(String[] args) {
        synchronized (SynchronizedExample2.class) {

        }
        m();
    }

    public static synchronized void m() {

    }
}
