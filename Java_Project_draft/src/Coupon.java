import java.util.Scanner;

// Payment 클래스(쿠폰 구매) - User 클래스를 상속 받음
class Payment extends User{
	//속성
	public static int money = 0;
	
	// cupon_option 메소드 - 쿠폰 구매의 메뉴 출력 기능
	// --> User 클래스의 메소드를 오버라이딩
    @Override
	public void cupon_option() {
    	System.out.println("\n<< 쿠폰 구매 >>");
    	System.out.println("1. 1,000원 (00:45)");
    	System.out.println("2. 2,000원 (01:30)");
    	System.out.println("3. 3,000원 (02:15)");
    	System.out.println("4. 5,000원 (04:30)");
    	System.out.println("5. 10,000원 (08:15)");
    	System.out.println("6. 20,000원 (15:45)");
    	System.out.println("7. 30,000원 (23:15)");
    	System.out.println("8. 50,000원 (38:15)");
    	System.out.println("0. 이전단계");
    	System.out.print("(입력 후 Enter) : ");
	}
	
    // makePayment 메소드 - 선택한 쿠폰에 따라 결제를 진행하는 기능
    public void makePayment() {
    	// Scanner 객체 생성
        Scanner stdIn = new Scanner(System.in);
        int choice = 9;
        
        while (choice != 0) {
        	int purchase = 0;
        	
        	cupon_option(); // 오버라이딩한 Payment 클래스의 메소드 호출
        	
        	// 입력 받기
            choice = stdIn.nextInt();

            switch (choice) {
                case 1: 
                	purchase = 1000;
                    break;
                case 2: 
                	purchase = 2000;
                    break;
                case 3: 
                	purchase = 3000;
                    break;
                case 4: 
                	purchase = 5000;
                    break;
                case 5: 
                	purchase = 10000;
                    break;
                case 6: 
                	purchase = 20000;
                    break;
                case 7: 
                	purchase = 30000;
                    break;
                case 8: 
                	purchase = 50000;
                    break;
                case 0:	// 0 입력
                    System.out.println("\n이전 화면으로 돌아갑니다.\n");
                    break;
                default:	// 범위를 벗어난 값
                    System.out.println("\n잘못된 선택입니다. 다시 선택해주세요.\n");
            }
            
            // 범위 내의 쿠폰을 선택했다면
            if (choice > 0 && choice < 9) {
            	System.out.printf("\n결제할 금액 : %,d원\n", purchase);
            	System.out.println("결제 수단을 선택해 주세요.");
            	System.out.println("1. 카드");
            	System.out.println("2. 현금");
            	System.out.println("3. 이체");
            	System.out.print("(입력 후 Enter) : ");
            	int num = stdIn.nextInt();
            	switch (num) {
	                case 1: 
	                	System.out.println("\n카드로 결제합니다.");
	                    break;
	                case 2: 
	                	System.out.println("\n현금으로 결제합니다.");
	                    break;
	                case 3: 
	                	System.out.println("\n이체로 결제합니다.");
	                    break;
	                case 0: 
	                	System.out.println("\n이전 화면으로 돌아갑니다.\n");
	                	break;
	                default:
	                    System.out.println("\n잘못된 선택입니다.\n");
            	}
            	
            	// 결제 진행 입력 받기
            	if (num >= 1 && num <= 3) {
            		System.out.print("결제를 진행하시겠습니까? (y/n): ");
            		char res = stdIn.next().charAt(0);
            		
            		// 결제 진행
            		if (res == 'y' || res == 'Y' ) {
            			money += purchase;
            			System.out.println("\n결제가 완료되었습니다!");
            		}
            		
            		// 결제 취소
            		else {
            			System.out.println("결제가 취소되었습니다.");
            			money = 0;
            		}
            	}
            }
        }
    }
        // pm_Payment 메소드(관리자 관련) --> 사용자가 결제 후 관리자에게 출력되는 기능
    	public void pm_Payment() {
            System.out.println("\n<< 쿠폰 매출 현황 >>");
            System.out.printf("총 판매 금액 : %,d원\n", money);
        }
}