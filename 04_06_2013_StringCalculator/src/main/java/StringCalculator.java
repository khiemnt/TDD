/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/4/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculator {
    public static int add(String s) {
        if(s.isEmpty())
            return 0;
        else
        {
             return sum(s);
        }

    }
    public static int sum(String s){

        String regex="[, \n // ; * \\[ \\]  % # ]";
        String []listNumbers=s.split(regex) ;
        int sum=0;
        boolean  flag=true;
        String negativeString="";
        for(String num:listNumbers){
            if(!num.isEmpty() &&toInt(num)>=0 && toInt(num)<1001){
                sum+=Integer.parseInt(num);
            } else if(!num.isEmpty() && toInt(num)<0){
                negativeString+=num+" ";
                flag=false;
            }
        }
        if(flag==false){
            throw new  NumberFormatException("negatives not allowed "+negativeString);
        }
        return sum  ;
    }

    public static int toInt(String s){
        return Integer.parseInt(s) ;
    }
}
