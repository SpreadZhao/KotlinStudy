public class ConstructorTest {

    static class Static {

    }

    class People {
        int age;
        String name;


        {
            System.out.println("Non-static block of People");
        }

        public void work() {
            System.out.println("People.work()");
        }

        public People() {
            System.out.println("People()");
        }

        public People(int age, String name) {
            System.out.println("People(age, name)");
            this.age = age;
            this.name = name;
        }
    }

    class Student extends People {

        int stuId;


        {
            System.out.println("Non-static block of Student");
        }

        @Override
        public void work() {

        }

        public Student() {
            super(1, "spread");
            System.out.println("Student()");
        }

        public Student(int age, String name) {
            System.out.println("Student(age, name)");
        }

        public Student(int age, String name, int stuId) {
            super(age, name);
            System.out.println("Student(age, name, stuId)");
            this.stuId = stuId;
        }
    }

    public static void test() {
        Student stu = new ConstructorTest().new Student(2, "zhao");
    }
}
