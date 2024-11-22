package DBStudent;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainStudent extends JFrame implements ActionListener {

	// 컨테이너 객체
	Container ct = getContentPane();
	
	// 패널 객체들
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	
	// 상태 메시지 라벨
	JLabel msg1 = new JLabel();
	
	// 학생 정보를 입력할 라벨들
	JLabel l1 = new JLabel("    연   번    ");
	JLabel l2 = new JLabel("    학   과    ");
	JLabel l3 = new JLabel("    학   번    ");
	JLabel l4 = new JLabel("    학   년    ");
	JLabel l5 = new JLabel("    이   름    ");
	JLabel l6 = new JLabel("    연락처    ");
	
	// 학생 정보 입력 필드들
	JTextField t1 = new JTextField(10);
	JTextField t2 = new JTextField(10);
	JTextField t3 = new JTextField(10);
	JTextField t4 = new JTextField(10);
	JTextField t5 = new JTextField(10);
	
	// 버튼들
	JButton b1 = new JButton("    등  록    ");
	JButton b2 = new JButton("    조  회    ");
	JButton b3 = new JButton("    수  정    ");
	JButton b4 = new JButton("    삭  제    ");
	
	// 학생 번호를 선택하는 콤보박스
	JComboBox cb;
	
	// 학생 목록을 출력할 텍스트 영역
	JTextArea ta;
	
	// 상태 메시지 변수
	String msg = "";
	
	// 전체 학생목록을 저장하는 ArrayList
	ArrayList<Student> datas = new ArrayList<Student>();
	
	// 데이터베이스 연동 객체
	StudentDAO Sdao = new StudentDAO();
	
	// 학생 객체
	Student student;
	
	// UI 구성 시작 메소드
	public void startUI() {
		ct.setLayout(new BorderLayout());
		
		// 각 패널에 레이아웃 설정
		p1.setLayout(new GridLayout(1, 1, 20, 2));
		p2.setLayout(new GridLayout(6, 1, 20, 2));
		p3.setLayout(new GridLayout(6, 1, 20, 2));
		p4.setLayout(new GridLayout(4, 1, 20, 2));
		p5.setLayout(new GridLayout(1, 1, 20, 2));
		
		// 상태 메시지 초기화
		msg1.setText(msg1 + " 프로그램이 시작 되었습니다.");
		msg1.setEnabled(false);
		
		// 학생 관리 번호를 위한 콤보박스
		cb = new JComboBox();
		
		// 학생 목록 출력 텍스트 영역 및 스크롤
		ta = new JTextArea(20, 40);
		JScrollPane scroll = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// 패널에 컴포넌트 배치
		p1.add(msg1);
		
		// 학생 정보 입력 라벨 추가
		p2.add(l1);
		p2.add(l2);
		p2.add(l3);
		p2.add(l4);
		p2.add(l5);
		p2.add(l6);
		
		// 학생 정보 입력 필드 추가
		p3.add(cb);
		p3.add(t1);
		p3.add(t2);
		p3.add(t3);
		p3.add(t4);
		p3.add(t5);
		
		// 버튼들 추가
		p4.add(b1);
		p4.add(b2);
		p4.add(b3);
		p4.add(b4);
		
		// 텍스트 영역을 스크롤 패널에 추가
		p5.add(scroll);
		
		// 각 버튼에 액션 리스너 추가
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		
		// 메인 컨테이너에 패널들 배치
		ct.add(p1, BorderLayout.NORTH);
		ct.add(p2, BorderLayout.WEST);
		ct.add(p3, BorderLayout.CENTER);
		ct.add(p4, BorderLayout.EAST);
		ct.add(p5, BorderLayout.SOUTH);
		
		// 화면 데이터 갱신
        refreshData();
		
        // 화면 크기 및 설정
        setResizable(false);
        setVisible(true);
	}
	
	// 생성자 - JFrame 초기화
	public MainStudent() {
		super("Student Information");
		setLayout(new BorderLayout(20, 20));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 700);
	}
	
	// 입력 필드를 초기화하는 메소드
	public void clearField() {
		t1.setText("");
		t2.setText("");
		t3.setText("");
		t4.setText("");
		t5.setText("");
	}
	
	// 학생 목록을 출력하는 메소드
	public void refreshData() {
		ta.setText("\n\n");
		clearField();
		
		// 헤더 출력
		ta.append("     연 번\t학 과\t\t학 번\t학 년\t이 름\t연락처\n");
		datas = Sdao.getAll();
		
		// 콤보 박스 데이터 갱신
		cb.setModel(new DefaultComboBoxModel(Sdao.getItems()));
		
		if(datas != null) {
			// 학생 목록을 출력
			for(Student p : datas) {
				StringBuffer sb = new StringBuffer();
				sb.append("     " + p.getNumber() + "\t");
				sb.append(p.getDept() + "\t\t");
				sb.append(p.getStudentID() + "\t");
				sb.append(p.getGrade() + "\t");
				sb.append(p.getName() + "\t");
				sb.append(p.getPhone() + "\n");
				ta.append(sb.toString());
			}
		}
	}
	
	// 버튼 클릭 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		// 등록 버튼 클릭시
		if(obj == b1) {
			student = new Student();
			student.setDept(t1.getText());
			student.setStudentID(Integer.parseInt(t2.getText()));
			student.setGrade(Integer.parseInt(t3.getText()));
			student.setName(t4.getText());
			student.setPhone(t5.getText());
			
			// 학생 등록 성공 여부 체크
			if(Sdao.insertStudent(student)) {
				msg1.setText(msg + "학생을 등록했습니다.");
			}
			else {
				msg1.setText(msg + "학생 등록이 실패 했습니다.");
			}
			// 결과 데이터 출력
			refreshData();
		}
		
		// 조회 버튼 클릭시
		else if(obj == b2) {
			String s = (String)cb.getSelectedItem();
			if(!s.equals("연번선택")) {
				student = Sdao.selectStudent(Integer.parseInt(s));
				if(student != null) {
					msg1.setText(msg + "학생 정보를 가져왔습니다.");
					t1.setText(student.getDept());
					t2.setText(String.valueOf(student.getStudentID()));
					t3.setText(String.valueOf(student.getGrade()));
					t4.setText(student.getName());
					t5.setText(student.getPhone());
				}
				else {
					msg1.setText(msg + "학생이 검색되지 않았습니다.");
				}
			}
			else {
				// 결과 데이터 출력
				refreshData();
			}
		}
		
		// 수정 버튼 클릭시
		else if(obj == b3) {
			student = new Student();
			student.setDept(t1.getText());
			student.setStudentID(Integer.parseInt(t2.getText()));
			student.setGrade(Integer.parseInt(t3.getText()));
			student.setName(t4.getText());
			student.setPhone(t5.getText());
			student.setNumber(Integer.parseInt((String)cb.getSelectedItem()));
			// 학생 정보 수정
			if(Sdao.updatestudent(student)) {
				msg1.setText(msg + "학생 정보를 수정했습니다.");
			}
			else {
				msg1.setText(msg + "학생 정보 수정에 실패 했습니다.");
			}
			
			// 결과 데이터 출력
			refreshData();
		}
		
		// 삭제 버튼 클릭시
		else if(obj == b4) {
			String s = (String)cb.getSelectedItem();
			
			// "전체"는 삭제 불가
			if(s.equals("전체")) {
				msg1.setText(msg + "전체 삭제는 되지 않습니다.");
			}
			else {
				// 학생 삭제
				if(Sdao.deletestudent(Integer.parseInt(s))) {
					msg1.setText(msg + "학생이 삭제되었습니다.");
				}
				else {
					msg1.setText(msg + "학생이 삭제되지 않았습니다.");
				}
			}
			
			// 결과 데이터 출력
			refreshData();
		}
	}

	// 메인 메소드 - 프로그램 실행
	public static void main(String[] args) {
		MainStudent ms = new MainStudent();
		ms.startUI();
	}

}
