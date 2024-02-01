import java.util.ArrayList;

public class Stack<T> {

	private ArrayList<T> itemList;

	/** You might want to use arraylist for this too */
	public Stack() {
		itemList = new ArrayList<T>();
	}

	/**
	 * Returns the number of components in this stack.
	 *
	 * @return the number of components in the stack
	 */
	public int getSize() {
		return itemList.size();
	}

	/**
	 * Tests if this stack is empty.
	 *
	 * @return true if the stack is empty false otherwise
	 */
	public boolean isEmpty() {
		if(getSize() >= 1){
			return false;
		}
		return true;
	}

	/**
	 * Pushes an item onto the top of this stack.
	 */
	public void push(T item) {
		itemList.add(item);
	}

	/**
	 * Looks at the object at the top of this stack without removing it from the
	 * stack.
	 *
	 * @return the object at the top of this stack
	 */
	public T peek() {
		if(!isEmpty()){
			return itemList.get(getSize() - 1);
		}
		return null;
	}

	/**
	 * Removes the object at the top of this stack and returns that object as the
	 * value of this function.
	 *
	 * @return The object at the top of this stack
	 */
	public T pop() {
		if(!isEmpty()){
			T head = itemList.get(getSize() - 1);
			itemList.remove(getSize() - 1);
			return head;
		}
		return null;
	}

	/**
	 * Prints each component of the stack seperated by a new line character.
	 *
	 * @returns The string representation of the stack
	 */
	@Override
	public String toString() {
		String stringRep = "";
		for(T item : itemList){
			stringRep += item + "\n";
		}
		return stringRep;
	}

	public static void main(String[] args) {
		Stack<String> stack = new Stack<String>();

		System.out.println(stack.getSize());

		stack.push("This");
		stack.push("is");
		stack.push("a");
		stack.push("stack");
		System.out.println(stack.toString());

		stack.pop();
		stack.pop();
		System.out.println(stack.toString());

		stack.push("still");
		stack.push("a");
		stack.push("stack");
		System.out.println(stack.toString());

		System.out.println(stack.peek());
		System.out.println(stack.peek());
	}
}
