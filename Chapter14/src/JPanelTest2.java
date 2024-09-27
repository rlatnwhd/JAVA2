import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class JPanel2 extends JFrame {
	public JPanel2() {
		// 프레임으로부터 컨테이너 생성
		Container ct = getContentPane();
		
		// 배치 관리자 설정. BorderLayout
		ct.setLayout(new BorderLayout());
		
		// 라디오 버튼이 추가될 판넬 객체 생성
		JPanel jp1 = new JPanel();
		
		// jp1에 GridLayout 배치 관리자 설정
		jp1.setLayout(new GridLayout(5, 1));
		
		// 컴포넌트 생성하여 바로 컨테이너에 추가
		jp1.add(new JRadioButton("JAVA"));
		jp1.add(new JRadioButton("C"));
		jp1.add(new JRadioButton("Python"));
		jp1.add(new JRadioButton("JS"));
		jp1.add(new JRadioButton("JSP"));
		
		// 버튼이 추가될 판넬 객체 생성
		JPanel jp2 = new JPanel();
		
		// jp2에 GridLayout 배치 관리자 설정
		jp2.setLayout(new GridLayout(5, 1));
		
		jp2.add(new JButton("JAVA"));
		jp2.add(new JButton("C"));
		jp2.add(new JButton("Python"));
		jp2.add(new JButton("JS"));
		jp2.add(new JButton("JSP"));
		
		// 레이블이 추가될 판넬 객체 생성
		JPanel jp3 = new JPanel();
		
		// jp2에 GridLayout 배치 관리자 설정
		jp3.setLayout(new GridLayout(1, 1));
		
		// 판넬 3에 레이블 추가
		jp3.add(new JLabel("좋아하는 프로그래밍 언어를 선택하세요!"));
		
		// 컨테이너에 판넬 올리기 (쟁반에 접시 올리기) (위치 선정)
		ct.add(jp1, BorderLayout.WEST);
		ct.add(jp2, BorderLayout.EAST);
		ct.add(jp3, BorderLayout.NORTH);
		
		setTitle("JPanel Test");
		setSize(500, 300);
		
		// 윈도우 창 종료 시 프로세스 닫기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 화면에 출력
		setVisible(true);
	}
}

public class JPanelTest2 {

	public static void main(String[] args) {
		new JPanel2();
	}

}
