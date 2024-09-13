// 사용자가 입력한 글자가 문자인지 숫자인지 판별

import java.util.Scanner;

public class isLetter {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		System.out.println("문자 또는 숫자 입력 : ");
		String input = stdIn.next();
		boolean letter = false; // 문자인지 아닌지 확인하는 변수
		
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i); // i번째 문자를 반환하여 c에 저장
			if(Character.isLetter(c)) {
				letter = true;
				break;
			}
		}
		
		if(letter = true) {
			System.out.println(input + "은 문자입니다.");
		}
		else {
			System.out.println(input + "은 숫자입니다.");
		}
	}

}
