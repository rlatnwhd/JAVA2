import java.util.Scanner;
import java.util.Stack;

public class StackTest1 {

	public static void main(String[] args) {
		Stack<String> stack = new Stack<String>();
		
		// 키보드로부터 4개의 과일 이름을 입력받아 stack에 저장
		Scanner stdIn = new Scanner(System.in);
		for(int i = 0; i < 4; i++) {
			System.out.print("과일 이름을 입력하세요 : ");
			String fruits = stdIn.next();
			stack.push(fruits);
		}
		System.out.println("과일 리스트<스택> : " + stack);
		
		// 스택에서 과일 찾기
		System.out.print("검색할 과일 이름 입력 : ");
		String f_name = stdIn.next();
		int fruit = stack.search(f_name); // 검색한 과일의 위치 저장
		
		if(fruit != -1) {
			System.out.println("스택에서 " + f_name + "의 위치는 " + fruit + "번째 입니다.");
		}
		else {
			System.out.println("스택에 " + f_name + "(이)가 존재하지 않습니다.");
		}
		
		// 스택에서 과일 삭제
		System.out.println("\n과일 리스트 삭제<스택>");
		while(!stack.empty()) {
			String name = stack.pop();
			System.out.println("스택에서 삭제 : " + name);
		}
	}

}
