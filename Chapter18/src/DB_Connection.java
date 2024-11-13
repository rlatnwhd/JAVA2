import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
	public static void main(String[] args) {
		// 드라이버 설정
		String jdbcDriver = "com.mysql.cj.jdbc.Driver";
		
		// 데이터베이스 URL 설정
		String url = "jdbc:mysql://localhost:3306/dbstudent?";
		
		// Connection 클래스 변수 선언.
		Connection conn;
		
		String id = "root"; // DB에 로그인할 ID
		String pw = "1234"; // MySQL 설정 시 입력한 비밀번호
		
		try {
			// 1단계 : JDBC 드라이버 로드
			Class.forName(jdbcDriver);
			
			// 2단계 : 데이터베이스 연결
			conn = DriverManager.getConnection(url, id, pw);
			// DriverManager 자바 어플리케이션을 JDBC드라이버에 연결시켜주는 클래스
			// getConnection() 메소드로 DB에 연결하여 Connection 객체 반환
			System.out.println("DB 연결 완료");
			
			// 5단계 : 연결 해제
			conn.close();
		}
		catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
		}
		catch(SQLException e) {
			System.out.println("DB 연결 오류");
		}
		
	}
}
