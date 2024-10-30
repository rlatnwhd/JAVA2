import java.util.Scanner;

public class ScannerTest1 {

	public static void main(String[] args) {
		String day = "happy day, smile day, nice day, joyful day";
		
		Scanner stdIn = new Scanner(day);
		stdIn.useDelimiter("day, ");
		
		while(stdIn.hasNext()) {
			System.out.println(stdIn.next());
		}
	}

}
