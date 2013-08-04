import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/6/13
 * Time: 1:37 PM
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
    public void testPassNewLineDelimiter(){
        assertEquals(6,StringCalculator.add("1\n2,3"));
    }
    @Test
    public void testPassDifferentDelimiters(){
        assertEquals(3,StringCalculator.add("//;\n1;2"));
        assertEquals(4,StringCalculator.add("//#\n1#3"));
    }
    @Test
    public void testWithException(){
        try{
            StringCalculator.add("-1,2");
            fail();
        }catch(NumberFormatException ex){
            assertEquals("negatives not allowed -1 ",ex.getMessage());
        }

    }
    @Test
    public void testWithManyNegativeException(){
        try{
            StringCalculator.add("-1,-2,3");
            fail();
        }catch(NumberFormatException ex){
            assertEquals("negatives not allowed -1 -2 ",ex.getMessage());
        }

    }
    @Test
    public void testWithNumberBigger1000(){
        assertEquals(1,StringCalculator.add("1,1001"));

    }
    @Test
    public void testWithLengthDelimiter(){
        assertEquals(6,StringCalculator.add("//[***]\n1***2***3"));

    }
    @Test
    public void testWithManyDifferenceDelimiter(){
        assertEquals(6,StringCalculator.add("//[*][%]\n1*2%3"));

    }
    @Test
    public void testWithManyDelimiterHaveLengthBiggerThan1(){
        assertEquals(6,StringCalculator.add("//[***][;;]\n1***2;;3"));

    }
}
