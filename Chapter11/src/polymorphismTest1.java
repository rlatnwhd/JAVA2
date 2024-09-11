class Am {
	int count=1; // 속성
	void callme() { // 메소드
		System.out.println("Am의 callme() 실행, count 값 : " + count);
	}
}

class Bm extends Am{
	int count=2; // 속성 count=2, 상속된 상위 클래스의 속성은 가려짐
	void callme() { // 메소드 오버라이딩
		System.out.println("Bm의 callme() 실행, count 값 : " + count);
	}
}

class Cm extends Am {
	int count=3; // 속성 count=3, 상속된 상위 클래스의 속성은 가려짐
	void callme() { // 메소드
		System.out.println("Cm의 callme() 실행, count 값 : " + count);
	}
}
public class polymorphismTest1 {

	public static void main(String[] args) {
		Am r = new Am();
		r.callme();
		System.out.println("r.count 값 : " + r.count);
		
		r = new Bm(); // 형변환, 상위 클래스 형 객체 변수에 하위 클래스 객체 배정
		r.callme(); // Bm에 있는 callme() 호출
		System.out.println("r.count 값 : " + r.count); // 상위 클래스의 속성을 호출
		
		/*
		 * 형변환의 경우 메소드는 오버라이딩된 결과를 적용할 수 있다.
		 * 즉, 하위 클래스의 메소드에 접근이 가능하다.
		 * 형변환의 경우 속성(변수)는 오버라이딩 되지 않는다.
		 * 따라서 상위 클래스의 속성에 접근한다.
		 */
		
		
		
		r = new Cm(); // 형변환, 상위 클래스 형 객체 변수에 하위 클래스 객체 배정
		r.callme(); // Cm에 있는 callme() 호출
		System.out.println("r.count 값 : " + r.count); // 상위 클래스의 속성을 호출
	}

}
