/*
 * 나이를 입력 받아 출력하시오
 * 입력 받은 나이가 정수가 아닐 경우 예외 처리
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionTest8 {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		
		System.out.print("나이를 입력하세요 : ");
		try {
			int age = stdIn.nextInt();
			System.out.println("입력한 나이 : " + age + "세");
		}
		catch (InputMismatchException e) {
			System.out.println("정수만 입력 가능합니다.");
		} 
		catch (Exception e) {
			System.out.println("(경고) 예외 발생!");
		}
	}

}
