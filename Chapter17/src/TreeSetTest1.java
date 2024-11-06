import java.util.LinkedList;
import java.util.TreeSet;

public class TreeSetTest1 {

	public static void main(String[] args) {
		// TreeSet 객체 생성
		TreeSet<Integer> num1 = new TreeSet<Integer>();
		TreeSet<Integer> num2 = new TreeSet<Integer>();

		// LinkedList 객체 생성
		LinkedList<Integer> num3 = new LinkedList<Integer>();
		LinkedList<Integer> num4 = new LinkedList<Integer>();
		
		System.out.println("----------- TreeSet ------------");
		for (int i = 4; i >= 0; i--) {
			num1.add(i);     // 결과 예측 : 4 3 2 1 0
			num2.add(i*2);   // 결과 예측 : 8 6 4 2 0
		}
		
		// set은 순서에 상관없이 항상 정렬되어 출력됨
		System.out.println("num1 집합의 내용 : " + num1);
		System.out.println("num2 집합의 내용 : " + num2);
		
		TreeSet<Integer> union = new TreeSet<Integer>(num1);         // 합집합
		TreeSet<Integer> intersection = new TreeSet<Integer>(num1);  // 교집합
		TreeSet<Integer> difference = new TreeSet<Integer>(num1);    // 차집합
		
		union.addAll(num2);
		intersection.retainAll(num2);
		difference.removeAll(num2);
		
		// 정렬 O, 중복 X
		System.out.println("--------------------------------");
		System.out.println("num1과 num2의 합집합 : " + union);
		System.out.println("num1과 num2의 교집합 : " + intersection);
		System.out.println("num1과 num2의 차집합 : " + difference);
		
		System.out.println("---------- LinkedList ----------");
		for (int i = 4; i >= 0; i--) {
			num3.add(i);     // 결과 예측 : 4 3 2 1 0
			num4.add(i*2);   // 결과 예측 : 8 6 4 2 0
		}
		
		// LinkedList 정렬 없이 그대로 출력
		System.out.println("num3 집합의 내용 : " + num3);
		System.out.println("num4 집합의 내용 : " + num4);
		
		LinkedList<Integer> union_list = new LinkedList<Integer>(num3);         // 합집합
		LinkedList<Integer> intersection_list = new LinkedList<Integer>(num3);  // 교집합
		LinkedList<Integer> difference_list = new LinkedList<Integer>(num3);    // 차집합
		
		union_list.addAll(num4);
		intersection_list.retainAll(num4);
		difference_list.removeAll(num4);
		
		// 정렬 X, 중복 O
		System.out.println("--------------------------------");
		System.out.println("num3과 num4의 합집합 : " + union_list);
		System.out.println("num3과 num4의 교집합 : " + intersection_list);
		System.out.println("num3과 num4의 차집합 : " + difference_list);
	}

}
