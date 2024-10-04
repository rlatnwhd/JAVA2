import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class GUI2 extends JFrame implements ActionListener {
	// 속성으로 TextArea와 TextField를 선언
	// 이벤트 처리에서 사용해야 하기에 생성자 밖에 속성으로 선언
	// 글 내용을 입력하고, 버튼을 누르면 파일로 생성되어야 함
	private JTextField file_name;
	private JTextArea contents;
	
	public GUI2() {	
		file_name = new JTextField("파일 이름을 입력하세요.",20);
		JButton jb = new JButton("파일 불러오기");
		contents = new JTextArea(10, 20);
		
		contents.setEditable(false);
		
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
		setTitle("GUI Test2");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// 파일 이름으로 입력 객체 생성
			String s = file_name.getText();
			FileInputStream fis = new FileInputStream(s);
			DataInputStream dis = new DataInputStream(fis);
			
			contents.setText(dis.readUTF());
			fis.close();
			System.out.println(s + "파일을 읽었습니다.");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
public class GUITest2 {

	public static void main(String[] args) {
		new GUI2();
	}

}
