import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/6/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCalculator {
    public static int add(String s) {
        if (s.isEmpty())
            return 0;
        else {
            return sum(getAfterSplit(s));
        }
    }

    public static String[] getAfterSplit(String s) {
        String delimiterString = "\n";
        if (s.startsWith("//")) {
            if (s.contains("[") && s.contains("]")) {
                int startPoint = 0;
                int endPoint = 0;
                List<String> listDelimiter = new ArrayList<String>();
                for (int i = 0; i < s.length(); i++) {
                    if (String.valueOf(s.charAt(i)).equals("[")) {
                        startPoint = i;
                    }
                    if (String.valueOf(s.charAt(i)).equals("]")) {
                        endPoint = i;
                        listDelimiter.add(s.substring(startPoint + 1, endPoint));

                    }
                }
                s = s.substring(endPoint + 2);
                for (int i = 0; i < listDelimiter.size(); i++) {
                    s = s.replace(listDelimiter.get(i), ",");
                }

            } else {
                delimiterString = s.substring(2, 3);
                s = s.substring(4);
            }


        }
        s = s.replace(delimiterString, ",");
        String[] listNumber = s.split(",");
        return listNumber;
    }

    public static int sum(String[] listNumber) {
        int sum = 0;
        boolean flag = true;
        String negativeString = "";
        for (String num : listNumber) {
            if (!num.isEmpty() && toInt(num) >= 0) {
                if (toInt(num) < 1001) {
                    sum += toInt(num);
                }
            } else {
                flag = false;
                negativeString += num + " ";
            }
        }
        if (flag == false) {
            throw new NumberFormatException("negatives not allowed " + negativeString);
        }
        return sum;
    }

    public static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
