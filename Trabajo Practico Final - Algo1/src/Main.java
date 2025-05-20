//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidShape;

public class Main {
    public static void main(String[] args) throws InvalidShape {
        List<List<Object>> lista = new ArrayList<>();

        // Creating and adding lists to the main list
        List<Object> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List<Object> list2 = new ArrayList<>();
        list2.add(4);
        list2.add(5);
        list2.add(6);

        List<Object> list3 = new ArrayList<>();
        list3.add(7);
        list3.add(8);
        list3.add(9);

        // Adding individual lists to the main list
        lista.add(list1);
        lista.add(list2);
        lista.add(list3);

        //Lista de labels
        List<Object> labels = new ArrayList<>();
        labels.add("A");
        labels.add("B");
        labels.add("C");

        DataFrame df = new DataFrame(lista, labels, labels);
        df.head();
    }
}