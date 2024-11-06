import java.util.HashMap;
import java.util.Scanner;

public class HashMapTest1 {

	public static void main(String[] args) {
		// HashMap 객체 생성, key와 value의 쌍으로 이루어 져야 함
		HashMap<String, String> dict = new HashMap<String, String>();

		// 3개의 (key, value) 쌍으로 이루는 dict 만들기
		dict.put("사과", "apple");
		dict.put("바나나", "banana");
		dict.put("메론", "melon");
		dict.put("포도", "grape");
		dict.put("파인애플", "pineapple");
		dict.put("키위", "kiwi");
		dict.put("망고", "mango");
		dict.put("배", "pear");
		dict.put("복숭아", "peach");
		
		// 사용자로부터 한글 단어를 입력 받아 영어 단어 검색
		Scanner stdIn = new Scanner(System.in);
		
		while(true) {
			System.out.print("과일 영단어 검색(0은 종료) : ");
			String kor = stdIn.next();
			
			if(kor.equals("0")) {
				System.out.println("종료");
				break;
			}
			
			// HashMap에서 key(kor)의 value(eng) 검색
			String eng = dict.get(kor);
			
			if(eng == null)
				System.out.println(kor + "은(는) 존재하지 않습니다.");
			else
				System.out.println(eng);
		}
	}

}
