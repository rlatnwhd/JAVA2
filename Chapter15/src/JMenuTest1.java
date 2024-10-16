import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

class JMenu1 extends JFrame implements ActionListener {
	JTextField jtf;
	public JMenu1() {
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		JMenuBar jmb = new JMenuBar(); // JMenuBar() 객체 생성
		
		JMenu menu1 = new JMenu("File"); // JMenu() 객체 생성
		JMenu menu2 = new JMenu("Edit");
		
		JMenuItem jmi = new JMenuItem("New File"); // 서브 메뉴 JMenuItem() 객체 생성
		jmi.addActionListener(this); // 이벤트 리스너 등록
		menu1.add(jmi); // menu1 메뉴에 추가
		
		jmi = new JMenuItem("Save"); // 서브 메뉴 JMenuItem() 객체 생성
		jmi.addActionListener(this); // 이벤트 리스너 등록
		menu1.add(jmi); // menu1 메뉴에 추가
		
		jmb.add(menu1);
		
		jmi = new JMenuItem("Open"); // 서브 메뉴 JMenuItem() 객체 생성
		jmi.addActionListener(this); // 이벤트 리스너 등록
		menu2.add(jmi); // menu1 메뉴에 추가
		
		jmi = new JMenuItem("Cut"); // 서브 메뉴 JMenuItem() 객체 생성
		jmi.addActionListener(this); // 이벤트 리스너 등록
		menu2.add(jmi); // menu1 메뉴에 추가
		
		jmi = new JMenuItem("Copy"); // 서브 메뉴 JMenuItem() 객체 생성
		jmi.addActionListener(this); // 이벤트 리스너 등록
		menu2.add(jmi); // menu1 메뉴에 추가
		
		JCheckBoxMenuItem jcbmi = new JCheckBoxMenuItem("Graduated Ruler"); 
		jcbmi.addActionListener(this); // 이벤트 리스너 등록
		menu2.add(jcbmi); // menu1 메뉴에 추가
		
		JCheckBoxMenuItem jcbmb = new JCheckBoxMenuItem("Modifiable Status"); 
		jcbmb.addActionListener(this); // 이벤트 리스너 등록
		menu2.add(jcbmb); // menu1 메뉴에 추가
		
		jmb.add(menu2);
		
		setJMenuBar(jmb);
		
		jtf = new JTextField();
		ct.add(jtf, BorderLayout.SOUTH);
		jtf.setEnabled(false);
		
	    setTitle("JMenu Test1");// 컨테이너 기본 설정
	    setSize(300, 300);
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jtf.setText(e.getActionCommand());
	}
}

public class JMenuTest1 {

	public static void main(String[] args) {
		new JMenu1();
	}

}
