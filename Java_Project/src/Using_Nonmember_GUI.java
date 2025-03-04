import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

// PC방 비회원 전용 실시간 이용 관리 시스템
// 회원 시스템(Using_GUI)의 기능을 비회원용으로 최적화하고 보안을 강화한 특수 목적 클래스
public class Using_Nonmember_GUI extends JFrame implements ActionListener {
    // === 비회원 세션 관리 변수 ===
    private String loginUserID;    // 시스템 발급 임시 ID (1000-9999 범위의 난수)
    private int seatNumber;        // 현재 사용 중인 좌석 번호
    private JLabel timeLabel;      // 실시간 잔여 시간 표시용 라벨
    private JLabel infoLabel;      // 비회원 정보 및 좌석 표시용 라벨
    private Timer timer;           // 시간 감소 및 DB 동기화용 타이머 (1분 주기)
    private int remainingMinutes;  // 실시간 잔여 시간 (분 단위)
    public static Using_Nonmember_GUI instance;  // 전역 접근용 싱글톤 인스턴스
    private Timer couponCheckTimer; // 부정 사용 방지용 보안 타이머 (5초 주기)

    public Using_Nonmember_GUI(String userID, int seatNum, String initialTime) {
        instance = this;
        this.loginUserID = userID;
        this.seatNumber = seatNum;

        // 초기 시간 문자열을 분 단위로 변환하여 저장
        String[] timeParts = initialTime.split(":");
        remainingMinutes = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);

        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10)); // 여백 10픽셀로 전체 레이아웃 설정

        // === 정보 표시 패널 구성 (GridLayout 3행 1열) ===
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        infoLabel = new JLabel("사용자: 비회원("+ userID + ") | 좌석번호: " + seatNumber + "번");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel = new JLabel("남은 시간: " + initialTime);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        
        infoPanel.add(new JLabel("PC 사용 현황", JLabel.CENTER));
        infoPanel.add(infoLabel);
        infoPanel.add(timeLabel);
        ct.add(infoPanel, BorderLayout.CENTER);

        // === 버튼 패널 구성 (GridLayout 1행 2열) ===
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton moveButton = new JButton("자리 이동");
        JButton endButton = new JButton("사용 종료");
        
        moveButton.addActionListener(this);
        endButton.addActionListener(this);
        
        buttonPanel.add(moveButton);
        buttonPanel.add(endButton);
        
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // === 창 설정 ===
        setTitle("비회원 PC 이용");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // 창 닫기 방지로 보안 강화
        setResizable(false);  // 창 크기 고정으로 UI 일관성 유지
        setVisible(true);

        // === 시스템 타이머 시작 ===
        startTimer();           // 시간 관리 시작
        startCouponCheckTimer(); // 보안 검사 시작
    }

    // === 시간 관리 타이머 - 1분마다 실행 ===
    // 1. 잔여 시간 감소
    // 2. UI 시간 표시 업데이트
    // 3. DB 시간 정보 갱신
    // 4. 시간 만료 시 자동 종료
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingMinutes > 0) {
                    remainingMinutes--;
                    updateTimeLabel();
                    updateDatabase();
                    
                    if (remainingMinutes == 0) {
                        timer.cancel();
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(Using_Nonmember_GUI.this, "시간이 만료되었습니다.");
                            endUse();
                        });
                    }
                }
            }
        }, 60000, 60000); // 1분 간격으로 실행
    }

    // === UI 시간 표시 업데이트 메소드 ===
    private void updateTimeLabel() {
        int hours = remainingMinutes / 60;
        int minutes = remainingMinutes % 60;
        SwingUtilities.invokeLater(() -> {
            timeLabel.setText(String.format("남은 시간: %02d:%02d", hours, minutes));
        });
    }

    // === DB 시간 정보 갱신 메소드 ===
    private void updateDatabase() {
        try {
            int hours = remainingMinutes / 60;
            int minutes = remainingMinutes % 60;
            String timeStr = String.format("%02d:%02d", hours, minutes);
            
            String sql = "UPDATE seatstatus SET remaining_time = ? WHERE user_id = ?";
            PreparedStatement pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, timeStr);
            pstmt.setString(2, loginUserID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === 보안 검사 타이머 - 5초마다 실행 ===
    private void startCouponCheckTimer() {
        couponCheckTimer = new Timer();
        couponCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkCouponExists();
            }
        }, 0, 5000); // 5초 간격으로 실행
    }

    // === 쿠폰 유효성 검사 메소드 ===
    private void checkCouponExists() {
        try {
            String sql = "SELECT COUNT(*) FROM salesstatus WHERE ID = ? AND TIME = ?";
            PreparedStatement pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, loginUserID);
            pstmt.setString(2, Main.date);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                // 쿠폰이 삭제된 경우
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, 
                        "쿠폰이 삭제되어 PC 사용이 종료됩니다.", 
                        "사용 종료", 
                        JOptionPane.WARNING_MESSAGE);
                    endUse();
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === 시스템 종료 처리 메소드 ===
    private void endUse() {
        try {
            String sql = "DELETE FROM seatstatus WHERE user_id = ?";
            PreparedStatement pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, loginUserID);
            pstmt.executeUpdate();

            // 모든 타이머 종료
            if (timer != null) {
                timer.cancel();
            }
            if (couponCheckTimer != null) {
                couponCheckTimer.cancel();
            }

            dispose();
            new PC_Privilege();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === 이벤트 처리 메소드 ===
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        switch (cmd) {
            case "자리 이동":
                try {
                    // 현재 자리 정보 삭제
                    String sql = "DELETE FROM seatstatus WHERE user_id = ?";
                    PreparedStatement pstmt = Main.conn.prepareStatement(sql);
                    pstmt.setString(1, loginUserID);
                    pstmt.executeUpdate();
                    
                    // 모든 타이머 종료
                    if (timer != null) {
                        timer.cancel();
                    }
                    if (couponCheckTimer != null) {
                        couponCheckTimer.cancel();
                    }
                    
                    dispose();
                    new SeatManagement("비회원", loginUserID);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
                
            case "사용 종료":
                int confirm = JOptionPane.showConfirmDialog(this,
                    "남은시간은 저장되지 않습니다.\n정말 사용을 종료하시겠습니까?",
                    "사용 종료 확인",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm == JOptionPane.YES_OPTION) {
                    endUse();
                }
                break;
        }
    }
}
