import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// PC방 회원가입 시스템을 구현한 클래스
// BorderLayout을 기본으로 사용하여 화면을 구성하고, 각 컴포넌트를 패널로 분리하여 유지보수성 향상
class SignUp extends JFrame implements ActionListener {
    // 사용자 입력을 받는 텍스트 필드들
    // 길이를 20으로 통일하여 UI의 일관성 유지
    private JTextField name_area = new JTextField(20);    // 이름 입력
    private JTextField id_area = new JTextField(20);      // 아이디 입력
    private JTextField pw_area = new JTextField(20);      // 비밀번호 입력
    private JTextField number_area = new JTextField(20);  // 전화번호 입력
    private JTextField email_area = new JTextField(20);   // 이메일 입력

    // 각 입력 필드를 설명하는 라벨들
    private JLabel head_lb, name_lb, id_lb, pw_lb, number_lb, email_lb, gender_lb;
    
    // 성별 선택을 위한 라디오 버튼 배열
    // ButtonGroup으로 그룹화하여 하나만 선택되도록 구현
    private JRadioButton[] gender_area = new JRadioButton[2];
    private String[] gender = { "남성", "여성" };
    
    // ID 중복 체크 관련 변수들
    // 중복 확인 없이 가입을 방지하기 위한 안전장치
    private boolean overlapCheck = false;    // 중복 확인 완료 여부
    private String nonOverlapId;             // 중복 확인된 아이디 저장

