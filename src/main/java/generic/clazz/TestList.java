package generic.clazz;

import java.util.ArrayList;
import java.util.List;

public class TestList<T extends Number> {
    int i = 1;

    public static void main(String[] args) {
//        TestList<Number> list1 = new TestList<>();
//        TestList<Integer> list2 = new TestList<>();
//        TestList<? extends Number> list3 = new TestList<>();
//        // Type parameter 'java.lang.Object' is not within its bound; should extend 'java.lang.Number'
//        TestList<Object> list4 = new TestList<Object>();
//        // Type parameter '? extends DelegationTest' is not within its bound; should extend 'java.lang.Number'
//        TestList<? extends DelegationTest> list5 = new TestList<>();
//        // Type parameter 'java.lang.constant.Constable' is not within its bound; should implement 'java.lang.Number'
//        TestList<? extends Constable> list6 = new TestList<Constable>();
//        TestList<? super Integer> list7 = new TestList<>();
//        TestList<? super Number> list8 = new TestList<>();
//        fun2();
    }

    public static Object[] fun1() {
        return new Integer[5];
    }

    public static void fun2() {
        Integer[] arr = (Integer[]) fun1();
        System.out.println(arr.length);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (int num : list) {

        }
    }
}


