import java.util.Set;

public abstract class Test {

    int foo;
    Set<Integer> bar;

    public static int inc(int x) {
        return add(x, 1);
    }

    public static int add(int x, int y) {
        return x+y;
    }

    public static void print42() {
        System.out.println("42");
    }

}
