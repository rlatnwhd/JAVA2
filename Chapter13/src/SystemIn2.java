import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SystemIn2 {

	public static void main(String[] args) {
		try {
			System.out.print("데이터 입력 : ");
			InputStream is = System.in; // byte 타입을 처리
			// 한글 처리를 위해 문자 기반 스트림 사용
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String data = br.readLine(); // 값 읽어오기
			// 1byte값만 읽어온다
			// -> 한글은 제대로 출력이 불가능 (글자 깨짐)
			System.out.println("입력한 데이터 : " + data);
			
			is.close(); // 입력 스트림 닫기
		} catch (Exception e) {
			System.out.print("입력한 데이터가 없습니다.");
		}
		
	}

}
