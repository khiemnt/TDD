import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/30/13
 * Time: 1:49 PM
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
        Assert.assertEquals(3,StringCalculator.add("1,2"));
    }
    @Test
    public void testPassNewLineDelimiter(){
        Assert.assertEquals(6,StringCalculator.add("1\n2,3"));
    }
    @Test
    public void testSupportDifferentDelimiters(){
        Assert.assertEquals(3,StringCalculator.add("//;\n1;2"));
    }
    @Test(expected = NumberFormatException.class)
    public void testWithException(){
       StringCalculator.add("-2,3,4");
    }
    @Test
    public void testContainNumberBigger1000(){
        Assert.assertEquals(3,StringCalculator.add("3 + 1001"));
    }
    @Test
    public void testWithLengthDelimiters(){
        Assert.assertEquals(6,StringCalculator.add("//[***]\n1***2***3"));
    }
    @Test
    public void testWithMultipleDelimiters(){
        Assert.assertEquals(6,StringCalculator.add("//[*][%]\n1*2%3"));
    }
}
