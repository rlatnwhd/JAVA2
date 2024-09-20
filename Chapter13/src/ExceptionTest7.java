import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionTest7 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		FileReader file = new FileReader("a.txt");

		int i;
		while((i = file.read()) != -1) {
			System.out.println((char)i);
		}
		
		file.close();
	}

}
