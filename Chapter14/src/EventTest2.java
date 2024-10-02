import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Event2 extends JFrame {
	JLabel jl = new JLabel("버튼을 눌러주세요!");
	
	public Event2() {
		Container ct = getContentPane();
		
		ct.setLayout(new FlowLayout());
		
		JButton jb1 = new JButton("사랑합니다");
		JButton jb2 = new JButton("행복합니다");
		
		ct.add(jb1);
		ct.add(jb2);
		ct.add(jl);
		
		jb1.addActionListener(new EventProcess());
		jb2.addActionListener(new EventProcess());
		
		setTitle("Event Test2");
		
		setSize(300, 200);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
	
	// 내부 클래스로 이벤트 처리
	// 1) 처리할 이벤트 종류 결정
	// 2) 이벤트에 적합한 이벤트 리스너 인터페이스를 사용
	// 3) 이벤트를 사용할 버튼에 리스너 등록 (25번 줄)
	// 4) 리스너 인터페이스에 선언된 메소드를 오버라이딩하여 이벤트 처리 루틴 작성 (44번 줄)
	private class EventProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			jl.setText(e.getActionCommand()); // 버튼의 레이블을 문자열로 변환시켜 출력
			
		}
		
	}
}

public class EventTest2 {

	public static void main(String[] args) {
		new Event2();
	}

}
