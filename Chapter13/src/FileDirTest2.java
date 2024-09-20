/*
 * a.txt 파일 정보 출력
 */

import java.io.File;

public class FileDirTest2 {

	public static void main(String[] args) {
		File f1 = new File("C:\\Users\\kimsj\\OneDrive - SILLA UNIVERSITY\\바탕 화면\\코딩\\Eclipse-Java\\Chapter13\\a.txt");
		
		try {
			System.out.println("파일 이름 : " + f1.getName());
			System.out.println("파일 경로 : " + f1.getPath());
			System.out.println(f1.exists() ? "파일 확인!" : "파일 확인 불가");
			System.out.println("파일 크기 : " + f1.length() + "Bytes");
		}
		catch (Exception e) {
			System.out.println("해당 파일을 찾을 수 없습니다.");
			System.out.println("어떤 문제 일까요?");
			System.out.println(e);
		}
	}

}
