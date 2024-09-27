import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

class JPanel1 extends JFrame {
	public JPanel1() {
		// 프레임으로부터 컨테이너 생성
		Container ct = getContentPane();
		
		// 배치 관리자 설정. FlowLayout
		ct.setLayout(new FlowLayout());
		
		// 컴포넌트 생성하여 바로 컨테이너에 추가
		ct.add(new JRadioButton("JAVA"));
		ct.add(new JRadioButton("C"));
		ct.add(new JRadioButton("Python"));
		ct.add(new JRadioButton("JS"));
		ct.add(new JRadioButton("JSP"));
		
		ct.add(new JButton("JAVA"));
		ct.add(new JButton("C"));
		ct.add(new JButton("Python"));
		ct.add(new JButton("JS"));
		ct.add(new JButton("JSP"));
		
		setTitle("JPanel Test");
		setSize(300, 200);
		
		// 윈도우 창 종료 시 프로세스 닫기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 화면에 출력
		setVisible(true);
	}
}

public class JPanelTest1 {

	public static void main(String[] args) {
		new JPanel1();
	}

}
