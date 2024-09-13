
public class StringBufferTest1 {

	public static void main(String[] args) {
		StringBuffer sb1 = new StringBuffer("Hello Java");
		StringBuffer sb2 = new StringBuffer("처음 시작하는 자바");
		
		System.out.println("sb1의 내용 : " + sb1);
		System.out.println("sb2의 내용 : " + sb2);
		
		System.out.println("문자열 끼워넣기 : " + sb2.insert(8, "Power "));
		sb2.setCharAt(5, '되');
		System.out.println("문자 바꾸기 : " + sb2);
		
		sb2.setLength(5);
		System.out.println("sb2의 내용 : " + sb2);
	}

}
