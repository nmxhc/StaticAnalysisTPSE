public class BranchTest {

    public static int foo() {
        int x = 0;

        if (x == 1) {
            x = 7;
            System.out.println("A");
            x = 2;
        } else if (x == 2) {
            x = 12;
            System.out.println("B");
            x = 3;
        }

        x = 4;

        return x;
    }
}