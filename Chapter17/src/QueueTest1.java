import java.util.LinkedList;
import java.util.Scanner;

public class QueueTest1 {

	public static void main(String[] args) {
		// Queue 인터페이스를 포함하는 LinkedList 클래스 사용
		LinkedList<String> queue = new LinkedList<String>();

		// 키보드로부터 4개의 과일 이름을 입력받아 queue에 저장
		Scanner stdIn = new Scanner(System.in);
		for(int i = 0; i < 4; i++) {
			System.out.print("과일 이름을 입력하세요 : ");
			String fruits = stdIn.next();
			queue.offer(fruits);
		}
		System.out.println("과일 리스트<큐> : " + queue);
		
		// 큐에서 과일 찾기
		System.out.print("검색할 과일 이름 입력 : ");
		String f_name = stdIn.next();
		int fruit = queue.indexOf(f_name);
		
		if(fruit != -1) {
			System.out.println("스택에서 " + f_name + "의 위치는 " + fruit + "번째 입니다.");
		}
		else {
			System.out.println("스택에 " + f_name + "(이)가 존재하지 않습니다.");
		}
		
		// 큐에서 과일 삭제
		System.out.println("\n과일 리스트 삭제<큐>");
		while(!queue.isEmpty()) {
			String name = queue.poll();
			System.out.println("스택에서 삭제 : " + name);
		}
		
		
	}

}
