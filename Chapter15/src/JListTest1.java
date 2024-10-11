import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class JList1 extends JFrame implements ListSelectionListener {
	// 과일 배열
	String fruitsList[] = {"apple", "banana", "cherry",  "grape", "pear", "persimmom"};
	
	// 과일 배열로 리스트 객체 생성
	private JList jl = new JList(fruitsList);
	
	// 이미지 아이콘 객체 배열 생성
	private ImageIcon[] fruitIcons = {
			new ImageIcon("images/apple.jpg"),
			new ImageIcon("images/banana.jpg"),
			new ImageIcon("images/cherry.jpg"),
			new ImageIcon("images/grape.jpg"),
			new ImageIcon("images/pear.jpg"),
			new ImageIcon("images/persimmom.jpg")
	};
	
	// 이미지 아이콘과 과일 이름 출력 라벨
	private JLabel[] jli = new JLabel[6]; // 과일 아이콘
	private JLabel jln = new JLabel(); // 과일 이름
	
	public JList1() {
		// 컨테이너 생성
		Container ct = getContentPane();
		
		// 컨테이너 배치 관리자 설정
		ct.setLayout(new BorderLayout());
		
		// 이미지 영역 판넬 생성
		JPanel jp1 = new JPanel();
		
		// 이미지 영역 판넬 배치 관리자 설정
		jp1.setLayout(new GridLayout(3, 2));
		
		// 그리드에 아이콘 붙이기
		for(int i = 0 ; i < 6; i++) {
			jp1.add(jli[i] = new JLabel());
		}
		
		// 과일 이름 출력 영역 판넬 생성
		JPanel jp2 = new JPanel();
		jp2.add(jln);
		
		ct.add(jp1, BorderLayout.CENTER);
		ct.add(jl, BorderLayout.WEST);
		ct.add(jp2, BorderLayout.EAST);
		
		jl.addListSelectionListener(this);
		
		// 컨테이너 설정
		setTitle("List Test1");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		for(int i = 0; i < 6; i++) {
			jli[i].setIcon(null);
		}
		
		// 리스트 내용 가져오기
		int[] indices = jl.getSelectedIndices();
		String s = "선택한 항목 : ";
		
		// 리스트 길이까지 반복
		for(int i = 0; i < indices.length; i++) {
			// 과일 아이콘 리스트의 이미지를 가져옴
			jli[i].setIcon(fruitIcons[indices[i]]);
			// 과일 이름을 가져옴
			s = s + fruitsList[indices[i]] + " ";
		}
		jln.setText(s); // 출력
	}
}

public class JListTest1 {

	public static void main(String[] args) {
		new JList1();
	}
}
