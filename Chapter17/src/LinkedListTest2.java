import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedListTest2 {

    public static void main(String[] args) {
        ArrayList<Integer> alist = new ArrayList<Integer>();
        LinkedList<Integer> llist = new LinkedList<Integer>();
        
        long start, stop;
        
        // ArrayList를 순차적으로 삽입 연산을 할 때 걸리는 시간
        start = System.currentTimeMillis();
        for (int i = 0; i <= 1000000; i++) {
            alist.add(i);
        }
        stop = System.currentTimeMillis();
        System.out.println("ArrayList - 순차적 삽입 : " + (stop - start) + "ms");
        
        // LinkedList를 순차적으로 삽입 연산을 할 때 걸리는 시간
        start = System.currentTimeMillis();
        for (int i = 0; i <= 1000000; i++) {
            llist.add(i);
        }
        stop = System.currentTimeMillis();
        System.out.println("LinkedList - 순차적 삽입 : " + (stop - start) + "ms");
        
        // ArrayList를 중간에 삽입할 때 걸리는 시간
        start = System.currentTimeMillis();
        for (int i = 0; i <= 10000; i++) {
            alist.add(500, i);
        }
        stop = System.currentTimeMillis();
        System.out.println("ArrayList - 중간 삽입 : " + (stop - start) + "ms");
        
        // LinkedList를 중간에 삽입할 때 걸리는 시간
        start = System.currentTimeMillis();
        for (int i = 0; i <= 10000; i++) {
            llist.add(500, i);
        }
        stop = System.currentTimeMillis();
        System.out.println("LinkedList - 중간 삽입 : " + (stop - start) + "ms");
    }
}
