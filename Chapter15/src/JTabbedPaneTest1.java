import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

class RadioPanel extends JPanel implements ActionListener {
	ButtonGroup bg = new ButtonGroup();
	JLabel jl1 = new JLabel(" | 당싱의 성별은? | ");
	JLabel jl2 = new JLabel("");
	
	JRadioButton[] jr = new JRadioButton[5];
	String[] gender = {"남자", "여자"};
	
	public RadioPanel() {
		for(int i = 0; i < 2; i++) {
			jr[i] = new JRadioButton(gender[i]);
			add(jr[i]);
			jr[i].addActionListener(this);
			bg.add(jr[i]);
		}
		add(jl1);
		add(jl2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		jl2.setText(s);
	}
}

class ComboPanel extends JPanel implements ItemListener {
	JLabel jl1 = new JLabel(" | 당싱의 혈액형은? | ");
	JLabel jl2 = new JLabel("");
	
	public ComboPanel() {
		JComboBox<String> jcb = new JComboBox<String>();
		jcb.addItem("A형");
		jcb.addItem("B형");
		jcb.addItem("O형");
		jcb.addItem("AB형");
		
		jcb.addItemListener(this);
		
		add(jl1);
		add(jl2);
		add(jcb);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		String s = (String)e.getItem();
		jl2.setText(s);
		
	}
}

class JTabbedPane1 extends JFrame {
	
	public JTabbedPane1(){	
		Container ct = getContentPane(); // 컨테이너 생성
		JTabbedPane jtp = new JTabbedPane(); // JTabbedPane() 객체 생성
		RadioPanel rp = new RadioPanel(); // RadioPanel() 객체 생성
		ComboPanel cp = new ComboPanel(); // ComboPanel() 객체 생성
		
		jtp.addTab("성별", rp); // JTabbedPane()에 RadioPanel() 추가
		jtp.addTab("혈액형", cp); // JTabbedPane()에 ComboPanel() 추가
		
		ct.add(jtp);
		
	    setTitle("JTabbedPane Test1");// 컨테이너 기본 설정
	    setSize(300, 300);
	    setVisible(true);
	}
}

public class JTabbedPaneTest1 {
	public static void main(String[] args) {
		new JTabbedPane1();
	}
}
