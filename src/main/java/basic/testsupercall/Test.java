package basic.testsupercall;

public class Test {
  public static void main(String[] args) {
    Parent person = new Child();
    System.out.print("result: ");
    person.print();
  }
}
