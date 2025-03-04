import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// PC방 키오스크의 메인 화면을 구현한 클래스
// 사용자와 관리자가 시스템에 접근할 수 있는 초기 진입점 제공
class PC_Privilege extends JFrame implements ActionListener {
    // PC방 GUI 컴포넌트
    private JLabel jl = new JLabel("안녕하세요. PC방 프로그램입니다."); // 환영 메시지 라벨
    private JLabel getoLabel = new JLabel(new ImageIcon("image/geto_image.jpeg")); // 로고 이미지 라벨

    // 생성자: 메인 화면 GUI 초기화
    // BorderLayout을 사용하여 컴포넌트들을 배치
    // 상단(NORTH): 로고 이미지
    // 중앙(CENTER): 환영 메시지
    // 하단(SOUTH): 버튼 패널(사용자/관리자/종료)
    public PC_Privilege() {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10)); // 간격 조정 (가로, 세로)

        // 상단 이미지
        JPanel imagePanel = new JPanel();
        imagePanel.add(getoLabel);
        imagePanel.setBorder(new EmptyBorder(10, 10, 0, 10)); // 위, 좌, 아래, 우 여백
        ct.add(imagePanel, BorderLayout.NORTH);

        // 중앙 텍스트
        JPanel mainLabel = new JPanel();
        mainLabel.add(jl);
        mainLabel.setBorder(new EmptyBorder(0, 10, 0, 10)); // 좌우만 여백 추가
        ct.add(mainLabel, BorderLayout.CENTER);

        // 버튼 영역
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0)); // 버튼 사이 간격 추가
        JButton exit = new JButton("종료");
        JButton user = new JButton("사용자로 시작");
        JButton manager = new JButton("관리자로 시작");

        exit.addActionListener(this);
        user.addActionListener(this);
        manager.addActionListener(this);

        buttonPanel.add(user);
        buttonPanel.add(manager);
        buttonPanel.add(exit);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 패널 여백 추가
        ct.add(buttonPanel, BorderLayout.SOUTH);

        setTitle("GETO PC방");
        setSize(400, 310); // 창 크기 약간 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
        setResizable(false);
        setVisible(true);
    }

    // 버튼 클릭 이벤트 처리 메소드
    // switch 문을 사용하여 각 버튼별 동작 처리:
    // - "사용자로 시작": 현재 창을 닫고 User_GUI 화면으로 전환
    // - "관리자로 시작": 현재 창을 닫고 Manager_GUI 화면으로 전환
    // - "종료": 현재 창을 닫고 프로그램 종료
    // 각 전환 시 dispose()를 호출하여 현재 창의 리소스를 정리
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        switch (s) {
            case "사용자로 시작":
            	dispose();
            	new User_GUI();
                break;
            case "관리자로 시작":
            	dispose();
                new Manager_GUI();
                break;
            default:
                dispose();
        }
    }
}
