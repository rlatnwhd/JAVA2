import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest1 {
	public static void main(String[] args) {
		// TreeMap 생성 (이름:key, 상적:value)
		TreeMap<String, Integer> scores = new TreeMap<String, Integer>();
		
		scores.put("김수종", 100);
		scores.put("김미미", 63);
		scores.put("황수현", 47);
		scores.put("천승용", 81);
		scores.put("김유민", 84);
		scores.put("방극붕", 95);
		
		// 성적 출력 (key를 기준으로 자동 정렬)
		System.out.println("------- 학생 성적 -------");
		for(Map.Entry<String, Integer> entry : scores.entrySet()) {
			System.out.println("이름 : " + entry.getKey() + " | 성적 : " + entry.getValue());
		}
		
		// 점수에 따라 정렬하기 위해 List 생성
		List<Map.Entry<String, Integer>> entryList = new ArrayList<>(scores.entrySet());
		
		// 점수를 기준으로 정렬(오름차순) - 람다 표현식 사용
		entryList.sort(Comparator.comparingInt(Map.Entry::getValue));
		
		// 점수 순으로 출력 (낮은 점수부터)
		System.out.println("-----------------------");
		System.out.println("점수 순으로 출력 (낮은 점수부터) : " + entryList);
		
		// 최고, 최저 점수 출력
		System.out.println("-----------------------");
		System.out.println("최저 점수 : " + entryList.get(0).getValue() + "(" + entryList.get(0).getKey() + ")");
		System.out.println("최고 점수 : " + entryList.get(entryList.size() - 1).getValue() + "(" + entryList.get(entryList.size() - 1).getKey() + ")");

// 		// 이름(Key)를 기준으로 성적 출력
//		Map.Entry<String, Integer> highest = scores.lastEntry();
//		Map.Entry<String, Integer> lowest = scores.firstEntry();
//		System.out.println("-----------------------");
//		System.out.println("최고 점수 : " + highest.getValue() + "(" + highest.getKey() + ")");
//		System.out.println("최저 점수 : " + lowest.getValue() + "(" + lowest.getKey() + ")");
	}
}
