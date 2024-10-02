import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class JLabel1 extends JFrame implements ActionListener {
	private JLabel result = new JLabel();
	public ImageIcon img1, img2;
	
	public JLabel1() {
		// 컨테이너 생성
		Container ct = getContentPane();
		
		// 배치 관리자 설정
		ct.setLayout(new FlowLayout());
		
		// 버튼 생성
		JButton apple = new JButton("사과");
		JButton pear = new JButton("배");
		
		// 이미지 생성
		img1 = new ImageIcon("images\\apple.jpg");
		img2 = new ImageIcon("images\\pear.jpg");
		
		// 컨테이너에 버튼과 레이블 추가
		ct.add(apple);
		ct.add(pear);
		ct.add(result);
		
		// 버튼에 리스너 등록
		apple.addActionListener(this);
		pear.addActionListener(this);
		
		// 컨테이너 설정
		setTitle("Image Test1");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "사과") {
			result.setIcon(img1);
			result.setText("사과입니다");
		}
		if (e.getActionCommand() == "배") {
			result.setIcon(img2);
			result.setText("배입니다");
		}
		
	}
}

public class JLabelTest1 {

	public static void main(String[] args) {
		new JLabel1();
	}

}
