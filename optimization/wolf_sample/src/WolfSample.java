import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
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
 
    @FunctionalInterface
    public static interface Lambda1{
        Integer someMethod(Integer y, Integer z);
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
        Lambda_1 lambda1 = WolfSample::lambda1;
        Integer lambda1_result = lambda1.lambda1(5, 12, 0);
    }
    /*In main function:
        Lambda_<number> lambda<number> = <class_name>::lambda_<number>;
        <return type> lambda<number>_result = lambda<number>.lambda<number>(arg_list);
     */
    /*Outside main function in class:
        private static <return type> lambda<number>(<signature>) {
            <lambda_function>
        }
        @FunctionalInterface
        public static interface Lambda_<number> {
            <return type> lambda<number>(<signature>)
        }
    */
    private static Integer lambda1(Integer w, Integer y, Integer z) {
        return w + y * z;
    }

    @FunctionalInterface
    public static interface Lambda_1 {
        Integer lambda1(Integer w, Integer y, Integer z);
    }
}
