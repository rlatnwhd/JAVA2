import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// PC방 관리자 로그인 화면을 구현한 클래스
// 관리자 인증을 통해 관리자 메뉴에 접근할 수 있는 인터페이스 제공
class Manager_GUI extends JFrame implements ActionListener {
    private JLabel getoLabel = new JLabel(new ImageIcon("image/geto_image.jpeg"));
    private JLabel jl = new JLabel("<< 관리자 ID와 비밀번호를 입력해주세요 >>");
    private JLabel id = new JLabel("ID   : ");
    private JLabel pw = new JLabel("PW : ");
    private JTextField id_area = new JTextField(20);
    private JTextField pw_area = new JTextField(20);

    public Manager_GUI() {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10)); // 패널 간 간격 조정 (좌우 10px)
        
        // 이미지 패널
        JPanel imagePanel = new JPanel();
        imagePanel.add(getoLabel);
        imagePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 위, 좌, 아래, 우 여백 추가
        ct.add(imagePanel, BorderLayout.NORTH);

        // 설명 레이블과 입력 필드 패널을 감싸는 메인 패널
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2행 1열로 배치
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 설명 레이블 패널
        JPanel labelPanel = new JPanel();
        labelPanel.add(jl);
        mainPanel.add(labelPanel);

        // 입력 필드 패널 (ID와 PW를 한 줄씩 배치)
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2행 1열로 배치

        // ID 입력 패널
        JPanel idPanel = new JPanel(new BorderLayout(5, 5));
        idPanel.add(id, BorderLayout.WEST);
        idPanel.add(id_area, BorderLayout.CENTER);
        inputPanel.add(idPanel);

        // PW 입력 패널
        JPanel pwPanel = new JPanel(new BorderLayout(5, 5));
        pwPanel.add(pw, BorderLayout.WEST);
        pwPanel.add(pw_area, BorderLayout.CENTER);
        inputPanel.add(pwPanel);
        
        mainPanel.add(inputPanel);

        // 메인 패널을 CENTER에 추가
        ct.add(mainPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton cancel = new JButton("취소");
        JButton enter = new JButton("확인");
        
        cancel.addActionListener(this);
        enter.addActionListener(this);
        
        buttonPanel.add(cancel);
        buttonPanel.add(enter);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 창 설정
        setTitle("관리자 로그인");
        setSize(400, 450); // 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		
        ResultSet result = null;
        Statement stmt = null;
        
        try {
			stmt = Main.conn.createStatement();
			result = stmt.executeQuery("select * from user where ID='사장님'");
			
			if(s.equals("확인")) {
				 if (result.next()) { // 가져온 결과 비교
	                String id = result.getString("ID");
	                String pw = result.getString("PW");

	                // 입력값과 DB 값 비교
	                if (id.equals(id_area.getText()) && pw.equals(pw_area.getText())) {
	                	JOptionPane.showMessageDialog(null, "로그인 성공!");
	                    dispose();
	                    new Manager_Menu_GUI();
	                }
	                else {
	                    JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 옳지 않습니다!");
	                    id_area.setText("");
	                    pw_area.setText("");
	                }
	            }
			}
			else {
				dispose();
				new PC_Privilege();
			}
		}
        catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		
	}
}
