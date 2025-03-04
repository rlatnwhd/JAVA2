import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
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

// PC방 회원 로그인 화면을 구현한 클래스
// 회원 인증을 통해 PC 사용 서비스에 접근할 수 있는 인터페이스 제공
class SignIn extends JFrame implements ActionListener {
    // GUI 컴포넌트
    private JTextField id_area = new JTextField(20);   // ID 입력 필드
    private JTextField pw_area = new JTextField(20);   // 비밀번호 입력 필드
    private JLabel id_lb, pw_lb;                      // ID, PW 라벨
    private JLabel getoLabel = new JLabel(new ImageIcon("image/geto_image.jpeg")); // 로고 이미지

    // 생성자: 로그인 화면 GUI 초기화
    // BorderLayout을 사용하여 3개의 주요 영역으로 구성
    // - NORTH: 로고 이미지
    // - CENTER: ID/PW 입력 필드
    // - SOUTH: 로그인/취소 버튼
    public SignIn() {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10)); // 여백 10픽셀

        // 상단 이미지 패널: 로고 이미지 표시
        JPanel imagePanel = new JPanel();
        imagePanel.add(getoLabel);
        imagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ct.add(imagePanel, BorderLayout.NORTH);

        // 메인 입력 패널: ID와 비밀번호 입력 필드
        // GridLayout으로 ID/PW 입력 영역을 균등하게 배치
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // ID 입력 패널
        JPanel idPanel = new JPanel(new BorderLayout(10, 10));
        id_lb = new JLabel("아이디     : ");
        idPanel.add(id_lb, BorderLayout.WEST);
        idPanel.add(id_area, BorderLayout.CENTER);
        inputPanel.add(idPanel);

        // 비밀번호 입력 패널
        JPanel pwPanel = new JPanel(new BorderLayout(10, 10));
        pw_lb = new JLabel("비밀번호 : ");
        pwPanel.add(pw_lb, BorderLayout.WEST);
        pwPanel.add(pw_area, BorderLayout.CENTER);
        inputPanel.add(pwPanel);

        ct.add(inputPanel, BorderLayout.CENTER);

        // 버튼 패널: 로그인/취소 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton cancel = new JButton("취소");
        JButton enter = new JButton("로그인");
        
        cancel.addActionListener(this);
        enter.addActionListener(this);

        buttonPanel.add(cancel);
        buttonPanel.add(enter);
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 창 설정
        setTitle("회원 로그인");
        setSize(400, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // 버튼 클릭 이벤트 처리 메소드
    // 1. 로그인 버튼:
    //    - DB에서 사용자 정보 확인
    //    - 로그인 성공 시:
    //      * 남은 시간이 없으면 쿠폰 구매 화면으로 이동
    //      * 남은 시간이 있으면 좌석 관리 화면으로 이동
    //    - 로그인 실패 시 에러 메시지 표시
    // 2. 취소 버튼:
    //    - 사용자 선택 화면(User_GUI)으로 돌아가기
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        
        ResultSet result = null;
        Statement stmt = null;
        
        try {
            // DB 연결 재확인
            Main.reconnectDB();
            
            stmt = Main.conn.createStatement();
            result = stmt.executeQuery("select * from user where ID='" + id_area.getText() + "'");
            
            if(s.equals("로그인")) {
                if (result.next()) { // 결과가 있는 경우
                    String id = result.getString("ID");
                    String pw = result.getString("PW");
                    String time = result.getString("TIME");
                    
                    // 입력값과 DB 값 비교
                    if (id.equals(id_area.getText()) && pw.equals(pw_area.getText())) {
                        // 시간이 00:00인지 확인
                        if (time.equals("00:00")) {
                            JOptionPane.showMessageDialog(null, "남은 시간이 없습니다. 쿠폰을 구매해주세요.");
                            new Coupon(result.getString("NAME"), id);
                            id_area.setText("");
                            pw_area.setText("");
                            return;
                        }
                        else {
                        	JOptionPane.showMessageDialog(null, "로그인 성공!");
	                        dispose();
	                        new SeatManagement(result.getString("NAME"), id);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 옳지 않습니다!");
                        id_area.setText("");
                        pw_area.setText("");
                    }
                } else { // 결과가 없는 경우 (일치하는 아이디가 없음)
                    JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다!");
                    id_area.setText("");
                    pw_area.setText("");
                }
            } else if(s.equals("취소")) {
                dispose();
                new User_GUI();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다.");
        }
    }
}
