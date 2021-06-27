package heap;

import java.util.Comparator;
import net.datastructures.CompleteBinaryTree;
import net.datastructures.DefaultComparator;
import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;
import net.datastructures.Position;
import net.datastructures.AdaptablePriorityQueue;
import support.heap.HeapWrapper;

/**
 * An implementation of an adaptable priority queue by 
 * means of a heap. Be certain that your running times 
 * match those specified in the program documentation, 
 * and remember that the running time of a "called" 
 * method sets the minimum running time of the "calling" 
 * method. Feel free to add additional comments. 
 */

public class MyHeap<K,V> implements HeapWrapper<K,V>, AdaptablePriorityQueue<K,V> {
	
	// This the underlying data structure of your heap
	private MyLinkedHeapTree<MyHeapEntry<K,V>> _tree;
	private Comparator<K> _keyComparator;


	/** 
	 * Creates an empty heap with the given comparator. 
	 * 
	 * @param comparator to be used for heap keys
	 */
	public MyHeap(Comparator<K> comparator) {
		_tree = new MyLinkedHeapTree<>();
		_keyComparator = comparator;
	}

	/**
	 * Sets the comparator used for comparing items in the heap to the
	 * comparator passed in.
	 * 
	 * @param comparator, the comparator to be used for heap keys
	 * @throws IllegalStateException if priority queue is not empty
	 * @throws IllegalArgumentException if null comparator is passed in
	 */
	public void setComparator(Comparator<K> comparator)
			throws IllegalStateException, IllegalArgumentException {

		throw new IllegalStateException();
	}

	/**
	 * Returns a CompleteBinaryTree that will allow the visualizer 
	 * access to private members, shattering encapsulation, but 
	 * allowing visualization of the heap. This is the only method 
	 * needed to satisfy HeapWrapper interface implementation.
	 *
	 * Do not modify or call this method. It is solely
	 * necessary for the visualizer to work properly.
	 * 
	 * @return the underlying binary tree on which the heap is based
	 */
	public CompleteBinaryTree<MyHeapEntry<K,V>> getTree() {
		return _tree;
	}
	
	/** 
	 * Returns the size of the heap.
	 * This method must run in O(1) time.
	 *
	 * @return an int representing the number of entries stored
	 */
	public int size() {
		return _tree.size();
	}

	/** 
	 * Returns whether the heap is empty.
	 * This method must run in O(1) time.
	 * 
	 * @return true if the heap is empty; false otherwise
	 */
	public boolean isEmpty() {
		return _tree.isEmpty();
	}

	/** 
	 * Returns but does not remove the entry with minimum key.
	 * This method must run in O(1) time.
	 * 
	 * @return the entry with the minimum key in the heap
	 * @throws EmptyPriorityQueueException if the heap is empty
	 */
	public Entry<K,V> min() throws EmptyPriorityQueueException {
		if (_tree.isEmpty()) {
			throw new EmptyPriorityQueueException("Heap is empty");
		}
		return _tree.root().element(); // gets the root element
	}

	/** 
	 * Inserts a key-value pair and returns the entry created.
	 * This method must run in O(log n) time.
	 *
	 * @param key to be used as the key the heap is sorting with
	 * @param value stored with the associated key in the heap
	 * @return the entry created using the key/value parameters
	 * @throws InvalidKeyException if the key is not suitable for this heap
	 */
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		if (key == null) {
			throw new InvalidKeyException("Key is null");
		}
		MyHeapEntry<K, V> entry = new MyHeapEntry<K, V>(key, value);
		entry.setPos(_tree.add(entry)); // create entry and add to tree

		if (!_tree.isRoot(entry.getPos())) {
			this.upHeap(entry); // upHeap when adding!
		}

