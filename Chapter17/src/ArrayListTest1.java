import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ArrayListTest1 {

	public static void main(String[] args) {
		// 문자열 배열 생성
		String[] name = {"kim", "lee", "park", "jung", "oh"};
		
		// 문자열의 배열을 이용하여 리스트 객체(제네릭) 생성
		ArrayList<String> lname = new ArrayList<String>(Arrays.asList(name));
		System.out.println("초기값 : " + lname);
		
		// 리스트 마지막 부분에 요소 추가 ("ha");
		lname.add("ha");
		System.out.println("마지막에 \"ha\"요소 추가 : " + lname);
		
		// 배열의 요소 값 변경
		lname.set(0, "김");
		System.out.println("0번지를 \"김\"으로 변경 : " + lname);
		
		// 원하는 번지에 요소 추가 ("곽");
		lname.add(3, "곽");
		System.out.println("3번지에 \"곽\"요소 추가 : " + lname); // 추가로 인한 인덱스는 자동으로 조정
		
		// 무작위 재배열
		Collections.shuffle(lname);
		System.out.println("배열의 요소를 무작위로 배치 : " + lname); // 실행 시 랜덤으로 재배열
		
		// 배열 정렬
		Collections.sort(lname);
		System.out.println("정렬 : " + lname); // 내림차순으로 정렬(영어 우선)
		
		// 원하는 요소 출력
		System.out.println("5번째 값 출력 : " + lname.get(4)); // 5번쨰 값(인덱스 4) 출력
		
		// 리스트의 모든 요소를 "김"으로 변경
		Collections.fill(lname, "김");
		System.out.println("모든 요소를 \"김\"으로 변경 : " + lname);
	}

}
