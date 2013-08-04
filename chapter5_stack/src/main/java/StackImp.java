import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/23/13
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class StackImp implements StackExercise{

    private String[] array=new String[1000];
    private int size = 0;
    StackImp(){}
    @Override
    public String pop() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot pop from empty stack");
        }
        String result = array[size-1];
        array[--size] = null;
        return result;
    }

    @Override
    public void push(String item) {
        if (size == array.length) {
            throw new IllegalStateException("Cannot add to full stack");
        }
        array[size++] = item;
    }

    @Override
    public String top() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot top into empty stack");
        }
        return array[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
          StackImp s=new StackImp();
         System.out.println(s.isEmpty());
    }
}
