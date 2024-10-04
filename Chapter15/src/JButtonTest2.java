import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

class JButton2 extends JFrame implements ActionListener {
	private JTextField jtf = new JTextField(10);
	
	public JButton2() {
		jtf.setEditable(false);
		
		ImageIcon apple = new ImageIcon("images\\apple.jpg");
		ImageIcon banana = new ImageIcon("images\\banana.jpg");
		ImageIcon persimmom = new ImageIcon("images\\persimmom.jpg");
		ImageIcon pear = new ImageIcon("images\\pear.jpg");
		ImageIcon grape = new ImageIcon("images\\grape.jpg");
		
		JButton jp1 = new JButton("사과",apple);
		JButton jp2 = new JButton("바나나",banana);
		JButton jp3 = new JButton("감",persimmom);
		JButton jp4 = new JButton("배",pear);
		JButton jp5 = new JButton("포도",grape);
		
		
		jp1.addActionListener(this);
		jp2.addActionListener(this);
		jp3.addActionListener(this);
		jp4.addActionListener(this);
		jp5.addActionListener(this);
		
		// 컨테이너 생성
		Container ct = getContentPane();
		
		// 배치 관리자 생성(Grid)
		ct.setLayout(new GridLayout(3, 2));
		
		// 컨테이너 버튼 추가
		ct.add(jp1);
		ct.add(jp2);
		ct.add(jp3);
		ct.add(jp4);
		ct.add(jp5);
		ct.add(jtf);
		
		// 컨테이너 설정
		setTitle("Image Button Test2");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "사과") {
			jtf.setText("사과입니다");
		}
		else if (e.getActionCommand() == "바나나") {
			jtf.setText("바나나입니다");
		}
		else if (e.getActionCommand() == "감") {
			jtf.setText("감입니다");
		}
		else if (e.getActionCommand() == "배") {
			jtf.setText("배입니다");
		}
		else {
			jtf.setText("포도입니다");
		}

	}
}
public class JButtonTest2 {

	public static void main(String[] args) {
		new JButton2();
	}

}
