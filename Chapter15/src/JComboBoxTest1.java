import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

class JComboBox1 extends JFrame implements ItemListener {
	JLabel fruitLabel = new JLabel();
	public JComboBox1() {
		Container ct = getContentPane();
		
		ct.setLayout(new FlowLayout());
		
		// 콤보박스 객체 생성
		JComboBox<String> fruitCombo = new JComboBox<String>();
		
		// 콤보박스에 들어갈 내용이면서 이미지 파일명을 배열로 선언
		String fruitsList[] = {"apple", "banana", "cherry", "pear", "persimmom", "grape"};
		
		for(int i = 0; i < fruitsList.length; i++) {
			fruitCombo.addItem(fruitsList[i]);
		}
		
		fruitCombo.addItemListener(this);
		
		ct.add(fruitCombo);
		ct.add(fruitLabel);
		
		// 컨테이너 설정
		setTitle("ConboBox Test1");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		String fruit = (String)e.getItem();
		
		fruitLabel.setIcon(new ImageIcon("images/" + fruit + ".jpg"));
		
	}
	
}

public class JComboBoxTest1 {

	public static void main(String[] args) {
		new JComboBox1();

	}

}
