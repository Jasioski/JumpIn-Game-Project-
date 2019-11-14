package project;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import project.model.BoardItem;
import project.model.Coordinate;
import project.model.Rabbit;

import java.util.ArrayList;
import java.util.List;

public class PersistentDataExample {

    public static <T> void PrintList(List<T> list) {
        for (T inte: list) {
            System.out.println(inte);
        }
    }


    public static void main(String[] args) {
        ArrayList<Integer> numbers1 = new ArrayList();
        numbers1.add(0);

        System.out.println("Initial List");


        PrintList(numbers1);
        ///class happened here
        // in some method somewhere i want to add a number to numbers1
        numbers1.add(2);

        System.out.println("List after addition");

        PrintList(numbers1);

        PVector<Integer> numbers2 = TreePVector.empty();
        PVector<Integer> numbers3= numbers2.plus(0);

        System.out.println("Print persistent data");
        PrintList(numbers2);
        PrintList(numbers3);

        PVector<BoardItem> itemsList = TreePVector.empty();
        Rabbit item = new Rabbit(0,0);
        PrintList(itemsList);

        PVector<BoardItem> itemList2 = itemsList.plus(item);
        PrintList(itemList2);

        // if we change the coordinates of item
        item.setCoordinate(new Coordinate(0, 1));

        PrintList(itemList2);

    }
}