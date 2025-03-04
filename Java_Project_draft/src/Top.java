// User 클래스 - 최상위 클래스
import java.util.ArrayList;

class User {
	// 1. 속성
	
	// users - 자료형이 문자열 배열인 리스트 (클래스 변수)
	protected static ArrayList<String[]> users = new ArrayList<>(); 
	// user - 사용자의 이름, 아이디, 비밀번호, 이메일을 담을 배열
	protected String[] user; 

	// 기본 생성자
	public User() {};
	
	// 메소드 --> 하위 클래스에서 오버라이딩 하여 사용할 메소드를 선언 (메소드명 절약)
    public void menu_option() {}	
    public void pm_menu_option() {}
    public void cupon_option() {}

    // 명시적 생성자 - 매개 변수가 4개
    // 사용자의 이름, 아이디, 비밀번호, 이메일을 가져옴
	public User(String name, String id, String password, String mail) { 
		// user - 배열에 이름, 아이디, 비밀번호, 메일을 담은 후
        this.user = new String[] { name, id, password, mail };
        // 생성한 배열을 리스트에 추가
        users.add(user);
    }
}