		return entry;
	}

	/** 
	 * Removes and returns the entry with the minimum key.
	 * This method must run in O(log n) time.
	 * 
	 * @return the entry with the with the minimum key, now removed 
	 * @throws EmptyPriorityQueueException if the heap is empty
	 */
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		if (_tree.isEmpty()) {
			throw new EmptyPriorityQueueException("Heap is empty");
		}

		return this.remove(min());
	}

	/** 
	 * Removes and returns the given entry from the heap.
	 * This method must run in O(log n) time.
	 *
	 * @param entry to be removed from the heap
	 * @return the entry specified for removal by the parameter, now removed
	 * @throws InvalidEntryException if the entry cannot be removed from this heap
	 */
	public Entry<K,V> remove(Entry<K,V> entry) throws InvalidEntryException {
		if (entry == null) {
			throw new InvalidEntryException("Invalid Entry");
		}

		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);
		Position<MyHeapEntry<K,V>> og = checkedEntry.getPos();


		if (checkedEntry.getPos() == _tree.getLatest()) { // if the selected node is already last
			return _tree.remove();
		}
		this.swap(checkedEntry.getPos(), _tree.getLatest()); // swap marked entry and last entry

		MyHeapEntry<K, V> removedEntry = _tree.remove();

		if(_tree.isInternal(og)) {
			this.downHeap(og); // downHeap the position of the checkedEntry
		}

		return removedEntry;
	}

	/** 
	 * Replaces the key of the given entry.
	 * This method must run in O(log n) time.
	 *
	 * @param entry within which the key will be replaced
	 * @param key to replace the existing key in the entry
	 * @return the old key formerly associated with the entry
	 * @throws InvalidEntryException if the entry is invalid
	 * @throws InvalidKeyException if the key is invalid
	 */
	public K replaceKey(Entry<K,V> entry, K key) throws InvalidEntryException, InvalidKeyException {
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);
		if (checkedEntry == null) {
			throw new InvalidEntryException("Entry is null");
		}
		if (key == null) {
			throw new InvalidKeyException("Key is null");
		}
		checkedEntry.setKey(key);
		this.upHeap(checkedEntry);
		this.downHeap(checkedEntry.getPos()); // downheap when removing

		return checkedEntry.getKey();
	}

	/** 
	 * Replaces the value of the given entry.
	 * This method must run in O(1) time.
	 *
	 * @param entry within which the value will be replaced
	 * @param value to replace the existing value in the entry
	 * @return the old value formerly associated with the entry
	 * @throws InvalidEntryException if the entry cannot have its value replaced
	 */
	public V replaceValue(Entry<K,V> entry, V value) throws InvalidEntryException {		
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);
		if (checkedEntry == null) {
			throw new InvalidEntryException("Entry is null");
		}
		checkedEntry.setValue(value);

		return checkedEntry.getValue();
	}
	

	/**
	 * Determines whether a given entry is valid and converts it to a
	 * MyHeapEntry. Don't change this method.
	 *
	 * @param entry to be checked for validity with respect to the heap
	 * @return the entry cast as a MyHeapEntry if considered valid 
	 *
	 * @throws InvalidEntryException if the entry is not of the proper class
	 */
	public MyHeapEntry<K,V> checkAndConvertEntry(Entry<K,V> entry)
			throws InvalidEntryException {
		if (entry == null || !(entry instanceof MyHeapEntry)) {
			throw new InvalidEntryException("Invalid entry");
		}
		return (MyHeapEntry<K, V>) entry;
	}

	/*
	 * You may find it useful to add some helper methods here.
	 * Think about actions that may be executed often in the 
	 * rest of your code. For example, checking key  
	 * validity, upheaping and downheaping, swapping or 
	 * replacing elements, etc. Writing helper methods instead 
	 * of copying and pasting helps segment your code, makes 
	 * it easier to understand, and avoids problems in keeping 
	 * each occurrence "up-to-date."
	 */

	/**
	 * This method adds a node to a heap, and when adding, compares the parent value
	 * to the child value. If the child value is less than the parent value, the two
	 * values are swapped.
	 * @param entry
	 */


	public void upHeap(MyHeapEntry<K, V> entry) {
		Position<MyHeapEntry<K, V>> getPos = entry.getPos();

		while (getPos != _tree.root()) { // for all nodes that are not the root

			Position<MyHeapEntry<K, V>> parent = _tree.parent(getPos);

			if (_keyComparator.compare(parent.element().getKey(), getPos.element().getKey()) > 0) { // compare parent and child nodes
				this.swap(parent, getPos); // swap the lesser node
				getPos = parent;
			}

			else {
				break;
			}
		}
	}

	/**
	 * This method is used when removing a node from the heap. It compares the value of a node
	 * with its children. If the value is greater than one or both, it is swapped with the child of
	 * lowest value.
	 * @param entry
	 */


		private void downHeap (Position<MyHeapEntry < K, V>> entry){
			Position<MyHeapEntry < K, V>> parent = entry;

			while (_tree.isInternal(parent)) {
				Position<MyHeapEntry<K, V>> child = null;

				// case where node has one child
				if (_tree.hasLeft(parent) && (!_tree.hasRight(parent))) {
					child = _tree.left(parent);
				}
				// case where node has two children
				if (_tree.hasLeft(parent) && (_tree.hasRight(parent))) {
					if (_keyComparator.compare(_tree.left(parent).element().getKey(), _tree.right(parent).element().getKey()) < 0) {
						child = _tree.left(parent);
					} else {
						child = _tree.right(parent);
					}
				}
				if (_keyComparator.compare(parent.element().getKey(), child.element().getKey()) > 0) { // compare and get lesser child
					this.swap(parent, child);
					parent = child;
				} else {
					break;
				}
			}
		}


	/**
	 * This method swaps the entries and positions of two nodes in order to upHeap/downHeap.
	 * @param a
	 * @param b
	 */

	private void swap(Position<MyHeapEntry<K, V>> a, Position<MyHeapEntry<K, V>> b) {

		_tree.swapElements(a, b); //swap entries

		a.element().setPos(a); // swaps positions
		b.element().setPos(b);
	}

	/**
	 * Method that returns the last entry in the deque, used for testing.
	 */

	public MyHeapEntry<K, V> getLastE() {
		return _tree.getNode().getLast().element();
	}

}
