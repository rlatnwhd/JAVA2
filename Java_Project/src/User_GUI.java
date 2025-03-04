import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// PC방 사용자 초기 화면을 구현한 클래스
// 회원/비회원 로그인과 회원가입 기능을 제공하는 첫 진입점 인터페이스
class User_GUI extends JFrame implements ActionListener {
    // GUI 컴포넌트들
    // 게토 이미지를 로고로 사용하여 브랜드 아이덴티티 강화
    private JLabel getoLabel = new JLabel(new ImageIcon("image/geto_image.jpeg"));
    // 사용자에게 메뉴 선택을 안내하는 라벨
    private JLabel jl = new JLabel("<< 원하시는 메뉴를 선택하여 주십시오 >>");

    // 생성자: 사용자 초기 화면 GUI 초기화
    // BorderLayout을 사용하여 화면을 세 영역으로 구분
    // - NORTH: 로고 이미지
    // - CENTER: 안내 메시지
    // - SOUTH: 기능 버튼들
    public User_GUI() {
        Container ct = getContentPane();
        // 여백 10픽셀로 설정하여 컴포넌트간 적절한 간격 유지
        ct.setLayout(new BorderLayout(10, 10));

        // 이미지 패널 설정
        // FlowLayout.CENTER로 이미지를 중앙 정렬하여 시각적 균형 유지
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(getoLabel);
        // 패널에 여백을 추가하여 시각적 답답함 해소
        imagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ct.add(imagePanel, BorderLayout.NORTH);

        // 안내 메시지 패널
        // 중앙 정렬로 메시지의 가시성 향상
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(jl);
        labelPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        ct.add(labelPanel, BorderLayout.CENTER);

        // 버튼 패널 - 두 개의 하위 패널로 구성
        // BorderLayout을 사용하여 상단/하단 버튼 배치
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // 상단 버튼 패널 - GridLayout으로 3개 버튼 균등 배치
        // 회원 로그인, 비회원 로그인, 회원가입 버튼을 수평으로 정렬
        JPanel topButtonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton member = new JButton("회원 로그인");
        JButton nonmember = new JButton("비회원 로그인");
        JButton signUp = new JButton("회원가입");
        topButtonPanel.add(member);
        topButtonPanel.add(nonmember);
        topButtonPanel.add(signUp);

        // 하단 버튼 패널 - 취소 버튼
        // FlowLayout으로 단일 버튼 중앙 배치
        JPanel bottomButtonPanel = new JPanel(new FlowLayout());
        JButton cancel = new JButton("취소");
        bottomButtonPanel.add(cancel);

        // 버튼 이벤트 리스너 등록
        member.addActionListener(this);
        nonmember.addActionListener(this);
        signUp.addActionListener(this);
        cancel.addActionListener(this);
        
        // 버튼 패널 구성 완성
        buttonPanel.add(topButtonPanel, BorderLayout.NORTH);
        buttonPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 창 설정
        setTitle("사용자 옵션");
        setSize(400, 370);  // 모든 컴포넌트가 잘 보이는 최적 크기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);  // 창 크기 고정으로 UI 일관성 유지
        setVisible(true);
    }

    // 버튼 클릭 이벤트 처리 메소드
    // 각 버튼별 기능:
    // 1. 회원 로그인: SignIn 클래스로 이동
    // 2. 비회원 로그인: 
    //    - 랜덤 4자리 ID 생성 (1000-9999)
    //    - Coupon 클래스로 이동하여 시간 구매
    // 3. 회원가입: SignUp 클래스로 이동
    // 4. 취소: GETO_Privilege 클래스로 이동
    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        String s = e.getActionCommand();
        
        switch(s) {
            case "회원 로그인" :
                dispose();
                new SignIn();
                break;
            case "비회원 로그인" :
                dispose();
                // 1000-9999 사이의 랜덤 ID 생성
                String nonMemberID = Integer.toString(random.nextInt(9000) + 1000);
                new Coupon("비회원", nonMemberID);
                break;
            case "회원가입" :
                dispose();
                new SignUp();
                break;
            default :  // 취소 버튼
                dispose();
                new PC_Privilege();
        }
    }
}
