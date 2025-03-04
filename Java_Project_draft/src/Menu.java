import java.util.Scanner;

// Menu 클래스(메뉴 선택) - ProgramManager(관리자) 클래스를 상속 받음
class Menu extends ProgramManager { 
	// 부모의 메소드 오버라이딩 - 메뉴를 보여주는 기능
    @Override
    public void menu_option() { 
        System.out.println("\n<< 메뉴를 선택하여 주십시오 >>");
        System.out.println("1. 회원 로그인");
        System.out.println("2. 비회원 로그인");
        System.out.println("3. 회원가입");
        System.out.println("0. 처음화면으로");
        System.out.print("(입력 후 Enter) : ");
    }

    // displayMenu 메소드 - 선택된 메뉴에 따라 메소드를 호출하는 기능
    public void displayMenu() {
    	// Scanner 객체 생성
        Scanner stdIn = new Scanner(System.in);
        
        // 각 클래스에 대한 객체 생성
        Member m = new Member();
        Join j = new Join();
        Guest g = new Guest();
        
        int choice = 9;

        // choice 가 0이 아닐 때까지 반복한다
        while (choice != 0) {
        	// 메뉴를 보여주는 메소드를 호출, 메뉴를 먼저 보여줌
            menu_option(); 

            choice = stdIn.nextInt();

            switch (choice) {
                case 1: 	// 회원 로그인 선택
                    m.Member_login();
                    break;
                case 2:		// 비회원 로그인 선택
                	g.Non_menber_login();
                    break;
                case 3:		// 회원 가입 선택
                	j.Sign_in();
                    break;
                case 0:		// 처음 메뉴 화면 선택
                    System.out.println("\n처음 화면으로 돌아갑니다.\n");
                    break;
                default:	// 범위 내의 메뉴를 선택한 경우
                    System.out.println("\n잘못된 선택입니다. 다시 선택해주세요.\n");
            }
        }
    }
}