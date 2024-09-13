
public class IntegerTest1 {

	public static void main(String[] args) {
		Integer num1 = Integer.valueOf(13); // new Integer(13);
		Integer num2 = 25; // Autoboxing을 이용한 방법
		
		System.out.println("num1이 포장하고 있는 (정수)값 : " + num1.intValue());
		System.out.println("num2이 포장하고 있는 (정수)값 : " + num2);
		
		num2 = num1 + num2; // 객체 변수도 연산이 가능
		
		System.out.println("num1 + num2 = " + num2);
		
		System.out.println("num2의 2진수 : " + Integer.toBinaryString(num2));
		System.out.println("num1 == num2 = " + num1.equals(num2));
	}

}
