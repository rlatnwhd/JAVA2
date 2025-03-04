import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

// PC방 좌석 관리 시스템을 구현한 클래스
// 사용자가 좌석을 선택하고 예약할 수 있는 인터페이스 제공
class SeatManagement extends JFrame implements ActionListener {
    // GUI 컴포넌트
    private JLabel titleLabel = new JLabel("<< 원하시는 좌석을 선택하여 주십시오 >>", JLabel.CENTER); // 안내 메시지
    private JButton[] seatButtons = new JButton[64];  // 64개의 좌석 버튼 배열
    
    // 로그인한 사용자 정보
    private String loginUserName;  // 사용자 이름 (비회원의 경우 "비회원")
    private String loginUserID;    // 사용자 ID
    private String loginUserTime;  // 사용자의 남은 시간
    private JButton cancelButton;  // 취소 버튼

    // 생성자: 좌석 선택 화면 초기화
    // @param userName - 사용자 이름
    // @param userID - 사용자 ID
    public SeatManagement(String userName, String userID) {
        // 사용자 정보 초기화
        this.loginUserName = userName;
        this.loginUserID = userID;
        
        // 비회원인 경우 최근 구매한 쿠폰의 시간 정보 조회
        if (userName.equals("비회원")) {
            // DB에서 비회원의 최근 쿠폰 정보 조회
            try {
                String sql = "SELECT COUPON FROM salesstatus WHERE ID = ? ORDER BY TIME DESC LIMIT 1";
                PreparedStatement pstmt = Main.conn.prepareStatement(sql);
                pstmt.setString(1, loginUserID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String couponInfo = rs.getString("COUPON");
                    loginUserTime = couponInfo.substring(couponInfo.indexOf("(") + 1, couponInfo.indexOf(")"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // 회원인 경우 남은 시간 정보 조회
            try {
                String sql = "SELECT time FROM user WHERE id = ?";
                PreparedStatement pstmt = Main.conn.prepareStatement(sql);
                pstmt.setString(1, loginUserID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    loginUserTime = rs.getString("time");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // GUI 레이아웃 구성
        // BorderLayout 사용: NORTH(타이틀), CENTER(좌석), SOUTH(취소버튼)
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(0, 10));
        
        ct.add(titleLabel, BorderLayout.NORTH);
        
        JPanel seatPanel = new JPanel(new GridLayout(8, 8, 5, 5));
        for (int i = 0; i < 64; i++) {
            seatButtons[i] = new JButton(String.valueOf(i + 1));
            seatButtons[i].setBackground(new Color(77, 77, 77));
            seatButtons[i].setForeground(Color.WHITE);
            seatButtons[i].setFocusPainted(false);
            seatButtons[i].addActionListener(this);
            seatPanel.add(seatButtons[i]);
        }
        ct.add(seatPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        cancelButton = new JButton("취소");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);
        ct.add(buttonPanel, BorderLayout.SOUTH);
        
        setTitle("좌석 선택");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // 좌석 상태 초기 로드
        loadSeatStatus();
    }
    
    // 현재 좌석 사용 상태를 DB에서 조회하여 화면에 표시
    // - 빈 좌석: 회색
    // - 회원 사용중: 초록색
    // - 비회원 사용중: 빨간색
    private void loadSeatStatus() {
        try {
            // 먼저 모든 버튼을 기본 상태로 초기화
            for (int i = 0; i < seatButtons.length; i++) {
                seatButtons[i].setBackground(new Color(77, 77, 77));
                seatButtons[i].setForeground(Color.WHITE);
                seatButtons[i].setText(String.valueOf(i + 1));
                seatButtons[i].setEnabled(true);
            }

            String sql = "SELECT * FROM seatstatus";
            Statement stmt = Main.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int seatNum = rs.getInt("seat_number") - 1;
                String userName = rs.getString("user_name");
                String userId = rs.getString("user_id");
                String userTime = rs.getString("remaining_time");
                
                if (userName != null && !userName.isEmpty()) {
                    // 비회원은 숫자로만 된 ID를 가짐
                    if (userId.matches("\\d+")) {
                        seatButtons[seatNum].setBackground(new Color(240, 0, 0));
                    } else {
                        seatButtons[seatNum].setBackground(new Color(100, 200, 0));
                    }
                    seatButtons[seatNum].setText("<html><center>" + (seatNum + 1) + "<br>" + 
                                               userName + "<br>" + userTime + "</center></html>");
                    seatButtons[seatNum].setEnabled(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 좌석 선택 처리 메소드
    // @param seatNum - 선택된 좌석 번호
    // 트랜잭션을 사용하여 좌석 선택의 동시성 제어
    private void selectSeat(int seatNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = Main.conn;
            conn.setAutoCommit(false);
            
            // 먼저 해당 좌석이 사용 중인지 확인
            String checkSeatSql = "SELECT user_id FROM seatstatus WHERE seat_number = ? FOR UPDATE";
            pstmt = conn.prepareStatement(checkSeatSql);
            pstmt.setInt(1, seatNum + 1);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "이미 사용 중인 좌석입니다.");
                return;
            }
            
            // 사용자가 다른 좌석을 사용 중인지 확인
            String checkUserSql = "SELECT seat_number FROM seatstatus WHERE user_id = ?";
            pstmt = conn.prepareStatement(checkUserSql);
            pstmt.setString(1, loginUserID);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "이미 다른 좌석을 사용 중입니다.");
                return;
            }
            
            // 좌석 선택 처리
            String insertSql = "INSERT INTO seatstatus (seat_number, user_name, user_id, remaining_time) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, seatNum + 1);
            pstmt.setString(2, loginUserName);
            pstmt.setString(3, loginUserID);
            pstmt.setString(4, loginUserTime);
            pstmt.executeUpdate();
            
            conn.commit();
            dispose();
            
            // 비회원과 회원을 구분하여 적절한 GUI 실행
            if (loginUserName.equals("비회원")) {
                new Using_Nonmember_GUI(loginUserID, seatNum + 1, loginUserTime);
            } else {
                new Using_GUI(loginUserName, loginUserID, seatNum + 1);
            }
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "좌석 선택 중 오류가 발생했습니다.");
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // 버튼 클릭 이벤트 처리
    // - 좌석 버튼: 해당 좌석 선택 처리
    // - 취소 버튼: 이전 화면으로 돌아가기
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            dispose();
            if (loginUserName.equals("비회원")) {
                new User_GUI();
            } else {
                new Using_GUI(loginUserName, loginUserID, -1);
            }
            return;
        }
        
        // 좌석 버튼 처리
        for (int i = 0; i < seatButtons.length; i++) {
            if (e.getSource() == seatButtons[i]) {
                selectSeat(i);
                break;
            }
        }
    }
}
