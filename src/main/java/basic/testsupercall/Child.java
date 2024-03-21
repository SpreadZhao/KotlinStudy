package basic.testsupercall;

public class Child extends Parent {
    @Override
    public void print() {
        super.print();
    }

    public void anotherPrint() {
        super.print();
    }

    @Override
    public void realPrint() {
        System.out.println("child");
    }
}
