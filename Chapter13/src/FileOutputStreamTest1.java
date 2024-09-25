import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileOutputStreamTest1 {

	public static void main(String[] args) throws IOException {
		Scanner stdIn = new Scanner(System.in);
		
		System.out.print("저장할 파일명을 입력하세요 : ");
		String s = stdIn.next();
		
		// 파일명으로 객체 생성
		FileOutputStream fos = new FileOutputStream(s);
		
		for(int i=1; i<=20; i++) {
			fos.write(i);
		}
		
		fos.close();
		
		System.out.println(s + " 파일명으로 바이트 파일 생성 완료!");
	}

}
