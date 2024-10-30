import java.util.StringTokenizer;

public class StringTokenizerExam {

	public static void main(String[] args) {
		String s1 = "이름 김수종 성별 남자 나이 20";
		String s2 = "이름,천승용,성별,남자,나이,21";
		String s3 = "이름/김현탁/성별/남자/나이/26";
		String s4 = "이름:윤진환:성별:남자:나이:21";
		
		System.out.println("\n--------- 빈칸( )을 기준으로 ---------");
		StringTokenizer st1 = new StringTokenizer(s1); // 빈칸 기준(기본)
		while(st1.hasMoreTokens()) {
			String first = st1.nextToken();
			String second = st1.nextToken();
			System.out.println(first + "\t" + second);
		}
		
		System.out.println("\n--------- 쉼표(,)를 기준으로 ---------");
		StringTokenizer st2 = new StringTokenizer(s2, ","); // 쉼표 기준
		while(st2.hasMoreTokens()) {
			String first = st2.nextToken();
			String second = st2.nextToken();
			System.out.println(first + "\t" + second);
		}
		
		System.out.println("\n--------- 슬래시(/)를 기준으로 ---------");
		StringTokenizer st3 = new StringTokenizer(s3, "/"); // 슬래시 기준
		while(st3.hasMoreTokens()) {
			String first = st3.nextToken();
			String second = st3.nextToken();
			System.out.println(first + "\t" + second);
		}
		
		System.out.println("\n--------- 콜론(:)를 기준으로 ---------");
		StringTokenizer st4 = new StringTokenizer(s4, ":"); // 콜론 기준
		while(st4.hasMoreTokens()) {
			String first = st4.nextToken();
			String second = st4.nextToken();
			System.out.println(first + "\t" + second);
		}
	}
}
