package jni;

public class HelloJNI {
    static {
        System.loadLibrary("hello");
    }

    private native void sayHello();

    public static void test() {
        new HelloJNI().sayHello();
    }
}
