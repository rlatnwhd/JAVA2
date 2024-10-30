import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeTest1 {

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		LocalDateTime dateTime = LocalDateTime.now();
		
		System.out.println("현재 날짜 : " + date);
		System.out.println("현재 시간 : " + time);
		System.out.println("현재 시각 : " + dateTime);

		String s = dateTime.getYear() + "년";
		s += dateTime.getMonthValue() + "월";
		s += dateTime.getDayOfMonth() + "일 ";
		s += dateTime.getDayOfWeek() + " ";
		s += dateTime.getHour() + "시";
		s += dateTime.getMinute() + "분";
		s += dateTime.getSecond() + "초";
		
		System.out.println("현재 시각 : " + s);
		
		System.out.println("오늘부터 100일 기념일 : " + date.plusDays(100));
		System.out.println("오늘부터 10주 다이어트 : " + date.plusWeeks(10));
		System.out.println("오늘부터 졸업까지 2년  : " + date.plusYears(2));
	}

}
