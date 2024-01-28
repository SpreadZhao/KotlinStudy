import basic.BackingField
import concurrency.VolatileExample
import concurrency.VolatileExample2
import concurrency.VolatileExample3
import delegation.intro.DelegationTest
import delegation.mylazy.MyLazyTest
import security.AESTest

fun main(args: Array<String>) {
//    ConcurrencyVSSerial.startVS()
//    Deadlock().deadlock()
//    Deadlock().dummyDeadlock()
//    HelloJNI.test()
//  GenericTest.testAdder()
//  ReorderExample.test()
//  DelegationTest.testJava()
//  MyLazyTest.test()
//  AESTest.test()
//  VolatileExample.test()
//  VolatileExample2.test()
//  VolatileExample3.test()
  val bf = BackingField()
  bf.age = 1
}