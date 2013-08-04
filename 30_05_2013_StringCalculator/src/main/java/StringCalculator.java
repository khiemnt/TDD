/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/30/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculator {
    public static int add(String s) {
        if(s.isEmpty()){
            return 0;
        }  else{
            return sum(s) ;
        }
    }
    public static int sum(String s){
        int sum=0;
        String pattern="[//,\n ;+ \\[ \\] * %]";
        String[]listNumber=s.split(pattern);
        for(String num:listNumber){
            if(!num.isEmpty() && toInt(num)>=0 && toInt(num)<1001 ){
                sum+=toInt(num);
            } else if(!num.isEmpty() && toInt(num)<0){
                throw  new NumberFormatException();
            }
        }
        return sum;
    }
    public static int toInt(String number){
          return Integer.parseInt(number);
    }
}
