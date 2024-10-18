import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class MBTI extends JFrame {
	JLabel jl = new JLabel("당신의 mbti는 무엇인가요?");
	JTextArea jta = new JTextArea("MBTI를 선택하면 유형별 별명과 특징을 알려드립니다.", 4, 20);
	
	public MBTI() {
		Container ct = getContentPane(); // 컨테이너 생성
		
		ct.setLayout(new BorderLayout()); // 컨테이너의 배치 관리자 설정
		
		JPanel jp1 = new JPanel(); // 판넬1 생성
		
		jp1.setLayout(new GridLayout(2, 1, 10, 10)); // 판넬1의 배치 관리자 설정
		
		jta.setEnabled(false); // 텍스트 에리어 수정 불가
		
		jp1.add(jl); // 판넬 1에 라벨 및 텍스트 에이러 삽입
		jp1.add(jta);
		
		JPanel jp2 = new JPanel(); // 판넬2 생성
		
		jp2.setLayout(new GridLayout(4, 4, 10, 10)); // 판넬2의 배치 관리자 설정
		
		jp2.add(new JButton("ISTJ")); // 버튼 생성 및 판넬2에 삽입
		jp2.add(new JButton("ISFJ"));
		jp2.add(new JButton("INFJ"));
		jp2.add(new JButton("INTJ"));
		jp2.add(new JButton("ISTP"));
		jp2.add(new JButton("ISFP"));
		jp2.add(new JButton("INFP"));
		jp2.add(new JButton("INTP"));
		jp2.add(new JButton("ESTP"));
		jp2.add(new JButton("ESFP"));
		jp2.add(new JButton("ENFP"));
		jp2.add(new JButton("ENTP"));
		jp2.add(new JButton("EXTJ"));
		jp2.add(new JButton("ESFJ"));
		jp2.add(new JButton("ENFJ"));
		jp2.add(new JButton("ENTJ"));	
		
		ct.add(jp1, BorderLayout.NORTH); // BorderLayout으로 각 판넬의 위치 설정
		ct.add(jp2, BorderLayout.SOUTH);
		
		// 컨테이너 설정
		setTitle("MBTI Project");
		setSize(400, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

public class Exam_Test_1 {

	public static void main(String[] args) {
		new MBTI();
	}

}
