import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// PC방 관리자 메뉴 화면을 구현한 클래스
// 관리자가 사용할 수 있는 다양한 관리 기능을 제공하는 메인 인터페이스
class Manager_Menu_GUI extends JFrame implements ActionListener {
    // GUI 컴포넌트
    private JLabel getoLabel = new JLabel(new ImageIcon("image/geto_image.jpeg")); // 로고 이미지
    private JLabel jl = new JLabel("<< 환영합니다, 사장님! GETO SHOP 관리자 설정입니다 >>"); // 환영 메시지

    // 생성자: 관리자 메뉴 GUI 초기화
    // BorderLayout을 사용하여 3개의 주요 영역으로 구성
    // - NORTH: 로고 이미지
    // - CENTER: 환영 메시지
    // - SOUTH: 관리 기능 버튼들
    public Manager_Menu_GUI() {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10)); // BorderLayout 사용, 여백 설정

        // 이미지 패널
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(getoLabel);
        imagePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 패널의 안쪽 여백
        ct.add(imagePanel, BorderLayout.NORTH);

        // 설명 레이블 패널
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(jl);
        labelPanel.setBorder(new EmptyBorder(5, 10, 5, 10)); // 안쪽 여백
        ct.add(labelPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // 상단 버튼들 (회원 로그인, 비회원 로그인, 회원가입)
        JPanel topButtonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton memberManagement = new JButton("회원 관리");
        JButton revenueCheck = new JButton("총 수익 확인");
        JButton seatStatus = new JButton("자리 사용 현황");
        topButtonPanel.add(memberManagement);
        topButtonPanel.add(revenueCheck);
        topButtonPanel.add(seatStatus);

        // 하단 버튼 (취소)
        JPanel bottomButtonPanel = new JPanel(new FlowLayout());
        JButton cancel = new JButton("취소");
        bottomButtonPanel.add(cancel);

        memberManagement.addActionListener(this);
        revenueCheck.addActionListener(this);
        seatStatus.addActionListener(this);
        cancel.addActionListener(this);
        
        // 버튼 패널 구성
        buttonPanel.add(topButtonPanel, BorderLayout.NORTH);
        buttonPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 패널의 안쪽 여백
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 창 설정
        setTitle("관리자 옵션");
        setSize(400, 370); // 창 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // 버튼 클릭 이벤트 처리 메소드
    // 각 관리 기능별 화면 전환 처리:
    // - "회원 관리": 회원 정보 관리 화면(MembershipManagement)으로 전환
    // - "총 수익 확인": 매출 관리 화면(RevenueManagement)으로 전환
    // - "자리 사용 현황": 좌석 현황 화면(SeatUsageStatus)으로 전환
    // - "취소": 관리자 권한 선택 화면(GETO_Privilege)으로 돌아감
    // 각 전환 시 DB 재연결 확인 수행
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        
        switch(s) {
            case "회원 관리" :
                // DB 재연결 확인
                Main.reconnectDB();
                dispose();
                new MembershipManagement();
                break;
            case "총 수익 확인" :
                // DB 재연결 확인
                Main.reconnectDB();
                dispose();
                new RevenueManagement();
                break;
            case "자리 사용 현황" :
                Main.reconnectDB();
                dispose();
                new SeatUsageStatus();
                break;
            default :
                dispose();
                new PC_Privilege();
        }
        
    }
}
