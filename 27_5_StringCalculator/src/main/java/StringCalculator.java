/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 5/27/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculator {
    public int add(String s) {
        if(s.isEmpty()){
            return 0;
        }else{
            String regex = "[\\n,]";
            String str[] = s.split(regex);
             return getSum(str) ;
         }
    }
    public int getSum(String[] str){
        int sum  =0;
        for(String num:str){
            if(!num.isEmpty())   {
                sum+=Integer.parseInt(num);
            }
        }
        return sum;
    }
}
