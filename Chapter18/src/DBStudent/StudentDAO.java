package DBStudent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class StudentDAO {
   String jdbcUrl = "jdbc:mysql://localhost:3306/studentdb?"; // DB URL 설정
   
   Connection conn; // DB 연결 객체
   
   String id = "root"; // DB 로그인 ID
   String pw = "1234"; // DB 로그인 비밀번호
   
   PreparedStatement pstmt; // SQL 쿼리 실행 객체
   ResultSet rs; // 쿼리 실행 결과 객체
   
   Vector<String> items = null; // 콤보박스에 사용할 학생 관리 번호 목록
   String sql; // SQL 쿼리문 저장 변수
   
   // DB 연결 메소드
   public void connectDB() {
      try { 
         Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC 드라이버 로드
         conn = DriverManager.getConnection(jdbcUrl, id, pw); // DB 연결
      } catch (Exception e) {
         e.printStackTrace(); // 예외 처리
      }
   }
   
   // DB 연결 종료 메소드
   public void closeDB() {
      try {
         if (pstmt != null) pstmt.close(); // PreparedStatement 종료
         if (conn != null) conn.close();   // Connection 종료
      } catch (SQLException e) {
         e.printStackTrace(); // 예외 처리
      }
   }
   
   // 콤보박스의 학생관리 번호 목록을 위한 벡터 리턴
   public Vector<String> getItems() {
      return items;
   }
   
   // 전체 학생 목록을 가져오는 메소드
   public ArrayList<Student> getAll() {
      connectDB(); // DB 연결
      sql = "select * from student"; // SQL 쿼리문 설정
      
      // 전체 학생 데이터를 저장할 ArrayList 초기화
      ArrayList<Student> datas = new ArrayList<Student>();
      
      // 학생 관리 번호 콤보박스 데이터를 위한 벡터 초기화
      items = new Vector<String>();
      items.add("연번선택"); // 콤보박스의 첫 번째 항목으로 '연번선택' 추가
      
      try {
         pstmt = conn.prepareStatement(sql); // 쿼리 준비
         rs = pstmt.executeQuery(); // 쿼리 실행 결과 받기
         
         // 쿼리 결과가 있을 때마다 반복하여 Student 객체에 데이터 저장
         while(rs.next()){
            Student s = new Student();
            s.setNumber(rs.getInt("number")); // 학생 번호
            s.setDept(rs.getString("Dept"));  // 학과
            s.setStudentID(rs.getInt("StudentID")); // 학생 ID
            s.setGrade(rs.getInt("Grade"));  // 학년
            s.setName(rs.getString("Name")); // 이름
            s.setPhone(rs.getString("Phone")); // 전화번호
            datas.add(s); // 학생 객체를 ArrayList에 추가
            items.add(String.valueOf(rs.getInt("number"))); // 학생 번호를 콤보박스 항목에 추가
         }
      } 
      catch(SQLException e) {
         e.printStackTrace(); // 예외 처리
         return null; // 오류 발생 시 null 반환
      }
      finally {
         closeDB(); // DB 연결 종료
      }
      return datas; // 학생 목록 반환
   }
   
   // 새로운 학생을 등록하는 메소드
   public boolean insertStudent(Student student) {
      connectDB(); // DB 연결
      sql = "insert into student(dept, studentID, grade, name, phone) values(?,?,?,?,?)"; // SQL 쿼리문 설정
      
      try {
         pstmt = conn.prepareStatement(sql); // 쿼리 준비
         pstmt.setString(1, student.getDept()); // 학과
         pstmt.setInt(2, student.getStudentID()); // 학생 ID
         pstmt.setInt(3, student.getGrade()); // 학년
         pstmt.setString(4, student.getName()); // 이름
         pstmt.setString(5, student.getPhone()); // 전화번호
         pstmt.executeUpdate(); // 데이터 삽입
      } catch (SQLException e) {
         e.printStackTrace(); // 예외 처리
         return false; // 삽입 실패 시 false 반환
      }
      finally {
         closeDB(); // DB 연결 종료
      }
      return true; // 삽입 성공 시 true 반환
   }
   
   // 선택한 학생의 관리 번호에 해당하는 정보를 가져오는 메소드
   public Student selectStudent(int number) {
      connectDB(); // DB 연결
      sql = "select * from student where number=?"; // SQL 쿼리문 설정
      Student s = null; // 학생 객체 선언
      
      try {
         pstmt = conn.prepareStatement(sql); // 쿼리 준비
         pstmt.setInt(1, number); // 학생 번호 설정
         rs = pstmt.executeQuery(); // 쿼리 실행
         
         if (rs.next()) { // 쿼리 결과가 있으면
            s = new Student();
            s.setNumber(rs.getInt("number")); // 학생 번호
            s.setDept(rs.getString("Dept"));  // 학과
            s.setStudentID(rs.getInt("StudentID")); // 학생 ID
            s.setGrade(rs.getInt("Grade"));  // 학년
            s.setName(rs.getString("Name")); // 이름
            s.setPhone(rs.getString("Phone")); // 전화번호
         }
      } catch(SQLException e) {
         e.printStackTrace(); // 예외 처리
         return null; // 오류 발생 시 null 반환
      } finally {
         closeDB(); // DB 연결 종료
      }
      return s; // 학생 정보 반환
   }
   
   // 수정된 정보로 학생 정보를 업데이트 하는 메소드
   public boolean updatestudent(Student student) {
      connectDB(); // DB 연결
      sql = "update student set dept=?, studentID=?, grade=?, name=?, phone=? where number=?"; // SQL 쿼리문 설정
      
      try {
         pstmt = conn.prepareStatement(sql); // 쿼리 준비
         pstmt.setString(1, student.getDept()); // 학과
         pstmt.setInt(2, student.getStudentID()); // 학생 ID
         pstmt.setInt(3, student.getGrade()); // 학년
         pstmt.setString(4, student.getName()); // 이름
         pstmt.setString(5, student.getPhone()); // 전화번호
         pstmt.setInt(6, student.getNumber()); // 학생 번호
         pstmt.executeUpdate(); // 데이터 업데이트
      } catch(SQLException e) {
         e.printStackTrace(); // 예외 처리
         return false; // 업데이트 실패 시 false 반환
      } finally {
         closeDB(); // DB 연결 종료
      }
      return true; // 업데이트 성공 시 true 반환
   }
   
   // 선택한 학생을 삭제하는 메소드
   public boolean deletestudent(int number) {
      connectDB(); // DB 연결
      sql = "delete from student where number=?"; // SQL 쿼리문 설정
      try {
         pstmt = conn.prepareStatement(sql); // 쿼리 준비
         pstmt.setInt(1, number); // 학생 번호 설정
         pstmt.executeUpdate(); // 데이터 삭제
      } catch (SQLException e) {
         e.printStackTrace(); // 예외 처리
         return false; // 삭제 실패 시 false 반환
      } finally {
         closeDB(); // DB 연결 종료
      }
      return true; // 삭제 성공 시 true 반환
   }
}
