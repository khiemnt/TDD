import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;

import java.awt.image.DataBufferUShort;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: khiemNT
 * Date: 8/4/13
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class SingleLinkedList {

   LinkedList<Node> linkedList=new LinkedList();

    public int size() {
        return linkedList.size();
    }

    public void insertFirst(Object o) {
       Node node=new Node(o);
       linkedList.addFirst(node);
    }

    public Node first() {
        return linkedList.get(0);
    }

    public Node last() {
        return linkedList.get(linkedList.size()-1);
    }

    public Node before(Node node) {
       for(int  i=1;i<size();i++){
           if(linkedList.get(i)==node){
               return linkedList.get(i-1);
           }
       }
        return null;
    }

    public Node find(Object o1) {
        for(int  i=1;i<size();i++){
            if(linkedList.get(i).getData().equals(o1)){
                return linkedList.get(i);
            }
        }
        return null;
    }

    public void delete(Node n) {
        linkedList.remove(n);
    }

    public Node after(Node node) {
        for(int  i=0;i<size();i++){
            if(linkedList.get(i)==node){
                return linkedList.get(i+1);
            }
        }
        return null;
    }

    public void append(Object o3) {
        Node lastNode=new Node(o3);
        linkedList.add(size(),lastNode);
    }
}
