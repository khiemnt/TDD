import com.sun.corba.se.impl.orb.ParserTable;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/3/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculatorTest {
    @Test
    public void testPassEmptyString(){
        assertEquals(0,StringCalculator.add(""));
    }
    @Test
    public void testPassTwoNumber(){
        assertEquals(3,StringCalculator.add("1,2"));
    }
    @Test
    public void testWithNewLineDelimiter() {
        assertEquals(10, StringCalculator.add("1,2\n3,4"));
    }

    @Test
    public void testWithDifferentDelimiters() {
        assertEquals(3, StringCalculator.add("//;\n1;2"));
    }


    @Test
    public void TestCatchNegativeException() {
        try {
           StringCalculator.add("-2,2");
            fail("Runtime Exception");
        } catch (RuntimeException e) {
            assertEquals("Negatives not allowed ", e.getMessage());
        }
    }

    @Test
    public void testWithNumberBigger1000() {
        assertEquals(1, StringCalculator.add("1+1001"));
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
