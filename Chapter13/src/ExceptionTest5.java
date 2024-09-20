/*
 * 파일 입출력에 대해 FileNotFoundException, IOException 예외 발생
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionTest5 {

	public static void main(String[] args) {
		try {
			FileReader file = new FileReader("a.txt");

			int i;
			while((i = file.read()) != -1) {
				System.out.println((char)i);
			}
			
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("지정된 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
