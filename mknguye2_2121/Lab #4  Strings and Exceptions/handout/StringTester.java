public class StringTester{

    public static void main(String args[]){

        System.out.println("\"" + StringUtility.reverse("Yo wassup my     dude  ") + "\"");
        // output: dude my wassup yo
    
        System.out.println("\"" + StringUtility.reverse("123 Hello1 !!!") + "\"");
        // output: !!! hello1 123

        System.out.println("\"" + StringUtility.reverse("              m i  like  genuinelywant to cry right now") + "\"");
        // output: now right cry to genuinelywant like i m

        // System.out.println(StringUtility.maxOccuringCharacter(""));

        // System.out.println(StringUtility.maxOccuringCharacter("xyzxx"));
        // // output: x
    
        // System.out.println(StringUtility.maxOccuringCharacter("g0ggl3s^ @r3 3lit3"));
        // // output: g

        // System.out.println(StringUtility.maxOccuringCharacter("qqqqeeeeuuuusjdamn"));
        // // output: q
    
        // System.out.println(StringUtility.maxOccuringCharacter(" "));
        // // output: throws IllegalArguementException
    
        
        // System.out.println(StringUtility.isPalindrome("123racecar321!@#!@#"));
        // // output: true
    
        // System.out.println(StringUtility.isPalindrome("A man, a plan, a canal, Panama"));
        // // output: true
    
        // System.out.println(StringUtility.isPalindrome("-152"));
        // // output: false
        
        // System.out.println(StringUtility.isPalindrome("tacocat"));
        // // output: true

        // System.out.println(StringUtility.isPalindrome("racecar"));
        // // output: true
    
    
    }
    
}