    // 생성자: 회원가입 폼 초기화
    // GridLayout(8,1)을 사용하여 각 입력 필드를 수직으로 정렬
    // 각 컴포넌트에 여백을 추가하여 시각적 구분을 개선
    public SignUp() {
        // 메인 컨테이너 설정
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10)); // 전체 여백 10픽셀로 설정하여 답답해 보이지 않도록 함

        // 메인 패널 설정 - GridLayout 사용으로 균일한 간격 배치
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // 패널 외곽 여백
        ct.add(mainPanel, BorderLayout.CENTER);

        // 헤더 라벨 - 사용자에게 입력 안내
        head_lb = new JLabel("<< 회원 가입을 위해 회원 정보를 입력하여 주십시오. >>", JLabel.CENTER);
        mainPanel.add(head_lb);

        // 이름 입력 패널 - BorderLayout으로 라벨과 입력필드 정렬
        JPanel namePanel = new JPanel(new BorderLayout(5, 5));
        name_lb = new JLabel("이름    ");
        namePanel.add(name_lb, BorderLayout.WEST);      // 라벨을 왼쪽에 배치
        namePanel.add(name_area, BorderLayout.CENTER);  // 입력필드를 중앙에 배치
        mainPanel.add(namePanel);

        // 성별 선택 패널 - FlowLayout으로 라디오버튼 수평 배치
        // 중앙 정렬로 시각적 균형을 맞춤
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        gender_lb = new JLabel("성별    ");
        genderPanel.add(gender_lb, FlowLayout.LEFT);  // 라벨 왼쪽 정렬
        ButtonGroup bg = new ButtonGroup();
        for (int i = 0; i < gender.length; i++) {
            gender_area[i] = new JRadioButton(gender[i]);
            gender_area[i].addActionListener(this);
            genderPanel.add(gender_area[i]);
            bg.add(gender_area[i]);
        }
        mainPanel.add(genderPanel);

        // 아이디 입력 패널 - 중복확인 버튼 추가
        // BorderLayout.EAST에 버튼을 배치하여 입력필드와 함께 표시
        JPanel idPanel = new JPanel(new BorderLayout(5, 5));
        id_lb = new JLabel("아이디   ");
        JButton overlap = new JButton("중복확인");
        overlap.addActionListener(this);
        idPanel.add(id_lb, BorderLayout.WEST);
        idPanel.add(id_area, BorderLayout.CENTER);
        idPanel.add(overlap, BorderLayout.EAST);
        mainPanel.add(idPanel);

        // 비밀번호 패널
        JPanel pwPanel = new JPanel(new BorderLayout(5, 5));
        pw_lb = new JLabel("비밀번호");
        pwPanel.add(pw_lb, BorderLayout.WEST);
        pwPanel.add(pw_area, BorderLayout.CENTER);
        mainPanel.add(pwPanel);

        // 전화번호 패널
        JPanel numberPanel = new JPanel(new BorderLayout(5, 5));
        number_lb = new JLabel("전화번호");
        numberPanel.add(number_lb, BorderLayout.WEST);
        numberPanel.add(number_area, BorderLayout.CENTER);
        mainPanel.add(numberPanel);

        // 이메일 패널
        JPanel emailPanel = new JPanel(new BorderLayout(5, 5));
        email_lb = new JLabel("이메일   ");
        emailPanel.add(email_lb, BorderLayout.WEST);
        emailPanel.add(email_area, BorderLayout.CENTER);
        mainPanel.add(emailPanel);

        // 버튼 패널 - FlowLayout으로 버튼들을 중앙에 배치
        // 취소와 가입 버튼 사이에 충분한 간격을 두어 실수 클릭 방지
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton cancel = new JButton("취소");
        JButton enter = new JButton("가입");
        
        cancel.addActionListener(this);
        enter.addActionListener(this);
        
        buttonPanel.add(cancel);
        buttonPanel.add(enter);
        mainPanel.add(buttonPanel);

        // 창 설정
        setTitle("회원 가입");
        setSize(450, 550); // 창 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // 이벤트 처리 메소드
    // 세 가지 주요 기능 구현: ID 중복확인, 회원가입 처리, 취소
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        try {
        	Main.reconnectDB();
        	
        	Statement stmt = Main.conn.createStatement();
        			 
            if (s.equals("중복확인")) {
                checkIdOverlap(stmt);
            } else if (s.equals("가입")) {
                processSignUp(stmt);
            } else if (s.equals("취소")) {
                dispose();
                new User_GUI();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다!");
        }
    }

    // ID 중복 검사 메소드
    // 1. 입력값 null/빈 문자열 체크로 기본 유효성 검사
    // 2. DB 조회로 중복 여부 확인
    // 3. '사장님' 아이디는 특별히 사용 제한
    // 4. 결과를 사용자에게 즉시 피드백
    private void checkIdOverlap(Statement stmt) throws SQLException {
        String userId = id_area.getText();
        if (userId.equals(null) || userId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "아이디를 입력하세요!");
            return;
        }

        ResultSet result = stmt.executeQuery("SELECT ID FROM user WHERE ID = '" + userId + "'");
        if (result.next() || "사장님".equals(userId)) {
            JOptionPane.showMessageDialog(null, "해당 아이디로는 가입이 불가능합니다!");
            overlapCheck = false;
        } else {
            JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다!");
            overlapCheck = true;
            nonOverlapId = userId;
        }
    }

    // 회원가입 처리 메소드
    // 1. 모든 필드의 입력 여부 검사
    // 2. ID 중복확인 여부 재검증
    // 3. DB에 회원 정보 등록 (초기 시간은 30분으로 설정)
    // 4. 성공/실패 여부를 사용자에게 알림
    private void processSignUp(Statement stmt) throws SQLException {
        // 필드 값 가져오기
        String name = name_area.getText();
        String id = id_area.getText();
        String pw = pw_area.getText();
        String number = number_area.getText();
        String email = email_area.getText();

        // 공백 또는 선택 여부 확인
        boolean genderSelected = gender_area[0].isSelected() || gender_area[1].isSelected();
        
        if (name == null || name.isEmpty() || id == null || id.isEmpty() || pw == null || pw.isEmpty() || number == null || number.isEmpty() || email == null || email.isEmpty() || !genderSelected) {
            JOptionPane.showMessageDialog(null, "모든 정보를 입력하여 주세요!");
            return;
        }

        // 아이디 중복 확인 여부
        if (!overlapCheck || !id.equals(nonOverlapId)) {
            JOptionPane.showMessageDialog(null, "아이디 중복확인을 해주세요!");
            return;
        }

        // 성별 확인
        String gender = gender_area[0].isSelected() ? "남성" : "여성";

        // 회원가입 처리
        String query = String.format("INSERT INTO user (NAME, ID, PW, EMAIL, TIME, SIGN_IN, GENDER, NUMBER) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", name, id, pw, email, "00:30",Main.date, gender, number);
        stmt.executeUpdate(query);
        JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다!");
        dispose();
        new User_GUI();
    }
}
