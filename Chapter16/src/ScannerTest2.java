import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerTest2 {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		try {
			System.out.print("검색을 원하는 학생의 학번을 입력하시오 : ");
			
			int id = stdIn.nextInt();
			Scanner idNum = new Scanner(new File("phone.txt"));
			
			while(idNum.hasNext()) {
				if(id == idNum.nextInt()) {
					System.out.println(id + "학생의 전화번호 : " + idNum.next());
					return;
				}
				else {
					idNum.next();
				}
			}
			System.out.println("해당 학생 전화번호가 없습니다.");
		}
		catch (FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다.");
		}
		catch (InputMismatchException e){
			System.out.println("잘못 입력하셨습니다.");
		}
	}

}
