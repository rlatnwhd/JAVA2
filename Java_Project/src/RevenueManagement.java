import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.Connection;

public class RevenueManagement extends JFrame implements ActionListener {
    private JTable salesTable;
    private JTextField searchField;
    private JComboBox<String> comboBox;
    private DefaultTableModel model;
    private JButton searchButton, deleteButton;
    private JButton todayButton, weekButton, monthButton, yearButton;  // 시간 필터 버튼들 필드로 선언
    private JButton cancelButton;  // 취소 버튼 추가
    private RevenueDAO dao;
    private JLabel totalCountLabel;
    private JLabel totalAmountLabel;
    private Connection conn;  // Connection 필드 추가

    public RevenueManagement() {
        dao = new RevenueDAO();

        // DB 연결 초기화
        try {
            String jdbcDriver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/getoUserList?serverTimezone=UTC";
            String id = "root";
            String pw = "1234";
            
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 메인 컨테이너
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 10));

        // 검색 패널 (전체)
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        
        // 상단 검색 패널
        JPanel upperSearchPanel = new JPanel(new BorderLayout(10, 10));
        comboBox = new JComboBox<>(new String[]{"이름", "아이디", "쿠폰종류", "구매시각"});
        searchField = new JTextField(20);
        searchButton = new JButton("검색");
        
        upperSearchPanel.add(comboBox, BorderLayout.WEST);
        upperSearchPanel.add(searchField, BorderLayout.CENTER);
        upperSearchPanel.add(searchButton, BorderLayout.EAST);

        // 검색 패널에 추가
        searchPanel.add(upperSearchPanel, BorderLayout.CENTER);

        // 필터 패널 (시간 필터 + 총 개수)
        JPanel filterPanel = new JPanel(new BorderLayout(10, 10));
        
        // 왼쪽 시간 필터 버튼들
        JPanel timeFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        todayButton = new JButton("오늘 하루");
        weekButton = new JButton("지난 7일");
        monthButton = new JButton("지난 30일");
        yearButton = new JButton("지난 1년");
        
        timeFilterPanel.add(todayButton);
        timeFilterPanel.add(weekButton);
        timeFilterPanel.add(monthButton);
        timeFilterPanel.add(yearButton);

        // 오른쪽 총계 패널
        JPanel countPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        totalCountLabel = new JLabel("총 쿠폰 개수: 0개");
        totalAmountLabel = new JLabel("총 판매 금액: 0원");
        totalCountLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        totalAmountLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        countPanel.add(totalCountLabel);
        countPanel.add(totalAmountLabel);

        filterPanel.add(timeFilterPanel, BorderLayout.WEST);
        filterPanel.add(countPanel, BorderLayout.EAST);

        // 필터 패널을 검색 패널 아래에 추가
        searchPanel.add(filterPanel, BorderLayout.SOUTH);

        // 테이블 설정
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        model.addColumn("이름");
        model.addColumn("아이디");
        model.addColumn("쿠폰종류");
        model.addColumn("구매시각");
        
