import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Item[] arr;
	public RandomizedQueue(){
		arr= (Item[]) new Object[2];
		size=0;
	}                 // construct an empty randomized queue
	public boolean isEmpty(){
		return size==0;
	}                 // is the randomized queue empty?
	public int size()                        // return the number of items on the randomized queue
	{
		return size;
	}

	private void resize(int capacity) {
		assert capacity >= size;

		// textbook implementation
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++) {
			temp[i] = arr[i];
		}
		arr = temp;
	}



	public void enqueue(Item item)           // add the item
	{
		if(item==null){
			throw new java.lang.IllegalArgumentException();
		}
		if (size == arr.length) resize(2*arr.length);    // double size of array if necessary
		// add item
		arr[size++]=item;
	}
	public Item dequeue()                    // remove and return a random item
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Stack underflow");
		StdRandom.shuffle(arr,0,size);  //[0,size)
		Item item = arr[size-1];
		arr[size-1] = null;                              // to avoid loitering
		size--;
		// shrink size of array if necessary
		if (size > 0 && size == arr.length/4) resize(arr.length/2);
		return item;

	}
	public Item sample()                     // return a random item (but do not remove it)
	{ 
		int r=StdRandom.uniform(size);
		return arr[r];

	}

	public Iterator<Item> iterator()
	{ 
		return new ReverseArrayIterator(); }

	private class ReverseArrayIterator implements Iterator<Item>
	{
		private int i = size;
		public boolean hasNext() { return i > 0; }
		public void remove() { 
			throw new java.lang.UnsupportedOperationException();
		}
		
		public Item next() {
			if (!hasNext()) throw new java.util.NoSuchElementException();
			StdRandom.shuffle(arr,0,i);
			return arr[--i]; 
		}
	}


	public static void main(String[] args)   // unit testing (optional)
	{
		
		
		
	}

}