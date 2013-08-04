import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/13
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestStack {
    MyStack stack=new MyStack();
    @Test
    public void testEmptyStack(){
        assertEquals(0,stack.size());
    }
    @Test
    public void testPushAnElementToEmptyStack(){
       stack.push("a1");
       assertEquals(1,stack.size());
    }
    @Test
    public void testPushManyElementsToStack(){
        stack.push("a1");
        stack.push("a2");
        stack.push("a3");
        stack.push("a4");
        assertEquals(4,stack.size());
        stack.push("a5");
        assertEquals(5,stack.size());
    }
    @Test
    public void testTopFromEmptyStack(){
       assertNull(stack.top());
    }
    @Test
    public void testPushThanTop(){
        stack.push("a1");
        assertEquals("a1",stack.top());
    }
    @Test(expected = StackEmptyException.class)
    public void testPopFromEmptyStack() throws Exception{
        stack.pop();
        fail("Empty stack exception");
    }

    @Test
    public void testPop() throws Exception{
        stack.push("a1");
        stack.push("a2");
        stack.push("a3");
        stack.pop();
        assertEquals(2,stack.size());
    }
    @Test
    public void testPopAnElement() throws Exception{
        stack.push("a1");
        stack.pop();
        assertEquals(0,stack.size());
    }
    @Test
    public void testTopAndPop() throws Exception{
        stack.push("a1");
        stack.push("a2");
        stack.push("a3");
        assertEquals(stack.top(),stack.pop());
    }
    @Test
    public void testPushThanPopToEmptyStack() throws Exception{
        stack.push("a1");
        stack.push("a2");
        stack.push("a3");
        assertEquals("a3",stack.pop());
        assertEquals("a2",stack.pop());
        assertEquals("a1",stack.pop());
        assertEquals(0,stack.size());
    }
    @Test
    public void testPushNullToEmptyStack(){
        stack.push(null);
        assertEquals(0,stack.size());
    }
}
