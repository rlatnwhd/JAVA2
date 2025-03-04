import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

// PC방 실시간 좌석 사용 관리 시스템의 메인 인터페이스
// 사용자의 PC 이용 현황을 실시간으로 모니터링하고 제어하는 핵심 클래스
// 시간 관리, 좌석 이동, 쿠폰 구매 등 모든 실시간 기능을 통합 관리
public class Using_GUI extends JFrame implements ActionListener {
    // === 사용자 세션 정보 ===
    private String loginUserName;  // 현재 로그인한 사용자 이름
    private String loginUserID;    // 현재 로그인한 사용자 ID (DB 조회용 키값)
    private int seatNumber;        // 현재 사용 중인 좌석 번호 (좌석 관리용)
    
    // === GUI 컴포넌트 ===
    private JLabel timeLabel;      // 실시간으로 갱신되는 남은 시간 표시 라벨
    private JLabel infoLabel;      // 사용자 정보와 좌석 번호 표시 라벨
    private Timer timer;           // 1분 단위로 시간을 감소시키는 타이머
    private int remainingMinutes;  // 실시간 잔여 시간 (분 단위)
    private Timer displayTimer;    // 화면 갱신용 타이머 추가
    
    // === 싱글톤 인스턴스 ===
    // 다른 클래스(특히 Coupon 클래스)에서 시간 추가나 갱신을 위해 접근하는 전역 참조점
    public static Using_GUI instance;

