abstract class Shape {
	// 추상 메소드 선언 - 매개변수 X
	abstract void draw();
	// 추상 메소드 선언 - 매개변수 O
	abstract void computerArea(double a, double b);
}

class Rectangle1 extends Shape {
	// 메소드 오버라이딩 - 구체적으로 표현
	void draw() {
		System.out.println("사각형을 그리는 기능");
	}
	// 메소드 오버라이딩 - 구체적으로 표현
	void computerArea(double h, double v) {
		System.out.println("사각형의 넓이 : " + (h * v));
	}
}

public class AbstractTest1 {

	public static void main(String[] args) {
		// 상위 클래스 형으로 s 객체 변수를 만들어 하위 클래스
		Shape s = new Rectangle1();
		s.draw(); // 사각형을 그리는 기능 출력
		s.computerArea(3.0, 2.5); // 사각형의 넓이를 구하는 기능 출력
	}

}
