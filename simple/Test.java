public class Test {


	int a;
	static int b;

	public Test(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public String test() {
		a = 25;
		b = 22222;
		int x = 1;
		String s = "";
		if (x == 1) {
			s = "567";
		} else {
			s = "abc";
		}
		return s;
	}
}
