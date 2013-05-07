/**
 * This is the class for the adjacency list
 * @author Vanshil Shah vs2409
 *
 * @param <E>
 */
public class AdjacencyList<E> {
/**
	 * Ctor for mycountinglinkedlist. Initializes a list with just a head and a tail. 
	 */
	public AdjacencyList()
	{
		clear();
	}
	
	/**
	 * Initializes an empty list
	 */
	private void clear() {
		head = new Node<E>(null, null, null);
		tail = new Node<E>(null, null, head);
		head.next = tail;
	}
	
	/**
	 * Inserts the data value into the list if it doesn't already exist.
	 * If it exists, it increments the counter.  
	 * @param data - the value to insert
	 */
	public void insert(E data) {
		Node<E> tempNode = traverseList(data);
		if(tempNode==null) {
			Node<E> newNode = new Node<E>(data, head.next, head);
			//Since I'm adding to the list from the front, the tail will always have the same previous node
			//after the first item is read in. 
			if(size==0) {
				tail.previous = newNode;
			}
			head.next.previous = newNode;
			head.next = newNode;
			size++;
		}
		else
			tempNode.count++;
		insertTotal++;
	}
	
	/**
	 * Traverses the list and searches for the data value in the list
	 * @param data - the data value to find in the list
	 * @return The node which matches the data point. 
	 */
	private Node<E> traverseList(E data) {
		Node<E> current = head.next;
		while(current!=null && current.data!=null) {
			if(current.data.equals(data)) {
				return current;
			}
			current = current.next;
		}
		return null;
	}
	
	/**
	 * Deletes the node with the data
	 * @param data - data to be deleted
	 */
	public void delete(E data) throws Exception {
		Node<E> tempNode = traverseList(data);
		if(tempNode==null) {
			throw new Exception("Not in list");
		}
		else {
			Node<E> prev = tempNode.previous;
			Node<E> next = tempNode.next;
			prev.next = tempNode.next;
			next.previous = prev;
			size--;
			insertTotal-=tempNode.count;
		}
	}
	
	
	public int find(E value) {
		int idx = -1;
		Node<E> current = head.next;
		for(int count = 0; count<size; count++) {
			if(current.data == value) {
				idx = count;
				break;
			}
			current = current.next;
		}
		return idx; 
	}

	
	/**
	 * 
	 * @return the size of the list.
	 */
	public int getSize() {
		return size;
	}
	

	private static class Node<E> {
		
		public Node(E data, Node<E> next, Node<E> previous) {
			this.next = next;
			this.previous = previous;
			this.data = data;
			count++;
		}
	
		//These are the member variables of my node class. This is a doubly linked list so there is a reference to previous and next nodes.
		public Node<E> next;
		public Node<E> previous;
		public int count=0;
		public E data;
	}
	
	
	
	private int size=0;
	private Node<E> head;
	private Node<E> tail;
	private int insertTotal=0;

}