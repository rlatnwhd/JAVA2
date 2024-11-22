package DBStudent;

// 학생 정보 테이블 데이터 표현을 위한 클래스
public class Student {
	// 컬럼정보에 따른 필드 선언
	private int Number;   // 연번 (학생 번호)
	private String Dept;  // 학과
	private int studentID; // 학번
	private int Grade;     // 학년
	private String Name;   // 이름
	private String Phone;  // 연락처
	
	// 연번 (학생 번호) getter와 setter
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	
	// 학과 getter와 setter
	public String getDept() {
		return Dept;
	}
	public void setDept(String dept) {
		Dept = dept;
	}
	
	// 학번 getter와 setter
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	
	// 학년 getter와 setter
	public int getGrade() {
		return Grade;
	}
	public void setGrade(int grade) {
		Grade = grade;
	}
	
	// 이름 getter와 setter
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	// 연락처 getter와 setter
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	
}
