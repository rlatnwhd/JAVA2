import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

class JButton1 extends JFrame {
	public JButton1() {
		ImageIcon korea = new ImageIcon("images\\korea1.gif");
		ImageIcon usa = new ImageIcon("images\\usa.gif");
		ImageIcon germany = new ImageIcon("images\\germany.gif");
		
		// 버튼 객체 생성
		JButton nat = new JButton(korea); // 기본 버튼 이미지
		nat.setPressedIcon(usa); // 버튼 누르면 미국 국기
		nat.setRolloverIcon(germany); // 버튼에 마우스 올리면 독일 국기
		
		// 컨테이너 생성
		Container ct = getContentPane();
		
		// 배치 관리자 생성(Flow)
		ct.setLayout(new FlowLayout());
		
		// 컨테이너 버튼 추가
		ct.add(nat);
		
		// 컨테이너 설정
		setTitle("Image Button Test1");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
public class JButtonTest1 {

	public static void main(String[] args) {
		new JButton1();

	}

}
