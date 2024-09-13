package b_package;
import a_package.test1;
// import a_package.& -> 패키지의 모든 내용을 포함 //

public class test2 {

	public static void main(String[] args) {
		test1 ss = new test1();
		System.out.println(ss.sum(10, 20));
	}

}
