import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// PC방 쿠폰 구매 시스템을 구현한 클래스
// 회원과 비회원 모두 이용 가능하며, 다양한 시간/가격대의 쿠폰을 구매할 수 있음
class Coupon extends JFrame implements ActionListener {
    // GUI 컴포넌트
    private JLabel getoLabel = new JLabel(new ImageIcon("image/geto_image.jpeg")); // 로고 이미지 표시용 라벨
    private JLabel jl = new JLabel("<< 원하시는 옵션을 선택하여 주십시오 >>"); // 안내 메시지 라벨
    
    // 현재 로그인된 사용자 정보를 저장할 변수들
    private String loginUserName;  // 사용자 이름 (비회원의 경우 "비회원")
    private String loginUserID;    // 사용자 ID (비회원의 경우 랜덤 생성된 숫자)
    private String loginUserTime;  // 사용자의 현재 남은 시간

    // 생성자: 사용자 정보를 받아 쿠폰 구매 화면을 초기화
    public Coupon(String userName, String userID) {
        this.loginUserName = userName;
        this.loginUserID = userID;
        
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10));

        // 이미지 패널
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(getoLabel);
        imagePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 패널의 안쪽 여백
        ct.add(imagePanel, BorderLayout.NORTH);

        // 센터 패널 (설명 라벨 + 쿠폰 패널 포함)
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // 설명 레이블 패널
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(jl);
        labelPanel.setBorder(new EmptyBorder(5, 10, 5, 10)); // 안쪽 여백
        centerPanel.add(labelPanel, BorderLayout.NORTH);

        // 쿠폰 패널
        JPanel couponPanel = new JPanel(new GridLayout(2, 4, 10, 10)); // 버튼 사이 여백 추가
        couponPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 패널의 여백 추가

        JButton coupon1 = new JButton("<html><div style='text-align: center;'>1,000원<br>(00:45)</div></html>");
        JButton coupon2 = new JButton("<html><div style='text-align: center;'>2,000원<br>(01:30)</div></html>");
        JButton coupon3 = new JButton("<html><div style='text-align: center;'>3,000원<br>(02:15)</div></html>");
        JButton coupon4 = new JButton("<html><div style='text-align: center;'>5,000원<br>(03:45)</div></html>");
        JButton coupon5 = new JButton("<html><div style='text-align: center;'>10,000원<br>(07:30)</div></html>");
        JButton coupon6 = new JButton("<html><div style='text-align: center;'>20,000원<br>(15:10)</div></html>");
        JButton coupon7 = new JButton("<html><div style='text-align: center;'>30,000원<br>(22:45)</div></html>");
        JButton coupon8 = new JButton("<html><div style='text-align: center;'>50,000원<br>(40:00)</div></html>");


        // 버튼 이벤트 리스너 추가
        coupon1.addActionListener(this);
        coupon2.addActionListener(this);
        coupon3.addActionListener(this);
        coupon4.addActionListener(this);
        coupon5.addActionListener(this);
        coupon6.addActionListener(this);
        coupon7.addActionListener(this);
        coupon8.addActionListener(this);

        // 버튼 패널에 추가
        couponPanel.add(coupon1);
        couponPanel.add(coupon2);
        couponPanel.add(coupon3);
        couponPanel.add(coupon4);
        couponPanel.add(coupon5);
        couponPanel.add(coupon6);
        couponPanel.add(coupon7);
        couponPanel.add(coupon8);

        centerPanel.add(couponPanel, BorderLayout.CENTER);
        ct.add(centerPanel, BorderLayout.CENTER);

        // 버튼 패널 (취소 버튼)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cancel = new JButton("취소");
        cancel.addActionListener(this);
        buttonPanel.add(cancel);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 패널 여백
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 프레임 설정
        setTitle("쿠폰 구매");
        setSize(450, 500); // 창 크기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // "HH:mm" 형식의 시간 문자열을 총 분으로 변환하는 메소드
    private int convertTimeToMinutes(String timeStr) throws IllegalArgumentException {
        try {
            if (timeStr == null || timeStr.trim().isEmpty()) {
                throw new IllegalArgumentException("시간 문자열이 비어있습니다");
            }
            
            String[] parts = timeStr.trim().split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("잘못된 시간 형식입니다");
            }
            
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            
            if (hours < 0 || minutes < 0 || minutes >= 60) {
                throw new IllegalArgumentException("유효하지 않은 시간값입니다");
            }
            
            return hours * 60 + minutes;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다");
        }
    }

    // 총 분을 "HH:mm" 형식의 시간 문자열로 변환하는 메소드
    private String convertMinutesToTimeString(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    // 버튼 클릭 이벤트 처리 메소드
    // - 쿠폰 구매 처리
    // - 취소 버튼 처리
    // - DB 업데이트 및 구매 내역 기록
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();
        
        if (buttonText.equals("취소")) {
            dispose();
            if (loginUserName.equals("비회원")) {
                new User_GUI();
            }
            return;
        }
        
        // HTML 태그 제거 및 쿠폰 정보 파싱
        String cleanText = buttonText.replaceAll("<[^>]*>", "");
        String[] parts = cleanText.split("\\(|\\)");
        String price = parts[0].trim();
        String time = parts[1].trim();
        
        int result = JOptionPane.showConfirmDialog(this, 
            price + "을(를) 구매하시겠습니까?\n이용 시간: " + time,
            "구매 확인",
            JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            try {
                if (loginUserName.equals("비회원")) {
                    // 비회원의 경우 구매한 시간을 바로 저장
                    loginUserTime = time;  // 클래스 멤버 변수로 시간 저장
                } else {
                    // 기존 회원 처리 코드
                    String getTimeSQL = "SELECT TIME FROM user WHERE ID = ?";
                    PreparedStatement pstmtTime = Main.conn.prepareStatement(getTimeSQL);
                    pstmtTime.setString(1, loginUserID);
                    ResultSet timeRs = pstmtTime.executeQuery();
                    
                    // 현재 시간을 분으로 변환
                    int currentMinutes = 0;
                    if (timeRs.next()) {
                        String currentTime = timeRs.getString("TIME");
                        if (currentTime != null && !currentTime.isEmpty()) {
                            currentMinutes = convertTimeToMinutes(currentTime);
                        }
                    }
                    
                    // 추가할 시간을 분으로 변환
                    int additionalMinutes = convertTimeToMinutes(time);
                    
                    // 총 시간 계산
                    int totalMinutes = currentMinutes + additionalMinutes;
                    
                    // 시간 형식으로 변환
                    String newTime = convertMinutesToTimeString(totalMinutes);
                    
                    // 사용자의 시간 업데이트
                    String updateTimeSQL = "UPDATE user SET TIME = ? WHERE ID = ?";
                    PreparedStatement pstmt1 = Main.conn.prepareStatement(updateTimeSQL);
                    pstmt1.setString(1, newTime);
                    pstmt1.setString(2, loginUserID);
                    pstmt1.executeUpdate();
                    
                    // seatstatus 테이블도 업데이트
                    String updateSeatSQL = "UPDATE seatstatus SET remaining_time = ? WHERE user_id = ?";
                    PreparedStatement pstmt3 = Main.conn.prepareStatement(updateSeatSQL);
                    pstmt3.setString(1, newTime);
                    pstmt3.setString(2, loginUserID);
                    pstmt3.executeUpdate();
                }
                
                // 구매 내역 저장
                String couponInfo = price + " (" + time + ")";
                String insertSaleSQL = "INSERT INTO salesstatus (NAME, ID, COUPON, TIME) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt2 = Main.conn.prepareStatement(insertSaleSQL);
                pstmt2.setString(1, loginUserName);
                pstmt2.setString(2, loginUserID);
                pstmt2.setString(3, couponInfo);
                pstmt2.setString(4, Main.date);
                pstmt2.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "구매가 완료되었습니다.");
                dispose();

                // 비회원인 경우 자리 선택 화면으로 이동
                if (loginUserName.equals("비회원")) {
                    new SeatManagement(loginUserName, loginUserID);
                }
                // 회원이고 Using_GUI가 열려있다면 시간 업데이트
                else if (Using_GUI.instance != null) {
                    Using_GUI.instance.refreshTime();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "오류가 발생했습니다: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
