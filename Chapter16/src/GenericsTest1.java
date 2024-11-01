class Box<T> { // 제네릭 클래스 선언
	T vol; // 변수를 제네릭으로 선언
	
	void setVolume(T v) { // 메소드의 매개변수를 제네릭으로 선언
		vol = v;
	}
	
	T getVolume() { // 반환값의 형을 제네릭으로 선언
		return vol;
	}
}

public class GenericsTest1 {
	public static void main(String[] args) {
		Box<Integer> ibox = new Box<Integer>(); // 제네릭의 형을 지정하여 객체 생성(정수)
		ibox.setVolume(200);
		//ibox.setVolume(32.3);                    자료형이 다르므로 오류 발생
		System.out.println("<Integer>박스의 부피 : " + ibox.getVolume());
		
		Box<Double> dbox = new Box<Double>(); // 제네릭의 형을 지정하여 객체 생성(실수)
		dbox.setVolume(123.456);
		//dbox.setVolume(300)                      자료형이 다르므로 오류 발생
		System.out.println("<Double>박스의 부피 : " + dbox.getVolume());
	}
}
