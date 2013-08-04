import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/31/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculatorTest {
    @Test
    public void testPassEmptyString() {
        assertEquals(0, StringCalculator.add(""));
    }

    @Test
    public void testPassOneNumber() {
        assertEquals(1, StringCalculator.add("1"));
    }

    @Test
    public void testPassTwoNumberWithDelimiter() {
        assertEquals(3, StringCalculator.add("1,2"));
    }

    @Test
    public void testWithNewLineDelimiter() {
        assertEquals(10, StringCalculator.add("1,2\n3,4"));
    }

    @Test
    public void testWithDifferentDelimiters() {
        assertEquals(3, StringCalculator.add("//;\n1;2"));
    }

    @Test(expected = RuntimeException.class)
    public void testWithException() {
        StringCalculator.add("-1,2");
        fail("Runtime Exception");
    }

    @Test
    public void TestCatchNegativeException() {
        try {
            assertEquals(0, StringCalculator.add("-1,2"));
            fail("Runtime Exception");
        } catch (RuntimeException e) {
            assertEquals("Negatives not allowed ", e.getMessage());
        }
    }

    @Test
    public void testWithNumberBigger1000() {
        assertEquals(5, StringCalculator.add("2+3+1001"));
    }

    @Test
    public void testWithLengthDelimiter() {
        assertEquals(6, StringCalculator.add("//[***]\n1***2***3"));
    }

    @Test
    public void testWithMultipleDelimiter() {
        assertEquals(6, StringCalculator.add("//[*][%]\n1*2%3"));
    }
}
