import java.util.Scanner;

// Member 클래스(회원 로그인) - User 클래스 상속받음
class Member extends User {
	// Payment 클래스의 객체 생성
	Payment pay = new Payment();
	
	// Member_login 메소드 - 회원 로그인 후 쿠폰 선택, 결제를 진행하는 메소드
	public void Member_login() {
		// 아이디, 비밀번호 입력받기
        Scanner stdIn = new Scanner(System.in);
        System.out.println("\n<< 회원 로그인 >>");
        System.out.print("ID : ");
        String id = stdIn.nextLine();
        System.out.print("PW : ");
        String pw = stdIn.nextLine();

        // 회원 정보(아이디, 비밀번호)가 일치하는지 비교할 변수 login_success을 false로 초기화
        boolean login_success = false;
        char res = 'y';
        
        for (String[] userInfo : users) {
        	// userInfo 배열의 1번째 요소(아이디)와 2번째 요소(비밀번호) 라면...
            if (userInfo[1].equals(id) && userInfo[2].equals(pw)) 
            {
            	// res가 Y이거나 y일 때까지 반복
            	while(res == 'y' || res == 'Y') {
            		// userInfo 배열의 0번째 요소는 사용자의 이름이 저장되어 있음
            		System.out.println("환영합니다, " + userInfo[0] + "님!");
            		System.out.println("원하는 자리의 PC 전원을 키시면 바로 사용 가능합니다.");
            		System.out.print("쿠폰을 구매하시겠습니까? (y/n): ");
                	res = stdIn.next().charAt(0);
                	
                	// res가 Y이거나 y라면 쿠폰 결제창으로 넘어감
                	if (res == 'y' || res == 'Y' ) { pay.makePayment(); }
                	
                	// 그렇지 않다면(n 누른 경우) 이전에 사용자 메뉴창으로 돌아감
                	else { 
                		System.out.println("\n이전 화면으로 돌아갑니다."); 
                		login_success = true;	// true로 초기화 하여 아래 조건문에 대한 문장이 출력되지 않게 함
                	}
            	}
                break;	// 조건 확인 후 break 문으로 빠져나온 후, 아이디 비밀번호를 검사함
            }
        }
        if (login_success == false) {System.out.println("\n아이디 또는 비밀번호가 올바르지 않습니다.");}
    }
}