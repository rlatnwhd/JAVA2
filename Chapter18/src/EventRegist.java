import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EventRegist {
	 Scanner stdIn = new Scanner(System.in);
	 
     // 데이터베이스 URL 설정
	 String jdbcUrl = "jdbc:mysql://localhost:3306/dbevent?";
	
	 // Connection 클래스 변수 선언.
	 Connection conn;
	 
	 String id = "root"; // DB에 로그인할 ID
	 String pw = "1234"; // MySQL 설정 시 입력한 비밀번호
	 
	 PreparedStatement pstmt;
	 ResultSet rs;
	 
	 public EventRegist() {
		 System.out.println("## 이벤트 등록을 위해 이름과 이메일을 입력하세요 ##");
		 System.out.print("이름 : ");
		 String uname = stdIn.next();
		 System.out.print("이메일 : ");
		 String email = stdIn.next();
		 
		 connectDB();
		 registUser(uname,email);
		 printList();
		 closeDB();
	 }
	 
	 // DB연결 메소드
	 public void connectDB() {
		 try {
			 // 1단계: JDBC드라이버 로드
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 
			 // 2단계: 데이터베이스 연결
			 conn = DriverManager.getConnection(jdbcUrl, id, pw);
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	 }

	public void closeDB() {
		try {
			pstmt.close();
			rs.close();
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void registUser(String uname, String email) {
		String sql = "insert into event values(?,?)";
		try {
			// 3단계 : Stetement 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uname);
			pstmt.setString(2, email);
			
			// 4단계 : SQL 문 전송
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printList() {
		System.out.println("# 등록자 명단 #");
		String sql = "select * from event";
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("uname") + "\t" + rs.getString(2));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EventRegist er = new EventRegist();
	}
}

