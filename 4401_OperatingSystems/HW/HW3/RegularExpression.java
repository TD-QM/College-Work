import java.util.regex.Matcher; 
import java.util.regex.Pattern;

public class RegularExpression {
	
	public static void main(String [] args) {
		
		String text = "This. is test text. this is. test. teXt This. is test text. This is.. .. test text";
	
		String pattern = "[a-zA-Z]+";	
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);
		while (m.find()) {
			System.out.println(m.group());
		}
	}
}