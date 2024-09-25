import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class ObjectReadTest1 {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		System.out.print("불러 올 파일명을 입력하세요 : ");
		String s = stdIn.next();
		
		try {
			ObjectInputStream ols = new ObjectInputStream(new FileInputStream(s));
			Object s2 = ols.readObject(); // 객체 읽어오기
			
			// readObject()메소드로 직렬화된 객체의 2진 데이터를
			// 읽어서 객체 타입으로 형변환된다.
			PersonInfo p1 = (PersonInfo)ols.readObject();
			PersonInfo p2 = (PersonInfo)ols.readObject();
			
			System.out.println(s2);
			System.out.println("이름 : " + p1.name);
			System.out.println("주소 : " + p1.city);
			System.out.println("나이 : " + p1.age);
			System.out.println("--------------------");
			System.out.println("이름 : " + p2.name);
			System.out.println("주소 : " + p2.city);
			System.out.println("나이 : " + p2.age);
			
			ols.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(s + " 파일이 없습니다.");
		}
		catch (IOException e) {
			System.out.println(s + " 파일이 없습니다.");
		}
		catch (ClassNotFoundException e) {
			System.out.println(s + " 파일이 없습니다.");
		}
	}

}
