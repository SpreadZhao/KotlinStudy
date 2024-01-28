package basic;

public class ToBeFinal {


    public void addOne(int num) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (num == 1) {
                    System.out.println();
                }
            }
        };
        new Thread(task).start();
    }

    public void onAddFinished(int newNum) {

    }
}
