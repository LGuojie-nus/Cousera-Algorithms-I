import java.util.Iterator;
//import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; 
//regarding interable and interator
//https://stackoverflow.com/questions/6863182/what-is-the-difference-between-iterator-and-iterable-and-how-to-use-them
public class Deque<Item> implements Iterable<Item> {
	private class Node<Item> {
		public Node<Item> left, right;
		private final Item item;
		public Node(Item item) {
			// FIXME: maybe it's a bad practice to throw exception in constructor
			if (item == null) { throw new NullPointerException(); }
			this.item = item;
		}

		public void connectRight(Node<Item> other) {
			this.right = other;
			other.left = this;
		}
	}
	private Node<Item> head, tail;
	private int size;
	public Deque(){                           // construct an empty deque
		size=0;
		head=null;
		tail=null;
	}
	public boolean isEmpty(){                 // is the deque empty?
		return size() == 0; 
	}
	public int size(){                        // return the number of items on the deque
		return size;
	}
	public void addFirst(Item item){          // add the item to the front
		if(item==null){
			throw new java.lang.IllegalArgumentException();
		}
		Node<Item> prevHead = head;
		Node<Item> newHead = new Node<Item>(item);
		if (prevHead != null) {
			newHead.connectRight(prevHead);
		} else {
			tail = newHead;
		}
		head = newHead;
		size++;
		//this.printDeque();
	}
	public void addLast(Item item){           // add the item to the end
		if(item==null){
			throw new java.lang.IllegalArgumentException();
		}
		Node<Item> newTail = new Node<Item>(item);
		Node<Item> prevTail = tail;
		if (prevTail != null) {
			prevTail.connectRight(newTail);
		} else {
			head = newTail;
		}
		tail = newTail;
		size++;
	}
	public Item removeFirst(){                // remove and return the item from the front
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Node<Item> prevHead = head;
		if(size==1) {
			head=null;
			tail = null;}
		else {
			head = prevHead.right;	
			head.left=null;
		}
		size--;
		return prevHead.item;
	}




	public Item removeLast(){                 // remove and return the item from the end
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Node<Item> prevTail = tail;
		if(size==1) {
			tail = null;
			head = null; 
		}
		else {
			tail=prevTail.left;
			tail.right=null;
		}
		size--;
		//this.printDeque();
		return prevTail.item;
	}
	public Iterator<Item> iterator(){         // return an iterator over items in order from front to end
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node<Item> curr = head;
		public boolean hasNext() {
			return curr != null;
		}
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		public Item next() {
			if (!hasNext()) { throw new java.util.NoSuchElementException(); }
			Item item = curr.item;
			curr = curr.right;
			return item;
		}
	}
	/* to print the remaining elements in the queue 
	public void printDeque() {
		Iterator<Item> I=this.iterator();
		if(!I.hasNext())System.out.print("empty");
		while(I.hasNext()) {
			System.out.print(I.next()+" ");			
		}
		
		System.out.println();
	}
	 */

	public static void main(String[] args){
		Deque<Integer> deque = new Deque<Integer>();
		deque.addLast(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.removeFirst();  // ==> 5
        deque.addFirst(7);
        deque.removeFirst();  // ==> 7
        deque.addFirst(9);
        deque.addLast(10);
        deque.addLast(11);
        deque.removeLast();    //==> 11
        
	}


}


