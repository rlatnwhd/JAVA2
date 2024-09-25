// 해당 클래스는 직렬화하여 사용
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

class PersonInfo implements Serializable {
	String name;
	String city;
	int age;
	
	public PersonInfo(String name, String city, int age) {
		this.name = name;
		this.city = city;
		this.age = age;
	}
}
public class ObjectWriteTest1 {

	public static void main(String[] args) throws Exception, IOException {
		Scanner stdIn = new Scanner(System.in);
		System.out.print("저장할 파일명을 입력하세요 : ");
		String s = stdIn.next();
		String s1 = "*** 고객 정보 ***";
		
		// 직렬화된 객체를 2개 생성
		PersonInfo p1 = new PersonInfo("김수종", "부산", 20);
		PersonInfo p2 = new PersonInfo("천승용", "부산", 21);
		
		// 파일에 출력하기 위한 객체 생성
		// 직렬화된 객체를 2진수로 기록할 파일 지정
		// 객체를 기록할 객체 생성

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(s));
		
		oos.writeObject(s1);
		oos.writeObject(p1);
		oos.writeObject(p2);
		
		oos.close();
		
		System.out.println(s + "파일명으로 객체 파일을 생성");
	}
}
