import java.util.ArrayDeque;
public class WolfSample {

    public static Integer sumList(ArrayDeque<Integer> l) {
        if (l.size() == 0) {
            return 0;
        } else {
            ArrayDeque<Integer> tail1 = l.clone();
            tail1.removeFirst();
            return l.getFirst() + sumList(tail1);
        }
    }
    
    public static Integer prodList(ArrayDeque<Integer> argParty) {
        if (argParty.size() == 0) {
            return 1;
        } else {
            ArrayDeque<Integer> tail1 = argParty.clone();
            tail1.removeFirst();
            return argParty.getFirst() * prodList(tail1);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayDeque<Integer> list1 = new ArrayDeque<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        ArrayDeque<Integer> list2 = new ArrayDeque<>();
        list2.add(5);
        list2.add(7);
        list2.add(2);
        ArrayDeque<Integer> list3 = new ArrayDeque<>();
        list3.add(0);
        list3.add(127);
        list3.add(9);
        ArrayDeque<Integer> arg1 = new ArrayDeque<>();
        arg1.add(prodList(list1));
        arg1.add(prodList(list2));
        arg1.add(prodList(list3));
        System.out.println(sumList(arg1));
    }
}
