/*
 * 두 정수를 입력받아 나눗셈의 결과 출력
 * 0으로 나눌 경우 예외처리
 */
import java.util.Scanner;

public class ExceptionTest2 {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		
		System.out.print("첫 번째 수 입력 : ");
		int num1 = stdIn.nextInt();
		System.out.print("두 번째 수 입력 : ");
		int num2 = stdIn.nextInt();
		
		try {
			System.out.println("나눗셈 결과 : " + num1/num2);
		} catch (Exception e) {
			// 객체 e 는 예외의 변수
			// e.printStackTrace();
			System.out.println("나누는 수가 0이면 안 됩니다.");
		}
	}

}
