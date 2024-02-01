
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class QueueTester // extends TestCase
{

	private Queue<String> queue;
	// private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	// private final PrintStream systemOut = System.out;

	@Before
	public void setUp() {
		queue = new Queue<String>();
		// Output Streams for testing
		// System.setOut(new PrintStream(output));
	}

	@Test
	public void testOfferAndPeek() {
		queue.offer("This");
		queue.offer("is");
		queue.offer("a");
		queue.offer("queue");

		Object result;
		result = queue.peek();
		assertEquals("This", result.toString());

		result = queue.peek();
		assertEquals("This", result.toString());

		result = queue.peek();
		assertEquals("This", result.toString());
	}

	@Test
	public void testOfferAndPoll() {
		queue.offer("This");
		queue.offer("is");
		queue.offer("a");
		queue.offer("queue");

		Object result;
		result = queue.poll();
		assertEquals("This", result.toString());

		result = queue.poll();
		assertEquals("is", result.toString());

		result = queue.poll();
		assertEquals("a", result.toString());

		result = queue.poll();
		assertEquals("queue", result.toString());
	}

	@Test
	public void testPollAndPeek() {
		Object result;
		result = queue.poll();
		assertEquals(null, result);

		result = queue.peek();
		assertEquals(null, result);
	}

	@After
	public void restoreStreams() {
		// System.setOut(systemOut);
	}

}