import java.io.FileReader;

public class ExceptionTest6 {

	public static void main(String[] args) throws Exception {
		FileReader file = new FileReader("a.txt");

		int i;
		while((i = file.read()) != -1) {
			System.out.println((char)i);
		}
		
		file.close();
	}

}
