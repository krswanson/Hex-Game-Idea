package utilities;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;

public class CycleQueue<E> extends AbstractQueue<E> {

	private LinkedList<E> contents = new LinkedList<E>();
	private int size = 0;
	
	public CycleQueue() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean offer(E arg0) {
		size++;
		return contents.offerLast(arg0);
	}

	@Override
	public E peek() {
		return contents.peek();
	}

	@Override
	public E poll() {
		if (!contents.isEmpty()) size--;
		return contents.removeFirst();
	}

	@Override
	public Iterator<E> iterator() {
		return contents.iterator();
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Takes the next element in the queue and adds it to the end
	 * @return the next element in the queue (before it is put back at the end of the queue)
	 */
	public E cycle(){
		E first = poll();
		offer(first);
		return first;
	}

	public static void main(String[] a){
		CycleQueue<String> q = new CycleQueue<String>();
		q.add("Fred");
		q.offer("George");
		System.out.println(q.peek() + " " + q.size());
		System.out.println(q.cycle() + " " + q.size());
		System.out.println(q.cycle() + " " + q.size());
		System.out.println(q.poll() + " " + q.size());
		System.out.println(q.cycle() + " " + q.size());
		System.out.println(q.remove() + " " + q.size());
		
		
	}
}