import java.util.Random;
import java.util.TreeSet;
public class TreeSetLotto {
	public static void main(String[] args) {
		TreeSet<Integer> LottoNum = new TreeSet<Integer>();
		Random r = new Random();
		while(LottoNum.size() < 6) LottoNum.add(r.nextInt(45)+1);
		System.out.println("로또 번호 : " + LottoNum);
	}

}
