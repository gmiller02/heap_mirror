package heap;

import net.datastructures.Entry;
import net.datastructures.Position;

/**
 * Represents a key/value pair to be stored in a data 
 * structure, such as a heap. Entry<K,V> is a very 
 * limited accessing interface, so you may wish to add 
 * additional methods. In particular, think about the 
 * relationship of the Entry<K,V> to its location in 
 * the heap's binary tree. All methods must run in O(1)
 * time.
 *
 * Feel free to add additional comments. 
 */

public class MyHeapEntry<K,V> implements Entry<K,V> {
	private K _key;
	private V _value;
	private Position<MyHeapEntry<K, V>> _entry;


	/** 
	 * Default constructor. You may wish to modify the parameters.
	 */
	public MyHeapEntry(K key, V value) {
		_key = key;
		_value = value;
		_entry = null;

	}

	/**
	 * Sets the instance varible to the key parameter
	 * @param key
	 */

	public void setKey(K key) {
		_key = key;
	}
	
	/**
	 * @return the key stored in this entry 
	 */
	public K getKey() {
		return _key;
	}

	/** 
	 * @return the value stored in this entry 
	 */
	public V getValue() {
		return _value;
	}

	/**
	 * Sets the value instance variable to the value parameter
	 * @param value
	 */

	public void setValue(V value) { _value = value;}

	/**
	 * This method sets the entry instance variable to the Position (myheapentry) parameter
	 * @param entry
	 */

	public void setPos(Position<MyHeapEntry<K, V>> entry) {
		_entry = entry;
	}

	/**
	 * returns the position stored in this entry
	 * @return
	 */

	public Position<MyHeapEntry<K, V>> getPos() {
		return _entry;
	}

	
	/* Add any additional methods here */

}