    // === 생성자: PC 사용 화면 초기화 ===
    // @param userName: 사용자 이름 (화면 표시용)
    // @param userID: 사용자 ID (DB 작업용)
    // @param seatNum: 선택된 좌석 번호
    public Using_GUI(String userName, String userID, int seatNum) {
        instance = this;  // 싱글톤 패턴 구현 - 전역 접근점 설정
        this.loginUserName = userName;
        this.loginUserID = userID;
        this.seatNumber = seatNum;

        // === 메인 컨테이너 설정 ===
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10));  // 컴포넌트 간 10픽셀 여백

        // === 정보 표시 패널 구성 ===
        // GridLayout(3,1)로 세 개의 정보를 수직 정렬
        // 1. 타이틀 라벨
        // 2. 사용자 정보 라벨
        // 3. 실시간 시간 표시 라벨
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        infoLabel = new JLabel("사용자: " + loginUserName + " | 좌석번호: " + seatNumber + "번");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel = new JLabel("남은 시간: 로딩중...");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        
        infoPanel.add(new JLabel("PC 사용 현황", JLabel.CENTER));
        infoPanel.add(infoLabel);
        infoPanel.add(timeLabel);
        
        ct.add(infoPanel, BorderLayout.CENTER);

        // === 기능 버튼 패널 구성 ===
        // GridLayout(1,3)으로 세 개의 버튼을 수평 정렬
        // 1. 자리 이동: 현재 좌석을 유지한 채 다른 좌석으로 이동
        // 2. 쿠폰 구매: 시간 추가를 위한 쿠폰 구매 창 호출
        // 3. 사용 종료: PC 사용 종료 및 로그아웃
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton moveButton = new JButton("자리 이동");
        JButton couponButton = new JButton("쿠폰 구매");
        JButton endButton = new JButton("사용 종료");
        
        moveButton.addActionListener(this);
        couponButton.addActionListener(this);
        endButton.addActionListener(this);
        
        buttonPanel.add(moveButton);
        buttonPanel.add(couponButton);
        buttonPanel.add(endButton);
        
        ct.add(buttonPanel, BorderLayout.SOUTH);

        setTitle("PC 사용 중");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        // === 타이머 시작 ===
        // 시간 감소, DB 업데이트, UI 갱신을 통합 관리하는 타이머 실행
        startTimer();
        
        // 1초마다 시간 표시를 갱신하는 타이머 시작
        startDisplayTimer();
        
        // 기존 1분 타이머도 유지 (실제 시간 감소용)
        loadCurrentTime();
    }

    // === 타이머 초기화 및 시작 메소드 ===
    // 1분(60000ms) 간격으로 다음 작업을 수행:
    // 1. DB에서 좌석 상태 확인 (비정상 종료 대비)
    // 2. 남은 시간 1분 감소
    // 3. UI 시간 표시 갱신
    // 4. DB 시간 정보 업데이트
    // 5. 시간 만료 시 자동 종료 처리
    private void startTimer() {
        loadCurrentTime(); // 현재 시간 로드
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // 좌석 상태 확인
                try {
                    String sql = "SELECT * FROM seatstatus WHERE user_id = ?";
                    PreparedStatement pstmt = Main.conn.prepareStatement(sql);
                    pstmt.setString(1, loginUserID);
                    ResultSet rs = pstmt.executeQuery();
                    
                    // PC 이용 중일 때만 시간 차감
                    if (rs.next() && remainingMinutes > 0) {
                        remainingMinutes--;
                        updateTimeLabel();
                        updateDatabase();
                        
                        if (remainingMinutes == 0) {
                            timer.cancel();
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(Using_GUI.this, "시간이 만료되었습니다.");
                                endUse();
                            });
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 60000, 60000); // 1분(60000ms)마다 실행
        
        // 초기 시간 표시
        updateTimeLabel();
    }

    // === 현재 시간 로드 메소드 ===
    // DB의 user 테이블에서 현재 사용자의 남은 시간을 조회
    // "HH:mm" 형식의 시간 문자열을 분 단위로 변환하여 저장
    private void loadCurrentTime() {
        try {
            String sql = "SELECT time FROM user WHERE id = ?";
            PreparedStatement pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, loginUserID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String time = rs.getString("time");
                String[] parts = time.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                remainingMinutes = hours * 60 + minutes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === 시간 표시 갱신 메소드 ===
    // remainingMinutes를 "HH:mm" 형식으로 변환하여 화면에 표시
    // EDT(Event Dispatch Thread)에서 안전하게 UI 업데이트
    private void updateTimeLabel() {
        int hours = remainingMinutes / 60;
        int minutes = remainingMinutes % 60;
        SwingUtilities.invokeLater(() -> {
            timeLabel.setText(String.format("남은 시간: %02d:%02d", hours, minutes));
        });
    }

    // === DB 시간 정보 갱신 메소드 ===
    // 두 개의 테이블 동시 업데이트로 데이터 일관성 유지
    // 1. user 테이블: 사용자의 전체 남은 시간
    // 2. seatstatus 테이블: 현재 좌석 사용 상태
    private void updateDatabase() {
        try {
            int hours = remainingMinutes / 60;
            int minutes = remainingMinutes % 60;
            String timeStr = String.format("%02d:%02d", hours, minutes);
            
            // user 테이블 업데이트
            String sql = "UPDATE user SET time = ? WHERE id = ?";
            PreparedStatement pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, timeStr);
            pstmt.setString(2, loginUserID);
            pstmt.executeUpdate();
            
            // seatstatus 테이블 업데이트
            sql = "UPDATE seatstatus SET remaining_time = ? WHERE user_id = ?";
            pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, timeStr);
            pstmt.setString(2, loginUserID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === 시간 추가 메소드 ===
    // 쿠폰 구매 시 호출되어 시간을 추가하고 관련 정보 갱신
    // @param additionalTime: "HH:mm" 형식의 추가될 시간
    public void addTime(String additionalTime) {
        String[] parts = additionalTime.split(":");
        int additionalMinutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        remainingMinutes += additionalMinutes;
        updateTimeLabel();
        updateDatabase();
    }

    // === PC 사용 종료 처리 메소드 ===
    // 1. seatstatus 테이블에서 현재 사용자 정보 삭제
    // 2. 실행 중인 타이머 정지
    // 3. 모든 자원 정리 후 초기 화면으로 복귀
    private void endUse() {
        try {
            String sql = "DELETE FROM seatstatus WHERE user_id = ?";
            PreparedStatement pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, loginUserID);
            pstmt.executeUpdate();
            
            if (timer != null) {
                timer.cancel();
            }
            dispose();
            new User_GUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === 이벤트 처리 메소드 ===
    // 세 가지 주요 기능 처리:
    // 1. 자리 이동: 현재 좌석 정보 삭제 후 좌석 선택 화면으로 이동
    // 2. 쿠폰 구매: 현재 창을 유지한 채로 쿠폰 구매 창 실행
    // 3. 사용 종료: 사용자 확인 후 종료 처리 실행
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
                    
                    // 타이머는 계속 실행 중인 상태로 유지
                    dispose();
                    new SeatManagement(loginUserName, loginUserID);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
                
            case "쿠폰 구매":
                // 타이머 유지한 채로 쿠폰 구매 창 열기
                new Coupon(loginUserName, loginUserID);
                break;
                
            case "사용 종료":
                int confirm = JOptionPane.showConfirmDialog(this,
                    "정말 사용을 종료하시겠습니까?",
                    "사용 종료 확인",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    endUse();
                }
                break;
        }
    }

    // === 공개 유틸리티 메소드 ===
    // 쿠폰 구매나 시스템 요청으로 시간 정보 갱신이 필요할 때 호출
    public void updateAfterCouponPurchase() {
        loadCurrentTime(); // 새로운 시간 로드
        updateTimeLabel(); // 화면 업데이트
    }

    public void refreshTime() {
        updateDisplayTime();
    }

    private void startDisplayTimer() {
        if (displayTimer != null) {
            displayTimer.cancel();
            displayTimer.purge();
        }
        
        displayTimer = new Timer();
        displayTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    updateDisplayTime();
                });
            }
        }, 0, 1000); // 1초마다 실행
    }

    private void updateDisplayTime() {
        try {
            String sql = "SELECT TIME FROM user WHERE ID = ?";
            PreparedStatement pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, loginUserID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String timeStr = rs.getString("TIME");
                String[] timeParts = timeStr.split(":");
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);
                timeLabel.setText(String.format("남은 시간: %02d:%02d", hours, minutes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // dispose() 메소드 오버라이드하여 타이머들을 정리
    @Override
    public void dispose() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (displayTimer != null) {
            displayTimer.cancel();
            displayTimer.purge();
        }
        super.dispose();
    }
}
