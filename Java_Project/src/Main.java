import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

// 프로그램의 진입점(메인) 클래스
// DB 연결 및 초기 설정 담당
public class Main {
    // 데이터베이스 연결 객체
    public static Connection conn;
    public static String date;  // 현재 날짜 저장
    
    // DB 재연결 메소드
    public static boolean reconnectDB() {
        try {
            if (conn == null || conn.isClosed()) {
                String jdbcDriver = "com.mysql.cj.jdbc.Driver";
                String url = "jdbc:mysql://localhost:3306/getoUserList?serverTimezone=UTC";
                String id = "root";
                String pw = "1234";
                
                Class.forName(jdbcDriver);
                conn = DriverManager.getConnection(url, id, pw);
                System.out.println("DB 재연결 완료");
                return true;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DB 재연결 실패");
            return false;
        }
    }
    
    // 메인 메소드
    public static void main(String[] args) {
        // JDBC 드라이버 로드
        LocalDateTime dateTime = LocalDateTime.now();
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";

        // 데이터베이스 URL 설정
        String url = "jdbc:mysql://localhost:3306/getoUserList?serverTimezone=UTC";
        String id = "root";
        String pw = "1234";

        date = dateTime.getYear() + "-";
        date += dateTime.getMonthValue() + "-";
        date += dateTime.getDayOfMonth();
        
        try {
            // 1단계 : JDBC 드라이버 로드
            Class.forName(jdbcDriver);

            // 2단계 : 데이터베이스 연결
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("DB 연결 완료");
            System.out.println(date);

            // 3단계: GUI 실행
            new PC_Privilege();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버 로드 에러");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 연결 오류");
            e.printStackTrace();
        }
    }
}

