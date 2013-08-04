import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/28/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculatorTest {
    @Test
    public void testPassEmptyString(){
        Assert.assertEquals(0,StringCalculator.add(""));
    }
    @Test
    public void testPassOneNumber(){
        Assert.assertEquals(1,StringCalculator.add("1"));
    }
    @Test
    public void testPassTwoNumber(){
        Assert.assertEquals(5,StringCalculator.add("2,3"));
    }
    @Test
    public void testPassNewLineDelimiters(){
        Assert.assertEquals(8,StringCalculator.add("2\n3,3"));
    }
    @Test
    public void testWithManyDelimiter(){
        Assert.assertEquals(3,StringCalculator.add("//;\n1;2"));
    }
    @Test(expected = NumberFormatException.class)
    public void testWithException(){
        StringCalculator.add("-1,2,3")   ;
    }

}
