import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class StackTester {
	private Stack<String> stack;

	@Before
	public void setUp() {
		stack = new Stack<String>();
	}

	@Test
	public void testPush() {
		stack.push("This");
		stack.push("is");
		stack.push("a");
		stack.push("stack");
		assertEquals("This\nis\na\nstack\n", stack.toString());
	}

	@Test
	public void testPop() {
		stack.push("This");
		stack.push("is");
		stack.push("a");
		stack.push("stack");

		Object result;
		result = stack.pop();
		assertEquals("stack", result.toString());

		result = stack.pop();
		assertEquals("a", result.toString());

		result = "This\nis\n";
		assertEquals(result, stack.toString());
	}

	@Test
	public void testPeek() {
		stack.push("This");
		stack.push("is");
		stack.push("a");
		stack.push("stack");

		Object result;
		result = stack.peek();
		assertEquals("stack", result);

		result = stack.peek();
		assertEquals("stack", result);
	}

	@After
	public void restoreStreams() {
	}
}