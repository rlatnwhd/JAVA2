import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class JTFJTA1 extends JFrame implements ActionListener {
	private JTextField input;
	private JTextArea output;
	
	private JLabel jl = new JLabel("입력하세요.");
	
	public JTFJTA1() {
		input = new JTextField(20);
		output = new JTextArea(10, 20);
		
		output.setEnabled(false); // 수정 불가 설정
		
		Container ct = getContentPane();
		
		ct.setLayout(new FlowLayout());
		
		JPanel p1 = new JPanel();
		// 판넬에 input, output 추가
		// 컨테이너에 판넬과 레이블 추가
		
		p1.add(input);
		p1.add(output);
		ct.add(p1);
		ct.add(jl);
		
		// input에 리스너 등록
		input.addActionListener(this);
		
		setTitle("Event Test1");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (output.getLineCount() <= output.getRows()) {
			output.append(e.getActionCommand() + "\n");
			input.setText("");
		}
		else {
			jl.setText("입력이 종료되었습니다.");
			input.setEditable(false);
		}
	}
}

public class JTFJTATest1 {

	public static void main(String[] args) {
		new JTFJTA1();
	}

}
