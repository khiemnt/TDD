import junit.framework.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Stack;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/23/13
 * Time: 8:17 AM
 * To change this template use File | Settings | File Templates.
 */
public  class StackTest {

    protected StackImp s=new StackImp();
    @Test
    public void testStackIsEmpty() {
        StackImp s =new StackImp();
        Assert.assertTrue(s.isEmpty());
        assertEquals( 0,s.size());
    }

    @Test
    public void testPushToEmptyStack() {
        StackImp s =new StackImp();
        int numberOfPushes = 3;
        for (int i = 0; i < numberOfPushes; i++) {
            s.push("abc");
        }
        assertFalse(s.isEmpty());
        assertEquals(s.size(), numberOfPushes);
    }

    @Test
    public void testPushThenPop() {
        String str = "hello";
        s.push(str);
        assertEquals(s.pop(), str);
    }

    @Test
    public void testPushThenTop() {
        String str = "hello";
        s.push(str);
        int size = s.size();
        assertEquals(s.top(), str);
        assertEquals(s.size(), size);
    }

    @Test
    public void testPoppingDownToEmpty() {
        int numberOfPushes = (int)(Math.random() * 10 + 1);
        for (int i = 0; i < numberOfPushes; i++) {
            s.push("abc");
        }
        for (int i = 0; i < numberOfPushes; i++) {
            s.pop();
        }
        assertTrue(s.isEmpty());
        assertEquals(s.size(), 0);
    }

    @Test(expected=NoSuchElementException.class)
    public void testPopOnEmptyStack() {
        assertTrue(s.isEmpty());
        s.pop();
    }

    @Test(expected=NoSuchElementException.class)
    public void testPeekIntoEmptyStack() {
        assertTrue(s.isEmpty());
        s.top();
    }

}
