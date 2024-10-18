import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class JPanel1 extends JFrame implements ActionListener {
	// 라벨 및 텍스트필드, 텍스트에리어 정의
	JLabel jl1 = new JLabel("길이를 입력하고, 도형의 버튼을 클릭하세요.");
	JLabel jl2 = new JLabel("결과");
	
	JTextField jtf = new JTextField();
	JTextArea jta = new JTextArea(2, 20);

	public JPanel1() {
		Container ct = getContentPane(); // 컨테이너 생성
		
		ct.setLayout(new BorderLayout()); // 컨테이너 배치 관리자 설정
		
		JPanel jp1 = new JPanel(); // 판넬 3개 생성
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		
		jp1.setLayout(new GridLayout(2, 1)); // 판넬들의 배치관리자 설정
		jp2.setLayout(new FlowLayout());
		jp3.setLayout(new GridLayout(2, 1));
		
		jp1.add(jl1); // 판넬1에 라벨과 텍스트틸드 삽입
		jp1.add(jtf);
		
		JButton circle = new JButton("원"); // 필요한 버튼 생성 
		JButton triangle = new JButton("삼각형");
		JButton square = new JButton("사각형");
		JButton reset = new JButton("리셋");
		
		circle.addActionListener(this); // 버튼의 액션 리스너 등록
		triangle.addActionListener(this);
		square.addActionListener(this);
		reset.addActionListener(this);
		
		jp2.add(circle); // 판넬2에 버튼들 삽입
		jp2.add(triangle);
		jp2.add(square);
		jp2.add(reset);
		
		jta.setEnabled(false); // 텍스트에리어 수정 불가
		
		jp3.add(jl2); // 판넬3에 라벨과 텍스트에리어 삽입
		jp3.add(jta);
		
		ct.add(jp1, BorderLayout.NORTH); // BorderLayout으로 각 판넬의 위치 설정
		ct.add(jp2, BorderLayout.CENTER);
		ct.add(jp3, BorderLayout.SOUTH);
		
		// 컨테이너 설정
		setTitle("MBTI Project");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try { // 텍스트 필드에 들어간 값을 확인할 try/catch
			String s = e.getActionCommand(); // 버튼의 값을 가져옴
			int num = Integer.parseInt(jtf.getText()); // 문자로 입력된 숫자로 정수로 변환
													   // 하지만 공백 및 문자열인 경우는 예외 처리 catch 블록으로 이동 
			if(s == "원") { // 텍스트 에리어의 텍스트를 각각의 결과로 수정
				jta.setText("원의 넓이 : " + num + " X " + num + " X  3.14 = " + num*num*3.14);
			}
			else if(s == "삼각형") {
				jta.setText("삼각형의 넓이 : " + num + " X " + num + " / 2 = " + num*num/2);
			}
			else if(s == "사각형") {
				jta.setText("사각형의 넓이 : " + num + " X " + num + " = " + num*num);
			}
			else if(s == "리셋") {
				jtf.setText("");
				jta.setText("리셋!");
			}
		}
		catch(Exception ex) { // 입력 오류 시
			jta.setText("길이(숫자)를 입력하셔야 합니다.");
		}
	}
}
public class Exam_Test_2 {

	public static void main(String[] args) {
		new JPanel1();
	}

}
