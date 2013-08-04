import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/4/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class StringCalculatorTest {
    @Test
    public void testPassEmptyString(){
        assertEquals(0,StringCalculator.add(""));
    }
    @Test
    public void testPassOneNumber(){
        assertEquals(1,StringCalculator.add("1"));
    }
    @Test
    public void testPassTwoNumber(){
        assertEquals(3,StringCalculator.add("1,2"));
    }
    @Test
     public void testPassWithNewLineDelimiter(){
        assertEquals(6,StringCalculator.add("1\n2,3"));
    }
    @Test
    public void testPassWithDifferentDelimiters(){
        assertEquals(3,StringCalculator.add("//;\n1;2"));
    }
    @Test
    public void testPassWithException(){
        try{
            StringCalculator.add("-1,3");
            fail("Runtime exception");
        }catch(RuntimeException ex){
            assertEquals("negatives not allowed -1 ", ex.getMessage());
        }

    }
    @Test
    public void testPassWithMultipleNegatives(){
        try{
            StringCalculator.add("-1,-2,3");
            fail("Runtime exception");
        }catch(RuntimeException ex){
            assertEquals("negatives not allowed -1 -2 ", ex.getMessage());
        }

    }
    @Test
    public void testPassWithNumberBigger1000(){
        assertEquals(1,StringCalculator.add("1,1001"));

    }
    @Test
    public void testPassWithLengthDelimiter(){
        assertEquals(6,StringCalculator.add("//[***]\n1***2***3"));

    }
    @Test
    public void testPassWithManyDelimiter(){
        assertEquals(6,StringCalculator.add("//[*][%]\n1*2%3"));

    }
    @Test
    public void testWithExpectDelimiter(){
        assertEquals(2,StringCalculator.add("\\#\n#2"));

    }
}
