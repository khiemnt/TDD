/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/29/13
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculator {
    public static int add(String s) {
        if(s.isEmpty()){
            return 0;
        }else{
            return sum(s);
        }
    }
    public static int sum(String s){
        int sum=0;
        String pattern="[//,\n ;]";
        String[]str=s.split(pattern);
        for (String item:str){
            if(!item.isEmpty() && toInt(item)>=0 && toInt(item)<1000) {
                sum+=toInt(item);
            }else if(!item.isEmpty() && toInt(item)<0){
                throw new  NumberFormatException();
            }

        }
        return sum;
    }
    public static int toInt(String s){
        return Integer.parseInt(s);
    }

    public static void main(String[] args) {

    }
}
