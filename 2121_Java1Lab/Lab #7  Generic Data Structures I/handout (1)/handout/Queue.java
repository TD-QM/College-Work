import java.util.ArrayList;

public class Queue<T> {

	private ArrayList<T> elementList;
	/**
	 * Instantiate the needed datastructures in here (HINT: You might want to use
	 * arraylist)
	 */
	public Queue() {
		elementList = new ArrayList<T>();
	}

	/**
	 * Inserts the specified element into this queue if it is possible to do so
	 * immediately without violating capacity restrictions. When using a
	 * capacity-restricted queue, this method is generally preferable to add(E),
	 * which can fail to insert an element only by throwing an exception.
	 *
	 * @param element The element to add
	 */
	public void offer(T element) {
		elementList.add(element);
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null if
	 * this queue is empty.
	 *
	 * @return the head of the queue, or null if the queue is empty
	 */
	public T peek() {
		if(elementList.size() >= 1){
			return elementList.get(0);
		}
		return null;
	}

	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue
	 * is empty.
	 *
	 * @return the head of the queue, or null if the queue is empty
	 *
	 */
	public T poll() {
		if(elementList.size() >= 1){
			T head = elementList.get(0);
			elementList.remove(0);
			return head;
		}
		return null;
	}

	public static void main(String[] args) {
		Queue<String> queue = new Queue<String>();
		queue.offer("This");
		queue.offer("is");
		queue.offer("a");
		queue.offer("queue");

		System.out.println(queue.peek());
		System.out.println(queue.peek());
		System.out.println(queue.peek() + System.lineSeparator());

		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll() + System.lineSeparator());

		System.out.println(queue.poll());
		System.out.println(queue.peek());
	}
}