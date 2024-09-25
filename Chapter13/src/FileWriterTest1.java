import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWriterTest1 {

	public static void main(String[] args) throws IOException {
		Scanner stdIn = new Scanner(System.in);
		
		String source = "오늘밤 그대 모습 지울 수가 없네요\n"
				+ "슬픈 DJ 그대 모습 내마음은 흔들려요\n"
				+ "음악소리가 너무 작아요 그대 모습이 너무 멀어요\n"
				+ "슬픈 DJ 슬픈 DJ DJ\n"
				+ "음악소리가 너무 작아요 그대 모습이 너무 멀어요\n"
				+ "슬픈 DJ 슬픈 DJ DJ 나의 DJ";
		
		System.out.print("저장할 파일명을 입력 하세요 : ");
		String s = stdIn.next();
		
		// 저장한 파일 명으로 객채 생성
		FileWriter fw = new FileWriter(s); // 파일에 문자 출력
		
		fw.write(source); // 스트림 닫기
		
		System.out.println(s + "파일 생성 완료!");
		
		fw.close();
	}

}
