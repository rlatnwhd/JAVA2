import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

// PC방 회원 관리 시스템을 구현한 클래스
// 회원 정보의 조회, 추가, 수정, 삭제 기능을 제공하는 관리자용 인터페이스
public class MembershipManagement extends JFrame implements ActionListener {
    // GUI 컴포넌트
    private JTable userTable;          // 회원 정보를 표시할 테이블
    private JTextField searchField;     // 검색어 입력 필드
    private JComboBox<String> comboBox; // 검색 조건 선택 콤보박스
    private JButton searchButton, addButton, modifyButton, deleteButton, cancelButton; // 기능 버튼들

    private UserDAO userDAO;           // 회원 데이터 처리를 위한 DAO 객체

    // 생성자: 회원 관리 화면 GUI 초기화
    // 테이블, 검색 패널, 버튼 패널로 구성
    public MembershipManagement() {
        userDAO = new UserDAO();

        // 메인 컨테이너
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10));

        // 검색 패널
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        comboBox = new JComboBox<>(new String[]{"이름", "성별", "아이디", "이메일", "남은시간", "가입일", "전화번호"});
        searchField = new JTextField();
        searchButton = new JButton("검색");
        searchPanel.add(comboBox, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // 테이블 설정
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀 편집 불가
            }
        };
        
        model.addColumn("이름");
        model.addColumn("성별");
        model.addColumn("아이디");
        model.addColumn("이메일");
        model.addColumn("남은시간");
        model.addColumn("가입일");
        model.addColumn("전화번호");
        userTable = new JTable(model);
        userTable.getTableHeader().setReorderingAllowed(false); // 컬럼 순서 변경 방지

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));

        // 상단 버튼 그룹 (회원 추가, 수정, 삭제)
        JPanel upperButtonPanel = new JPanel(new GridLayout(1, 3, 20, 10));
        addButton = new JButton("회원 추가");
        modifyButton = new JButton("회원 수정");
        deleteButton = new JButton("회원 삭제");
        upperButtonPanel.add(addButton);
        upperButtonPanel.add(modifyButton);
        upperButtonPanel.add(deleteButton);

        // 하단 버튼 그룹 (취소 버튼)
        JPanel lowerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cancelButton = new JButton("취소");
        lowerButtonPanel.add(cancelButton);

        // 버튼 패널에 상하 그룹 추가
        buttonPanel.add(upperButtonPanel, BorderLayout.CENTER);
        buttonPanel.add(lowerButtonPanel, BorderLayout.SOUTH);

        // 테이블 스크롤 추가
        JScrollPane tableScroll = new JScrollPane(userTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // 이벤트 설정
        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // 테이블 초기 데이터 설정
        updateTable(userDAO.getAllUsers());

        // 컴포넌트 추가
        ct.add(searchPanel, BorderLayout.NORTH);
        ct.add(tableScroll, BorderLayout.CENTER);
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 창 설정
        setTitle("회원 관리 시스템");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // 회원 정보 입력/수정 폼을 여는 메소드
    // @param user - 수정할 회원 정보 (신규 추가시 null)
    // @param isEditMode - 수정 모드 여부
    private void openUserForm(User user, boolean isEditMode) {
        UserForm form = new UserForm(this, user, isEditMode);
        form.setVisible(true);
        
        // 모달 방식으로 변경하여 폼이 닫힐 때까지 대기하도록 수정
        form.setModal(true);
        
        // 폼이 닫힌 후에 확인 여부를 체크
        if (form.isConfirmed()) {
            if (isEditMode) {
                userDAO.modifyUser(user.getId(), form.getUser());
            } else {
                userDAO.addUser(form.getUser());
            }
            updateTable(userDAO.getAllUsers());
        }
    }

    // 버튼 클릭 이벤트 처리 메소드
    // - 검색: 선택된 조건으로 회원 검색
    // - 추가: 새 회원 정보 입력 폼 열기
    // - 수정: 선택된 회원 정보 수정 폼 열기
    // - 삭제: 선택된 회원 정보 삭제
    // - 취소: 관리자 메뉴로 돌아가기
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == searchButton) {
            String keyword = searchField.getText();
            String column = comboBox.getSelectedItem().toString();

            // 검색 실행
            try {
                updateTable(userDAO.searchUsers(column, keyword));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "잘못된 검색 컬럼입니다: " + ex.getMessage());
            }
        } else if (source == addButton) {
            openUserForm(null, false); // 추가 모드로 열기
        } else if (source == modifyButton) {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                // 선택된 행의 ID 가져오기
                String userId = userTable.getValueAt(selectedRow, 2).toString();
                User user = userDAO.getUserById(userId);

                if (user != null) {
                    openUserForm(user, true); // 수정 모드로 열기
                } else {
                    JOptionPane.showMessageDialog(this, "사용자 정보를 불러올 수 없습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "수정할 회원을 선택하세요.");
            }
        } else if (source == deleteButton) {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String userId = userTable.getValueAt(selectedRow, 2).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    userDAO.deleteUser(userId);
                    updateTable(userDAO.getAllUsers());
                }
            } else {
                JOptionPane.showMessageDialog(this, "삭제할 회원을 선택하세요.");
            }
        } else if (source == cancelButton) {
        	dispose();
        	new Manager_Menu_GUI();
        }
    }

    // 테이블 데이터 업데이트 메소드
    // @param users - 표시할 회원 목록
    private void updateTable(ArrayList<User> users) {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);  // 기존 테이블 데이터 초기화
        for (User user : users) {
            model.addRow(new Object[]{
                user.getName(), user.getGender(), user.getId(),
                user.getEmail(), user.getTime(), user.getSignIn(), user.getNumber()
            });
        }
    }
}

