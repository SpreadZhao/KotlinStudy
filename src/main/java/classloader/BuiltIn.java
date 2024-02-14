package classloader;

import java.sql.DriverManager;
import java.util.ArrayList;

public class BuiltIn {
    public static void main(String[] args) throws ClassNotFoundException {
//        try {
//            printClassLoaders();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        Class<?> c = Class.forName("basic.ToBeFinal");
        System.out.println("Classloader of ToBeFinal: " + c.getClassLoader());
    }

    private static void printClassLoaders() throws ClassNotFoundException {
        System.out.println("Classloader of this class: " + BuiltIn.class.getClassLoader());
        System.out.println("Classloader of DriverManager: " + DriverManager.class.getClassLoader());
        System.out.println("Classloader of ArrayList: " + ArrayList.class.getClassLoader());
    }
}
