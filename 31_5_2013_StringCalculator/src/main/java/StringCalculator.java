

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/31/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculator {
    public static int add(String s){
       if(s.isEmpty())
           return 0;
       else
           return sum(s);
    }

    private static int sum(String s) {
        int sum=0;
        String negativeString="";
        String pattern="[ ; ,\n + //* \\[  \\] % ]";
        String []listNumber=s.split(pattern);
        for(String num:listNumber){
            if(!num.isEmpty() && toInt(num)>=0 && toInt(num)<1001){
                sum+=toInt(num);
            } else if(!num.isEmpty() && toInt(num)<0){
                throw new RuntimeException("Negatives not allowed ");
            }
        }

        return sum;
    }
    public static int toInt(String s){
        return Integer.parseInt(s);
    }
}

