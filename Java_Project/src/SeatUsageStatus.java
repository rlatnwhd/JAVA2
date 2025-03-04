import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SeatUsageStatus extends JFrame implements ActionListener {
    // PC방 좌석 사용 현황을 실시간으로 모니터링하는 관리자용 클래스
    private JLabel titleLabel = new JLabel("<< 현재 좌석 사용 현황 >>", JLabel.CENTER); // 화면 타이틀
    private JButton[] seatButtons = new JButton[64];  // 64개의 좌석 상태 표시 버튼
    private JButton refreshButton;     // 수동 새로고침 버튼
    private JButton cancelButton;      // 돌아가기 버튼
    private Timer autoRefreshTimer;    // 자동 새로고침 타이머 (1초 간격)

    public SeatUsageStatus() {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10)); // 여백 10픽셀의 BorderLayout 사용

        // 제목 패널 설정
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 패널 여백 설정
        ct.add(titlePanel, BorderLayout.NORTH);

        // 좌석 패널 설정 (8x8 그리드)
        JPanel seatPanel = new JPanel(new GridLayout(8, 8, 5, 5));
        seatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 좌석 버튼 초기화 (관리자는 클릭 불가)
        for (int i = 0; i < 64; i++) {
            seatButtons[i] = new JButton(String.valueOf(i + 1));
            seatButtons[i].setBackground(new Color(77, 77, 77)); // 기본 색상 (회색)
            seatButtons[i].setForeground(Color.WHITE);
            seatButtons[i].setFocusPainted(false);
            seatButtons[i].setEnabled(false); // 관리자는 좌석 클릭 불가
            seatPanel.add(seatButtons[i]);
        }
        ct.add(seatPanel, BorderLayout.CENTER);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        refreshButton = new JButton("새로고침");
        cancelButton = new JButton("돌아가기");
        
        refreshButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(cancelButton);
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 창 설정
        setTitle("좌석 사용 현황");
        setSize(900, 700);
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // 초기 좌석 상태 로드
        loadSeatStatus();

        // 자동 새로고침 타이머 설정 (1초 간격)
        autoRefreshTimer = new Timer(1000, e -> loadSeatStatus());
        autoRefreshTimer.start();

        setVisible(true);
    }

    // 좌석 상태를 DB에서 조회하여 화면에 표시하는 메소드
    private void loadSeatStatus() {
        try {
            // 모든 좌석 초기화 (회색)
            for (int i = 0; i < seatButtons.length; i++) {
                seatButtons[i].setBackground(new Color(77, 77, 77));
                seatButtons[i].setText(String.valueOf(i + 1));
            }

            // DB에서 사용 중인 좌석 정보 조회
            String sql = "SELECT * FROM seatstatus";
            Statement stmt = Main.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int seatNum = rs.getInt("seat_number") - 1;
                String userName = rs.getString("user_name");
                String userId = rs.getString("user_id");
                String remainingTime = rs.getString("remaining_time");

                // 좌석 정보 표시 (좌석번호, 사용자명, 남은시간)
                String displayText = String.format("<html><center>%d번<br>%s<br>%s</center></html>",
                    seatNum + 1, userName, remainingTime);

                // 회원/비회원 구분하여 색상 설정
                // 비회원(숫자 ID)은 빨간색, 회원은 초록색으로 표시
                if (userId.matches("\\d+")) {
                    seatButtons[seatNum].setBackground(new Color(240, 0, 0));
                } else {
                    seatButtons[seatNum].setBackground(new Color(100, 200, 0));
                }
                seatButtons[seatNum].setText(displayText);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "좌석 정보를 불러오는 중 오류가 발생했습니다.", 
                "오류", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // 버튼 클릭 이벤트 처리
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            loadSeatStatus(); // 수동 새로고침
        } else if (e.getSource() == cancelButton) {
            // 타이머 정지 및 창 닫기
            if (autoRefreshTimer != null) {
                autoRefreshTimer.stop();
            }
            dispose();
            new Manager_Menu_GUI();
        }
    }
}
