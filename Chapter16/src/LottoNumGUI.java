import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Lotto extends JFrame implements ActionListener {
	private JLabel lotto_num = new JLabel();
	
	public Lotto() {
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		
		JButton lotto = new JButton("로또 번호 자동생성");
		
		ct.add(lotto);
		ct.add(lotto_num);
		
		lotto.addActionListener(this);
		
		setTitle("Lotto Number 생성기");
		setSize(300, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Random r = new Random();
		int[] lotto_number = new int[6];
		int temp; // 중복 생성 방지
		int i = 0;
		
		a: while(i < 6) {
			temp = r.nextInt(45)+1;
			for(int j = 0; j <= i; j++) {
				if(temp == lotto_number[j]) {
					continue a;
				}
			}
			lotto_number[i] = temp;
			i++;
		}
		Arrays.sort(lotto_number);
		lotto_num.setText("이번주 로또 번호 : " + Arrays.toString(lotto_number));
	}
}
public class LottoNumGUI {
	public static void main(String[] args) {
		new Lotto();
	}

}
