/**
 * If you do not use recursion, you will receive 0 points. You can delete the
 * content of the methods before you start your work. You should not change the
 * method definition.
 */
public class Recursion {
    public static String reverse(String s) {
        if(s.length() <= 1){
            return s;
        }
        return reverse(s.substring(1)) + s.substring(0, 1);
    }

    public static boolean isPalindrome(String s) {
        if (s.length() <= 1){
            return true;
        }
        return (s.charAt(0) == s.charAt(s.length()-1)) && (isPalindrome(s.substring(1, s.length()-1)));
    }

    public static int length(String s) {
        if(s.equals("")){
            return 0;
        }
        return length(s.substring(1)) + 1;
    }
}