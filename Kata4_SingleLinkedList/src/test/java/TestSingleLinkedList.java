import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: khiemNT
 * Date: 8/4/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestSingleLinkedList {

    @Test
    public void testEmptyList() {
        SingleLinkedList s = new SingleLinkedList();
        assertEquals(0, s.size());
    }

    @Test
    public void testInsertFirst() {
        SingleLinkedList s = new SingleLinkedList();
        s.insertFirst(new Integer(2));
        assertEquals(1, s.size());

    }

    @Test
    public void testReturnFirstNodeOfList() {
        SingleLinkedList s = new SingleLinkedList();
        s.insertFirst(new Integer(2));
        assertEquals(2, s.first().getData());
    }

    @Test
    public void testReturnLastNodeOfList() {
        SingleLinkedList s = new SingleLinkedList();
        s.insertFirst(new Integer(2));
        s.insertFirst(new Integer(3));
        assertEquals(2, s.last().getData());
    }

    @Test
    public void testGetNodeThatBeforeHeadNode() {
        SingleLinkedList s = new SingleLinkedList();
        Object o1 = new Integer(1);
        Object o2 = new Integer(2);
        s.insertFirst(o1);
        s.insertFirst(o2);

        Node firstNode=s.first();
        assertEquals(null, s.before(firstNode));
    }
    @Test
    public void testBeforeNode(){
        SingleLinkedList s = new SingleLinkedList();
        Object o1 = new Integer(1);
        Object o2 = new Integer(2);
        s.insertFirst(o1);
        s.insertFirst(o2);

        Node lastNode=s.last();
        assertEquals(2,s.before(lastNode).getData());
    }
    @Test
    public void testBeforeNoteThatNotExist(){
        SingleLinkedList s = new SingleLinkedList();
        Object o1 = new Integer(1);
        Object o2 = new Integer(2);
        s.insertFirst(o1);
        s.insertFirst(o2);

        Node node= new Node(5);
        assertEquals(null,s.before(node));
    }

    @Test
    public void testFindNode(){
        SingleLinkedList s = new SingleLinkedList();
        Object o1 = new Integer(1);
        Object o2 = new Integer(2);
        s.insertFirst(o1);
        s.insertFirst(o2);

        Node node=new Node(new Integer(1));
        assertEquals(node,s.find(o1));
    }
    @Test
    public void testDeleteNode(){
        SingleLinkedList s = new SingleLinkedList();
        Object o1 = new Integer(5);
        Object o2 = new Integer(2);
        s.insertFirst(o1);
        s.insertFirst(o2);

       Node n=s.first();
       s.delete(n);
       assertEquals(1,s.size());
       assertEquals(5,s.first().getData());
    }

    @Test
    public void testGetAfterNode(){
        SingleLinkedList s = new SingleLinkedList();
        Object o1 = new Integer(5);
        Object o2 = new Integer(2);
        s.insertFirst(o1);
        s.insertFirst(o2);

        Node nodeFist=s.first();
        assertEquals(5,s.after(nodeFist).getData());
    }
    @Test
    public void testAppendNode(){
        SingleLinkedList s = new SingleLinkedList();
        Object o1 = new Integer(5);
        Object o2 = new Integer(2);
        s.insertFirst(o1);
        s.insertFirst(o2);
        Object o3 = new Integer(4);
        s.append(o3);

        assertEquals(o3,s.last().getData());
    }
}
