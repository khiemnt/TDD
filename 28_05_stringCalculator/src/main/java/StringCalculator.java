/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/28/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculator {

    public static int add(String s) {
       if(s.isEmpty()){
           return 0;
       }else{
           return calSum(s);
       }

    }
    private static int calSum(String s) {
        String pattern="[,\\n //;]";
        String []str=s.split(pattern);
        int sum = 0;
        int count = 0;
        for (String num : str) {
            if (!num.isEmpty() && toInt(num)>0) {
                sum += toInt(num);
            } else if (!num.isEmpty() && toInt(num) < 0) {
                throw new NumberFormatException("Negative is not allowed");
            }
        }
        return sum;
    }
    private static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