        salesTable = new JTable(model);
        salesTable.getTableHeader().setReorderingAllowed(false);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        deleteButton = new JButton("쿠폰 삭제");
        cancelButton = new JButton("취소");  // 취소 버튼 생성
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);       // 취소 버튼 추가

        // 테이블 스크롤
        JScrollPane tableScroll = new JScrollPane(salesTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // 컴포넌트 추가
        ct.add(searchPanel, BorderLayout.NORTH);
        ct.add(tableScroll, BorderLayout.CENTER);
        ct.add(buttonPanel, BorderLayout.SOUTH);

        // 버튼들에 액션리스너 등록
        searchButton.addActionListener(this);
        deleteButton.addActionListener(this);
        todayButton.addActionListener(this);
        weekButton.addActionListener(this);
        monthButton.addActionListener(this);
        yearButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // 초기 데이터 로드 및 카운트 업데이트
        loadTableData();
        updateTotalStats();

        // 창 설정
        setTitle("쿠폰 판매 현황");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ActionListener 인터페이스의 메소드 구현
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        try {
            if (source == searchButton) {
                searchData();
            } else if (source == todayButton) {
                filterByTime("TODAY");
            } else if (source == weekButton) {
                filterByTime("WEEK");
            } else if (source == monthButton) {
                filterByTime("MONTH");
            } else if (source == yearButton) {
                filterByTime("YEAR");
            } else if (source == deleteButton) {
                deleteSelectedSale();
            } else if (source == cancelButton) {
                dispose();
                new Manager_Menu_GUI();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "작업 처리 중 오류가 발생했습니다: " + ex.getMessage(),
                "오류",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // 테이블 데이터 로드
    private void loadTableData() {
        model.setRowCount(0);  // 테이블 초기화
        ArrayList<Revenue> list = dao.getAllSales();  // 모든 데이터 가져오기
        for(Revenue rev : list) {
            model.addRow(new Object[]{
                rev.getName(),
                rev.getId(),
                rev.getCoupon(),
                rev.getTime()
            });
        }
    }

    // 검색 기능
    private void searchData() {
        String condition = comboBox.getSelectedItem().toString();
        String keyword = searchField.getText();
        
        switch(condition) {
            case "이름":
            	condition = "NAME";
            	break;
            case "아이디":
            	condition = "ID";
            	break;
            case "쿠폰종류":
            	condition = "COUPON";
            	break;
            case "구매시각":
            	condition = "TIME";
            	break;
        }

        ArrayList<Revenue> list = dao.searchSales(condition, keyword);
        updateTable(list);
    }

    // 시간 필터링 메소드
    private void filterByTime(String period) {
        ArrayList<Revenue> list = dao.getFilteredByTime(period);
        updateTable(list);
    }

    // 테이블 업데이트 메소드
    private void updateTable(ArrayList<Revenue> list) {
        model.setRowCount(0);
        for(Revenue rev : list) {
            model.addRow(new Object[]{
                rev.getName(),
                rev.getId(),
                rev.getCoupon(),
                rev.getTime()
            });
        }
        updateTotalStats();
    }

    // 총계 업데이트 메소드
    private void updateTotalStats() {
        int count = model.getRowCount();
        int totalAmount = 0;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String couponType = (String) model.getValueAt(i, 2);
            totalAmount += getCouponAmount(couponType);
        }
        
        totalCountLabel.setText("총 쿠폰 개수: " + count + "개");
        totalAmountLabel.setText("총 판매 금액: " + String.format("%,d", totalAmount) + "원");
    }

    // 쿠폰 금액 계산 메소드
    private int getCouponAmount(String couponType) {
        try {
            // 쿠폰 문자열을 공백이나 특수문자로 분리
            String[] parts = couponType.split("\\s+|/");
            // 첫 번째 부분에서 숫자만 추출
            String amountStr = parts[0].replaceAll("[^0-9]", "");
            return Integer.parseInt(amountStr);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 삭제 기능 구현
    private void deleteSelectedSale() {
        int selectedRow = salesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 항목을 선택해주세요.");
            return;
        }

        try {
            // DB 연결 재확인
            Main.reconnectDB();
            
            String userName = (String) salesTable.getValueAt(selectedRow, 0);
            String userId = (String) salesTable.getValueAt(selectedRow, 1);
            String couponInfo = (String) salesTable.getValueAt(selectedRow, 2);
            String purchaseTime = (String) salesTable.getValueAt(selectedRow, 3);

            // 쿠폰 시간 추출
            String timeStr = couponInfo.substring(couponInfo.indexOf("(") + 1, couponInfo.indexOf(")"));

            int confirm = JOptionPane.showConfirmDialog(this,
                "선택한 쿠폰을 삭제하시겠습니까?\n사용자의 남은 시간에서 해당 시간이 차감됩니다.",
                "쿠폰 삭제 확인",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Main.conn.setAutoCommit(false);  // 트랜잭션 시작

                // 비회원인 경우 특별 처리
                if (userName.equals("비회원")) {
                    // 1. PC 사용중인지 확인
                    String checkSeatSQL = "SELECT * FROM seatstatus WHERE user_id = ?";
                    PreparedStatement checkStmt = Main.conn.prepareStatement(checkSeatSQL);
                    checkStmt.setString(1, userId);
                    ResultSet seatRs = checkStmt.executeQuery();

                    if (seatRs.next()) {
                        // PC 사용중이면 강제 종료
                        String deleteSeatSQL = "DELETE FROM seatstatus WHERE user_id = ?";
                        PreparedStatement deleteSeatStmt = Main.conn.prepareStatement(deleteSeatSQL);
                        deleteSeatStmt.setString(1, userId);
                        deleteSeatStmt.executeUpdate();
                    }

                    // 2. salesstatus 테이블에서 쿠폰 삭제
                    String deleteSaleSQL = "DELETE FROM salesstatus WHERE ID = ? AND COUPON = ? AND TIME = ? LIMIT 1";
                    PreparedStatement deleteSaleStmt = Main.conn.prepareStatement(deleteSaleSQL);
                    deleteSaleStmt.setString(1, userId);
                    deleteSaleStmt.setString(2, couponInfo);
                    deleteSaleStmt.setString(3, purchaseTime);
                    deleteSaleStmt.executeUpdate();

                    Main.conn.commit();
                    JOptionPane.showMessageDialog(this, "쿠폰이 성공적으로 삭제되었습니다.");
                    loadTableData();
                    return;
                }

                // 기존 회원 처리 코드
                // 1. 현재 사용자의 시간 조회
                String getCurrentTimeSql = "SELECT TIME FROM user WHERE ID = ?";
                PreparedStatement getCurrentTimeStmt = Main.conn.prepareStatement(getCurrentTimeSql);
                getCurrentTimeStmt.setString(1, userId);
                ResultSet rs = getCurrentTimeStmt.executeQuery();
                
                if (rs.next()) {
                    String currentTime = rs.getString("TIME");
                    String newTime = calculateNewTime(currentTime, timeStr);
                    
                    // 2. user 테이블의 시간 업데이트
                    String updateUserSql = "UPDATE user SET TIME = ? WHERE ID = ?";
                    PreparedStatement updateUserStmt = Main.conn.prepareStatement(updateUserSql);
                    updateUserStmt.setString(1, newTime);
                    updateUserStmt.setString(2, userId);
                    updateUserStmt.executeUpdate();
                    
                    // 3. seatstatus 테이블 업데이트
                    String updateSeatSql = "UPDATE seatstatus SET remaining_time = ? WHERE user_id = ?";
                    PreparedStatement updateSeatStmt = Main.conn.prepareStatement(updateSeatSql);
                    updateSeatStmt.setString(1, newTime);
                    updateSeatStmt.setString(2, userId);
                    updateSeatStmt.executeUpdate();
                    
                    // 4. salesstatus 테이블에서 해당 쿠폰 삭제
                    String deleteSql = "DELETE FROM salesstatus WHERE ID = ? AND COUPON = ? AND TIME = ? LIMIT 1";
                    PreparedStatement deleteStmt = Main.conn.prepareStatement(deleteSql);
                    deleteStmt.setString(1, userId);
                    deleteStmt.setString(2, couponInfo);
                    deleteStmt.setString(3, purchaseTime);
                    deleteStmt.executeUpdate();
                    
                    Main.conn.commit();
                    JOptionPane.showMessageDialog(this, "쿠폰이 성공적으로 삭제되었습니다.");
                    
                    // Using_GUI가 열려있다면 시간 업데이트
                    if (Using_GUI.instance != null) {
                        Using_GUI.instance.refreshTime();
                    }
                    
                    // 테이블 및 통계 새로고침
                    loadTableData();
                    updateTotalStats();
                }
            }
        } catch (Exception ex) {
            try {
                Main.conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "오류가 발생했습니다: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // 시간 계산 메소드 개선
    private String calculateNewTime(String currentTime, String subtractTime) {
        try {
            String[] currentParts = currentTime.split(":");
            String[] subtractParts = subtractTime.split(":");
            
            int currentMinutes = Integer.parseInt(currentParts[0]) * 60 + Integer.parseInt(currentParts[1]);
            int subtractMinutes = Integer.parseInt(subtractParts[0]) * 60 + Integer.parseInt(subtractParts[1]);
            
            int resultMinutes = Math.max(currentMinutes - subtractMinutes, 0);
            
            int hours = resultMinutes / 60;
            int minutes = resultMinutes % 60;
            
            return String.format("%02d:%02d", hours, minutes);
        } catch (Exception e) {
            e.printStackTrace();
            return "00:00"; // 오류 발생 시 기본값 반환
        }
    }

    // 쿠폰 문자열에서 시간 추출 메소드
    public String extractTimeFromCoupon(String couponType) {
        try {
            // 괄호 안의 시간 형식 추출 (예: "50,000원 (40:00)" -> "40:00")
            String timeStr = couponType.substring(couponType.indexOf("(") + 1, couponType.indexOf(")"));
            return timeStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "00:00";
        }
    }
}

class RevenueDAO {
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    // DB 연결
    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Main.conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/getouserlist?",
                "root",
                "1234"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // DB 연결 해제
    private void disconnect() {
        try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(Main.conn != null) Main.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 쿠폰 판매 데이터 추가
    public boolean insertSale(Revenue revenue) {
        connect();
        String sql = "INSERT INTO salesStatus (NAME, ID, COUPON, TIME) VALUES (?, ?, ?, ?)";
        try {
            pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, revenue.getName());
            pstmt.setString(2, revenue.getId());
            pstmt.setString(3, revenue.getCoupon());
            pstmt.setString(4, revenue.getTime());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }
    
    // 검색 조건에 따른 데이터 조회
    public ArrayList<Revenue> searchSales(String condition, String keyword) {
        ArrayList<Revenue> list = new ArrayList<>();
        connect();
        String sql = "SELECT * FROM salesStatus WHERE " + condition + " LIKE ?";
        try {
            pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Revenue revenue = new Revenue();
                revenue.setName(rs.getString("NAME"));
                revenue.setId(rs.getString("ID"));
                revenue.setCoupon(rs.getString("COUPON"));
                revenue.setTime(rs.getString("TIME"));
                list.add(revenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return list;
    }
    
    // 모든 판매 데이터 조회
    public ArrayList<Revenue> getAllSales() {
        ArrayList<Revenue> list = new ArrayList<>();
        connect();
        String sql = "SELECT * FROM salesStatus";
        try {
            pstmt = Main.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Revenue revenue = new Revenue();
                revenue.setName(rs.getString("NAME"));
                revenue.setId(rs.getString("ID"));
                revenue.setCoupon(rs.getString("COUPON"));
                revenue.setTime(rs.getString("TIME"));
                list.add(revenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return list;
    }

    // 시간 필터링된 데이터 조회
    public ArrayList<Revenue> getFilteredByTime(String period) {
        ArrayList<Revenue> list = new ArrayList<>();
        connect();
        String sql = "";
        
        switch(period) {
            case "HOUR":
                sql = "SELECT * FROM salesStatus WHERE TIME >= DATE_SUB(NOW(), INTERVAL 1 HOUR)";
                break;
            case "TODAY":
                sql = "SELECT * FROM salesStatus WHERE DATE(TIME) = CURDATE()";
                break;
            case "WEEK":
                sql = "SELECT * FROM salesStatus WHERE TIME >= DATE_SUB(NOW(), INTERVAL 7 DAY)";
                break;
            case "MONTH":
                sql = "SELECT * FROM salesStatus WHERE TIME >= DATE_SUB(NOW(), INTERVAL 30 DAY)";
                break;
            case "YEAR":  // 1년 조건 추가
                sql = "SELECT * FROM salesStatus WHERE TIME >= DATE_SUB(NOW(), INTERVAL 1 YEAR)";
                break;
            default:
                sql = "SELECT * FROM salesStatus";
        }
        
        try {
            pstmt = Main.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Revenue revenue = new Revenue();
                revenue.setName(rs.getString("NAME"));
                revenue.setId(rs.getString("ID"));
                revenue.setCoupon(rs.getString("COUPON"));
                revenue.setTime(rs.getString("TIME"));
                list.add(revenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return list;
    }

    // 삭제 메소드 추가
    public boolean deleteSale(String id, String time) {
        connect();
        String sql = "DELETE FROM salesStatus WHERE ID = ? AND TIME = ? LIMIT 1";  // LIMIT 1 추가
        try {
            pstmt = Main.conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, time);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    // 회원의 남은 시간 업데이트 메소드
    public boolean updateUserTime(String id, String couponTime, boolean isSubtract) {
        connect();
        try {
            // 현재 사용자의 남은 시간 조회
            String selectSql = "SELECT TIME FROM user WHERE ID = ?";
            pstmt = Main.conn.prepareStatement(selectSql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String currentTime = rs.getString("TIME");
                
                // 시간 계산
                String newTime = calculateTime(currentTime, couponTime, isSubtract);
                
                // 새로운 시간으로 업데이트
                String updateSql = "UPDATE user SET TIME = ? WHERE ID = ?";
                pstmt = Main.conn.prepareStatement(updateSql);
                pstmt.setString(1, newTime);
                pstmt.setString(2, id);
                return pstmt.executeUpdate() > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    // HH:mm 형식의 시간 계산 메소드
    private String calculateTime(String currentTime, String couponTime, boolean isSubtract) {
        try {
            // HH:mm 형식의 시간을 분으로 변환
            String[] current = currentTime.split(":");
            int currentMinutes = Integer.parseInt(current[0]) * 60 + Integer.parseInt(current[1]);
            
            String[] coupon = couponTime.split(":");
            int couponMinutes = Integer.parseInt(coupon[0]) * 60 + Integer.parseInt(coupon[1]);
            
            // 시간 계산 (차감)
            int resultMinutes;
            if (isSubtract) {
                resultMinutes = currentMinutes - couponMinutes;
            } else {
                resultMinutes = currentMinutes + couponMinutes;
            }
            
            // 음수가 되지 않도록 처리
            if (resultMinutes < 0) resultMinutes = 0;
            
            // 결과를 다시 HH:mm 형식으로 변환
            int hours = resultMinutes / 60;
            int minutes = resultMinutes % 60;
            
            return String.format("%02d:%02d", hours, minutes);
        } catch (Exception e) {
            e.printStackTrace();
            return "00:00";
        }
    }

    // 쿠폰 문자열에서 시간 추출 메소드
    public String extractTimeFromCoupon(String couponType) {
        try {
            // 괄호 안의 시간 형식 추출 (예: "50,000원 (40:00)" -> "40:00")
            String timeStr = couponType.substring(couponType.indexOf("(") + 1, couponType.indexOf(")"));
            return timeStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "00:00";
        }
    }
}

class Revenue {
    private String name;
    private String id;
    private String coupon;
    private String time;
    
    // Getter Setter 메소드들
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCoupon() { return coupon; }
    public void setCoupon(String coupon) { this.coupon = coupon; }
    
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}