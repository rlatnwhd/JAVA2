/*
 * Windows 디렉토리에 대한 내용 출력
 * File 객체 사용
 */

import java.io.File;

public class FileDirTest1 {

	public static void main(String[] args) {
		String directory = "C:/Windows1";

		File f1 = new File(directory);
		
		try {
			System.out.println("검색 디렉토리 : " + directory);
			String s[] = f1.list();
			
			for(int i=0; i<s.length; i++) {
				File f = new File(directory + "/" + s[i]);
				if(f.isDirectory()) {
					System.out.println(s[i] + " : 디렉토리");
				}
				else {
					System.out.println(s[i] + " : 파일");
				}
			}
		}
		catch (Exception e) {
			System.out.println("지정한 " + directory + "은(는) 존재하지 않습니다.");
			System.out.println(e);
		}
	}

}
