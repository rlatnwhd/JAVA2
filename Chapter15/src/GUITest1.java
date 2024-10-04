import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class GUI1 extends JFrame implements ActionListener {
	// 속성으로 TextArea와 TextField를 선언
	// 이벤트 처리에서 사용해야 하기에 생성자 밖에 속성으로 선언
	// 글 내용을 입력하고, 버튼을 누르면 파일로 생성되어야 함
	private JTextField file_name;
	private JTextArea contents;
	
	public GUI1() {	
		file_name = new JTextField("파일 이름을 입력하세요.",20);
		JButton jb = new JButton("파일 저장하기");
		contents = new JTextArea("파일 내용을 입력하세요.",10, 20);
		
		jb.addActionListener(this);
		
		// 컨테이너 생성
		Container ct = getContentPane();
		
		// 배치 관리자 생성(Grid)
		ct.setLayout(new FlowLayout());
		
		// 컨테이너 버튼 추가
		ct.add(file_name);
		ct.add(jb);
		ct.add(contents);

		
		// 컨테이너 설정
		setTitle("GUI Test1");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// 출력
			// 파일 이름으로 출력 객체를 생성
			String s = file_name.getText();
			FileOutputStream fos = new FileOutputStream(s);
			DataOutputStream dos = new DataOutputStream(fos);
			
			// TextArea의 내용을 파일로 출력
			dos.writeUTF(contents.getText());
			fos.close();
			System.out.println(s + "파일을 생성하였습니다.");
		} catch (FileNotFoundException e1) {
			
		} catch (IOException e1) {
			
		} 
	}
}
public class GUITest1 {

	public static void main(String[] args) {
		new GUI1();
	}

}
