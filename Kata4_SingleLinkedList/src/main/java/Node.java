/**
 * Created with IntelliJ IDEA.
 * User: khiemNT
 * Date: 8/4/13
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    Object data;

    public Node(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (data != null ? !data.equals(node.data) : node.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
