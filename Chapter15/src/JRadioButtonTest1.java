import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class JRadioButton1 extends JFrame implements ActionListener {
	JLabel jl = new JLabel();
	JCheckBox[] jc = new JCheckBox[5];
	String[] hobby = {"게임", "운동", "독서", "산책", "요리"};
	JRadioButton[] jr = new JRadioButton[5];
	String[] age = {"10대", "20대", "30대", "40대", "50대"};
	
	public JRadioButton1() {
		// 컨테이너 생성
		Container ct = getContentPane();
		
		// 라벨 설정
		JLabel jl1 = new JLabel("당신의 취미는?");
		JLabel jl2 = new JLabel("당신의 나이는?");
		
		// 배치 관리자 설정.(그리드 레이라웃)
		ct.setLayout(new GridLayout(4, 1));
		
		JPanel jp1 = new JPanel(); // 취미 영역 판넬
		JPanel jp2 = new JPanel(); // 나이 영역 판넬
		JPanel jp3 = new JPanel(); // 결과 레이블 판넬
		
		// 취미 체크박스 버튼, 나이 라디오 버튼를 넣을 판넬 생성
		JPanel hobbyPanel = new JPanel();
		JPanel agePanel = new JPanel();
		
		// 나이를 나타내는 라디오버튼 그룹
		ButtonGroup bg = new ButtonGroup();
		
		// 취미와 나이를 나타내는 체크박스와 라디오 버튼 객체를 배열로 생성
		for(int i = 0; i < 5; i++) {
			jc[i] = new JCheckBox(hobby[i]);
			jr[i] = new JRadioButton(age[i]);
			
			hobbyPanel.add(jc[i]);
			agePanel.add(jr[i]);
			
			// 이벤트를 받아들일 각 컴포넌트에 리스너 등록
			jr[i].addActionListener(this);
			bg.add(jr[i]); // 버튼 그룹에 라디오버튼 객체 추가
		}
		
		jp1.add(jl1);
		jp1.add(hobbyPanel);
		jp2.add(jl2);
		jp2.add(agePanel);
		jp3.add(jl);
		
		ct.add(jp1);
		ct.add(jp2);
		ct.add(jp3);
		
		// 컨테이너 설정
		setTitle("Radio Button Test1");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = "당신의 취미는 : ";
		for(int i = 0; i < 5; i++) {
			if(jc[i].isSelected() == true) {
				s = s + hobby[i] + ", ";
			}
		}
		s += "당신의 나이는 : ";
		jl.setText(s + e.getActionCommand());
	}
	
}
public class JRadioButtonTest1 {

	public static void main(String[] args) {
		new JRadioButton1();
	}

}
