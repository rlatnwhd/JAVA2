import java.util.Scanner;

// Join 클래스(회원가입) - ProgramManager(관리자) 클래스를 상속 받음
class Join extends ProgramManager {
	
	// Sign_in 메소드 - 회원가입을 진행하는 기능
	public void Sign_in() {
		// 사용자에게 이름, 아이디, 비밀번호, 이메일 입력받기
		Scanner stdIn = new Scanner(System.in);
		System.out.println("\n<< 회원가입 >>");
		System.out.print("이름 : ");
		String name = stdIn.nextLine();
		System.out.print("아이디 : ");
		String id = stdIn.nextLine();
		System.out.print("비밀번호 : ");
		String password = stdIn.nextLine();
		System.out.print("이메일 : ");
		String mail = stdIn.nextLine();
		
		// 계정 생성 여부 묻기
		System.out.print("계정을 생성하시겠습니까? (y/n): ");
    	char res = stdIn.next().charAt(0);
    	
    	// 회원가입을 한다면 --> 입력받은 정보를 새로운 배열에 저장하여 리스트에 추가
    	if (res == 'y' || res == 'Y' ) {
    		System.out.println("\n회원가입이 완료되었습니다.");
    		user = new String[] { name, id, password, mail };
    		users.add(user);
    	}
    	
    	// 그렇지 않다면(회원가입을 안한다면) - 사용자 메뉴 화면으로 돌아감
    	else { System.out.println("\n회원가입이 취소되었습니다."); }
	}
}
