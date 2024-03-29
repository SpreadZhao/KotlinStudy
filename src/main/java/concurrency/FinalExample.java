package concurrency;

public class FinalExample {
    int i;                              // 普通变量
    final int j;                        // final变量
    static FinalExample obj;

    public FinalExample() {             // 构造方法
        i = 1;                          // 写普通域
        j = 2;                          // 写final域
    }

    public static void writer() {       // 写线程A执行
        obj = new FinalExample();
    }

    public static void reader() {       // 读线程B执行
        FinalExample example = obj;     // 读对象引用
        int a = example.i;              // 读普通域
        int b = example.j;              // 读final域
        System.out.println("a: " + a);
        System.out.println("b: " + b);
    }

    public static void main(String[] args) {
        Thread readerThread = new Thread(FinalExample::reader);
        Thread writerThread = new Thread(FinalExample::writer);
        writerThread.start();
        readerThread.start();

    }
}
