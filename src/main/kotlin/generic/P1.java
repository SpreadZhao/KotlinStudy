package generic;

class P1 {
}

class P2 extends P1 {

}

class P3 extends P2 {

}

class P4 extends P3 {

}

class P5 extends P4 {

}

class P<T extends P3> {
    int i;

    public static void main(String[] args) {
        P<P3> a = new P<>();
        P<P4> b = new P<>();
        P<? extends P3> c = new P<>();
        P<? super P3> d = new P<>();
        P<? extends P2> e = new P<P2>();
        P<? extends P4> f = new P<>();
        P<? super P2> g = new P<P2>();
        P<? super P4> h = new P<>();
    }
}