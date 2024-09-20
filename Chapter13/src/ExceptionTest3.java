/*
 * 두 정수를 입력받아 나눗셈의 결과 출력
 * 0으로 나눌 경우 예외처리
 * 입력 받는 수가 정수가 아니거나 문자일 경우 예외처리
 * 실수 or 문자 입력 가능
 * 
 * 예외가 발생하는 부분은 입력받는 부분과 나눗셈 하는 부분임
 * 이 부분을 모두 try로 묶어줘야 함
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionTest3 {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);

		try {
			System.out.print("첫 번째 수 입력 : ");
			int num1 = stdIn.nextInt();
			System.out.print("두 번째 수 입력 : ");
			int num2 = stdIn.nextInt();
			System.out.println("나눗셈 결과 : " + num1/num2);
		}
		catch (ArithmeticException e) {
			System.out.println("나누는 수가 0이면 안 됩니다.");
		}
		catch (NumberFormatException e) {
			System.out.println("첫 번째 수는 정수만 입력하요.");
		}
		catch (InputMismatchException e) {
			System.out.println("두 번째 수는 정수만 입력하세요.");
		}
		catch (Exception e) {
			System.out.println("(경고) 예외 발생! \n" + e);
		}
		finally {
			System.out.println("프로그램 종료");
		}
	}

}
