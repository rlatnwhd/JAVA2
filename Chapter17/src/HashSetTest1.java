import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class HashSetTest1 {
	public static void main(String[] args) {
		HashSet<Integer> odd = new HashSet<Integer>();  // HashSet 객체 생성
		HashSet<Integer> even = new HashSet<Integer>();
		
		for(int i = 1; i <= 10; i += 2) {
			odd.add(i);          // 홀수 저장
			even.add(i+1);       // 짝수 저장
		}
		
		// set에 저장된 내용 출력
		System.out.println("----------- HashSet ------------");
		System.out.println("odd 집합 내용 : " + odd);
		System.out.println("even 집합 내용 : " + even);
		System.out.println("--------------------------------");
		
		boolean setChanged = odd.add(5);
		System.out.println("odd에 5 추가 가능? : " + setChanged);
		// set은 중복 허용 X
		// odd set에는 이미 '5'라는 요소가 포함되어 있어 추가가 불가능 함
		
		setChanged = even.add(12);
		System.out.println("even에 12 추가 가능? : " + setChanged);
		// even set에는 '12'라는 요소가 없기 때문에 추가가 가능
		
		System.out.println("--------------------------------");
		System.out.println("odd 집합 내용 : " + odd);
		System.out.println("even 집합 내용 : " + even);
		
		System.out.println("--------------------------------");
		System.out.println("odd에서 가장 작은 값 : " + Collections.min(odd));
		System.out.println("even에서 가장 큰 값 : " + Collections.max(even));
		
		// Iterator 객체를 제네릭으로 생성
		Iterator<Integer> even_it = even.iterator();
		Iterator<Integer> odd_it = odd.iterator();
		int sum = 0;
		
		System.out.println("--------------------------------");
		
		// even_it 객체를 이용하여 Hashset의 각 요소를 순차 검색
		// set은 순서가 없기 때문에 검색이 불가능 하지만 Iterator를 사용하면 검색이 가능함
		while(even_it.hasNext()) sum += even_it.next();
		System.out.println("even 집합의 합 : "+ sum);
		sum = 0;
		while(odd_it.hasNext()) sum += odd_it.next();
		System.out.println("odd 집합의 합 : "+ sum);
	}

}
