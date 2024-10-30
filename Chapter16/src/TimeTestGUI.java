import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class MyTime extends JFrame implements ActionListener {
	JLabel jl = new JLabel("당신의 생일을 공백으로 구분하여 입력(예:2020 01 01)");
	JTextField jtf;
	JTextArea jta1;
	JTextArea jta2;
	
	public MyTime() {
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		
		jtf = new JTextField(10);
		jta1 = new JTextArea(4, 20);
		jta2 = new JTextArea(3, 20);
		
		jta1.setEnabled(false);
		jta2.setEnabled(false);
		
		JButton jb1 = new JButton("현재");
		JButton jb2 = new JButton("100일");
		
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		jp1.add(jl);
		jp1.add(jtf);
		
		jp2.add(jb1);
		jp2.add(jta1);
		
		jp3.add(jb2);
		jp3.add(jta2);
		
		ct.add(jp1);
		ct.add(jp2);
		ct.add(jp3);
		
		setTitle("현재 나이 입력");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringTokenizer st = new StringTokenizer(jtf.getText());
		
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());
		
		LocalDate my_birth = LocalDate.of(year,month,day);
		
		LocalDate current = LocalDate.now();
		LocalDate hundred = my_birth.plusYears(100);
		
		if(e.getActionCommand() == "현재") {
			jta1.append("당신의 생일 : " + toString(my_birth) + "\n");
			jta1.append("오늘 날짜는 : " + toString(current) + "\n");
			jta1.append("생일부터 오늘까지의 일 : " + my_birth.until(current, ChronoUnit.DAYS) + "\n");
			jta1.append("생일부터 오늘까지의 년 : " + my_birth.until(current, ChronoUnit.YEARS));
		
		}
		else {
			jta2.append("100살이 되는 날 : " + toString(hundred) + "\n");
			jta2.append("생일부터 100살까지의 주 : " + my_birth.until(hundred, ChronoUnit.WEEKS) + "\n");
			jta2.append("생일부터 100살까지의 일 : " + my_birth.until(hundred, ChronoUnit.DAYS) + "\n");
		}
	}

	private String toString(LocalDate my_birth) {
		return my_birth.getYear() + "년 "
				+ my_birth.getMonthValue() + "월 "
				+ my_birth.getDayOfMonth() + "일 ";
	}
}

public class TimeTestGUI {

	public static void main(String[] args) {
		new MyTime();
	}

}
