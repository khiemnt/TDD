import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/27/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculatorTest {
    @Test
    public void testPassEmptyString() {
        StringCalculator stringCalculator = new StringCalculator();
        Assert.assertEquals(0, stringCalculator.add(""));
    }

    @Test
    public void testPassOneNumber() {
        StringCalculator stringCalculator = new StringCalculator();
        Assert.assertEquals(2, stringCalculator.add("2"));
    }
    @Test
    public void testPassTwoNumber(){
        StringCalculator stringCalculator= new StringCalculator();
        Assert.assertEquals(3,stringCalculator.add("1,2"));
    }
    @Test
    public void testPassManyNumber(){
        StringCalculator stringCalculator= new StringCalculator();
        Assert.assertEquals(6,stringCalculator.add("1,2,3"));
    }
    @Test
    public void testWithNewlineDelimiters(){
        StringCalculator stringCalculator= new StringCalculator();
        Assert.assertEquals(6,stringCalculator.add("1\n2,3"));
    }
}
