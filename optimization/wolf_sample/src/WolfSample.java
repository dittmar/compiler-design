import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;
public class WolfSample {

    public static Integer sumList(ArrayList<Integer> l) {
        if (l.size() == 0) {
            return 0;
        } else {
            ArrayList<Integer> tail1 = new ArrayList<>(l.subList(1, l.size()));
            return l.get(0) + sumList(tail1);
        }
    }
    
    public static Integer prodList(ArrayList<Integer> argParty) {
        if (argParty.size() == 0) {
            return 1;
        } else {
            ArrayList<Integer> tail1 = new ArrayList<>(argParty.subList(1, argParty.size()));
            return argParty.get(0) * prodList(tail1);
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
        BiFunction<String, String,String> bi = (x, y) -> {      
            return x + y;
        };

        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        Collections.reverse(list1);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(5);
        list2.add(7);
        list2.add(2);
        ArrayList<Integer> list3 = new ArrayList<>();
        list3.add(0);
        list3.add(127);
        list3.add(9);
        ArrayList<Integer> arg1 = new ArrayList<>();
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
