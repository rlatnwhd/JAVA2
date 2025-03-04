import java.util.Scanner;

//main 메소드가 있는 클래스
public class Main_Activity {
    public static void main(String[] args) {
    	// 관리자의 이름, 아이디, 비밀번호, 이메일을 User 클래스에 배정(고정값)
    	new User("사장님", "사장님", "a123456789", "12345@gmail.com");
    	// Menu 클래스의 객체 생성
    	Menu menu = new Menu();
    	
    	int num = 9;
    	
    	// 입력받기
    	Scanner stdIn = new Scanner(System.in);
    	while(num != 0) {
    		System.out.println("안녕하세요. PC방 GETO 키오스크입니다.");
	    	System.out.println("1. 사용자");
	    	System.out.println("2. 관리자");
	    	System.out.println("0. 종료");
	    	System.out.print("(입력 후 Enter) : ");
	    	num = stdIn.nextInt();
	    	
	    	switch(num) {
	    		case 0 :	// 종료
	    			System.out.println("\nGETO 키오스크를 이용해주셔서 감사합니다.");
	    			break;
	    		case 1 :	// 사용자
	    			menu.displayMenu();
	    			break;
	    		case 2 :	// 관리자
	    			menu.pmlogin();
	    			break;
	    		default :	// 해당 값 벗어남
	    			System.out.println("\n다시 입력하여 주십시오!\n");
	    	}
    	}
    }
}
