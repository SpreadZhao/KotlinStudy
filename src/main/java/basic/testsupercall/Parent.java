package basic.testsupercall;

public class Parent {
  public void print() {
    realPrint();
  }

  public void realPrint() {
    System.out.println("parent");
  }
}
