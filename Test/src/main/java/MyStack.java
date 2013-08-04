import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/13
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyStack {
    List<String> stack = new ArrayList<String>();

    public int size() {
        return stack.size();
    }

    public void push(String s) {
        if (s != null) {
            stack.add(s);
        }
    }

    public String top() {
        if (size() > 0) {
            return stack.get(size() - 1);
        } else
            return null;
    }

    public String pop() throws StackEmptyException {
        if (size() <= 0) {
            throw new StackEmptyException("Empty stack");
        } else {
            String result = stack.get(size() - 1);
            stack.remove(size() - 1);
            return result;
        }

    }
}
