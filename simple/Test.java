public class Test {

	int a;
	int b;

	public Test(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public int test(int x) {
		Test t = new Test(x*2,x/2);
		return 1+a;
	}
}
