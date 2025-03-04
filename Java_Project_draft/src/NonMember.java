import java.util.Random;
import java.util.Scanner;

// Guest 클래스(비회원) - Payment 클래스를 상속 받음
class Guest extends Payment {
    public void Non_menber_login() {
    	// Scanner, Random 객체 생성
    	Scanner stdIn = new Scanner(System.in);
    	Random random = new Random();
    	
    	System.out.println("\n<< 비회원 로그인 >>");
    	System.out.println("비회원으로 사용하시려면 쿠폰을 구매하셔야합니다.");
    	System.out.print("계속 진행하시겠습니까? (y/n): ");
    	char res = stdIn.next().charAt(0);
    	
    	// 입력받은 res가 y, Y이라면 - 카드 번호 발급
    	if (res == 'y' || res == 'Y' ) {
    		makePayment();
    		int randomCode = random.nextInt(9000) + 1000;
    		System.out.println("1회성 비회원 카드번호가 발급되었습니다.");
    		System.out.println("PC에서 카드번호를 입력하시면 이용이 가능합니다.");
    		System.out.println("카드 번호 : " + randomCode);
    	}
    	
    	// 그렇지 않다면(n을 누른 경우) - 이전 화면으로 돌아감
    	else { System.out.println("\n이전 화면으로 돌아갑니다."); }
    }
}