import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileReaderTest1 {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);

		try {
			System.out.print("읽어 올 파일명을 입력하세요 : ");
			String s = stdIn.next();
			
			// 읽어 올 파일명으로 객체 생성
			FileReader fr = new FileReader(s);
			
			int i;
			while((i = fr.read()) != -1) {
				System.out.print((char)i);
			}
			
			fr.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다.");
		}
		catch (IOException e) {
			System.out.println("파일이 존재하지 않습니다.");
		}
	}

}
