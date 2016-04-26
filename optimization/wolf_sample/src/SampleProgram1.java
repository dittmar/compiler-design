import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
public class SampleProgram1 {
public static Integer sumList(List<Integer> l){
ArrayList<Integer>temp0 = new ArrayList<>(l);
return (l.size() == 0)
? 0
: l.get(0) + sumList(temp0.subList(1, temp0.size()));
}
public static Integer prodList(List<Integer> argParty){
ArrayList<Integer>temp1 = new ArrayList<>(argParty);
return (argParty.size() == 0)
? 1
: argParty.get(0) * prodList(temp1.subList(1, temp1.size()));
}
public static void main(String[] args) {
ArrayList<Integer> list0 = new ArrayList<>();
ArrayList<Integer> list1 = new ArrayList<>();
list1.add(1);
list1.add(2);
list1.add(3);
list0.add(prodList(list1));
ArrayList<Integer> list2 = new ArrayList<>();
list2.add(5);
list2.add(7);
list2.add(2);
list0.add(prodList(list2));
ArrayList<Integer> list3 = new ArrayList<>();
list3.add(0);
list3.add(127);
list3.add(9);
list0.add(prodList(list3));
System.out.println(sumList(list0));
}
}
