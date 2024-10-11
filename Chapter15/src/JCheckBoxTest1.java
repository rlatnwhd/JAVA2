import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

class JCheckBox1 extends JFrame implements ItemListener {
	private JTextField languageTF;
	
	public JCheckBox1() {
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		
		languageTF = new JTextField(10);
		
		// 체크 박스 객체 생성
		JCheckBox language1 = new JCheckBox("Java");
		JCheckBox language2 = new JCheckBox("C");
		JCheckBox language3 = new JCheckBox("Python");
		JCheckBox language4 = new JCheckBox("JavaScript");
		
		ct.add(language1);
		ct.add(language2);
		ct.add(language3);
		ct.add(language4);
		ct.add(languageTF);
		
		language1.addItemListener(this);
		language2.addItemListener(this);
		language3.addItemListener(this);
		language4.addItemListener(this);
		
		// 컨테이너 설정
		setTitle("CheckBox Test1");
		setSize(300, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		languageTF.setText(((JCheckBox)e.getItem()).getText());
		// 체크할 때, 해제할 때 이벤트 발생
	}	
}

public class JCheckBoxTest1 {

	public static void main(String[] args) {
		new JCheckBox1();

	}

}