// 회원 정보 입력/수정을 위한 모달 다이얼로그 클래스
class UserForm extends JDialog {
    // GUI 컴포넌트
    private JTextField nameField, idField, emailField, timeField, numberField, passwordField;
    private JComboBox<String> genderCombo;
    private JButton confirmButton, cancelButton;
    private User user;                 // 처리할 회원 정보
    private boolean isConfirmed = false; // 확인 버튼 클릭 여부

    // 생성자: 회원 정보 입력/수정 폼 초기화
    public UserForm(Frame owner, User user, boolean isEditMode) {
        super(owner, isEditMode ? "회원 수정" : "회원 추가", true);

        this.user = user;

        // 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 입력 필드 패널
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.add(new JLabel(" 이름"));
        nameField = new JTextField(user != null ? user.getName() : "");
        inputPanel.add(nameField);

        inputPanel.add(new JLabel(" 성별"));
        genderCombo = new JComboBox<>(new String[]{"남성", "여성"});
        genderCombo.setSelectedItem(user != null ? user.getGender() : "남성");
        inputPanel.add(genderCombo);

        inputPanel.add(new JLabel(" 아이디"));
        idField = new JTextField(user != null ? user.getId() : "");
        idField.setEditable(!isEditMode);
        inputPanel.add(idField);

        inputPanel.add(new JLabel(" 비밀번호"));
        passwordField = new JTextField();
        if (isEditMode && user != null) {
            passwordField.setText("********");
            passwordField.setEditable(false);
        }
        inputPanel.add(passwordField);
        
        inputPanel.add(new JLabel(" 이메일"));
        emailField = new JTextField(user != null ? user.getEmail() : "");
        inputPanel.add(emailField);

        inputPanel.add(new JLabel(" 남은시간"));
        timeField = new JTextField(user != null ? user.getTime() : "");
        inputPanel.add(timeField);

        inputPanel.add(new JLabel(" 전화번호"));
        numberField = new JTextField(user != null ? user.getNumber() : "");
        inputPanel.add(numberField);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        cancelButton = new JButton("취소");
        confirmButton = new JButton("확인");
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        // 버튼 이벤트
        confirmButton.addActionListener(e -> {
            // 모든 필드 검증
            if (nameField.getText().trim().isEmpty() ||
                idField.getText().trim().isEmpty() ||
                (!isEditMode && passwordField.getText().trim().isEmpty()) ||
                emailField.getText().trim().isEmpty() ||
                timeField.getText().trim().isEmpty() ||
                numberField.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 새로운 회원 추가시 아이디 중복 검사
            if (!isEditMode) {
                UserDAO userDAO = new UserDAO();
                if (userDAO.isIdExists(idField.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다.", "중복 오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            int confirm = JOptionPane.showConfirmDialog(this, "회원 정보를 저장하시겠습니까?", "저장 확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.user = new User();
                this.user.setName(nameField.getText());
                this.user.setGender(genderCombo.getSelectedItem().toString());
                this.user.setId(idField.getText());
                
                if (isEditMode) {
                    this.user.setPw(user.getPw());
                } else {
                    this.user.setPw(passwordField.getText());
                }
                
                this.user.setEmail(emailField.getText());
                this.user.setTime(timeField.getText());
                this.user.setNumber(numberField.getText());
                
                if (!isEditMode) {
                    java.time.LocalDate currentDate = java.time.LocalDate.now();
                    this.user.setSignIn(currentDate.toString());
                } else {
                    this.user.setSignIn(user.getSignIn());
                }
                
                isConfirmed = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // 메인 패널에 컴포넌트 추가
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 창 설정
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    // 폼 확인 여부 반환
    public boolean isConfirmed() {
        return isConfirmed;
    }

    // 입력/수정된 회원 정보 반환
    public User getUser() {
        return user;
    }
}

// 회원 데이터 처리를 위한 DAO 클래스
class UserDAO {
    // 모든 회원 정보 조회
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Statement stmt = Main.conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 새 회원 추가
    public void addUser(User user) {
        String sql = "INSERT INTO user (name, id, pw, email, time, sign_in, gender, number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Main.conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getId());
            ps.setString(3, user.getPw());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getTime());
            ps.setString(6, user.getSignIn());
            ps.setString(7, user.getGender());
            ps.setString(8, user.getNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 회원 정보 수정
    public boolean modifyUser(String id, User user) {
        String sql = "UPDATE user SET name=?, pw=?, email=?, time=?, gender=?, number=? WHERE id=?";
        try (PreparedStatement ps = Main.conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPw());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTime());
            ps.setString(5, user.getGender());
            ps.setString(6, user.getNumber());
            ps.setString(7, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void deleteUser(String id) {
        try (PreparedStatement ps = Main.conn.prepareStatement("DELETE FROM user WHERE id=?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(String id) {
        String sql = "SELECT * FROM user WHERE id=?";
        try (PreparedStatement ps = Main.conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> searchUsers(String column, String keyword) {
        ArrayList<User> users = new ArrayList<>();
        String columnName;

        switch (column) {
            case "이름":
                columnName = "name";
                break;
            case "성별":
                columnName = "gender";
                break;
            case "아이디":
                columnName = "id";
                break;
            case "이메일":
                columnName = "email";
                break;
            case "남은시간":
                columnName = "time";
                break;
            case "가입일":
                columnName = "sign_in";
                break;
            case "전화번호":
                columnName = "number";
                break;
            default:
                throw new IllegalArgumentException("잘못된 검색 컬럼: " + column);
        }

        String sql = "SELECT * FROM user WHERE " + columnName + " LIKE ?";
        try (PreparedStatement ps = Main.conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean isIdExists(String id) {
        String sql = "SELECT COUNT(*) FROM user WHERE id = ?";
        try (PreparedStatement ps = Main.conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setGender(rs.getString("gender"));
        user.setId(rs.getString("id"));
        user.setPw(rs.getString("pw"));
        user.setEmail(rs.getString("email"));
        user.setTime(rs.getString("time"));
        user.setSignIn(rs.getString("sign_in"));
        user.setNumber(rs.getString("number"));
        return user;
    }
}

class User {
    private String name, gender, id, pw, email, time, signIn, number;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPw() { return pw; }
    public void setPw(String pw) { this.pw = pw; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getSignIn() { return signIn; }
    public void setSignIn(String signIn) { this.signIn = signIn; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
}
