package heap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;
import org.junit.Ignore;
import org.junit.Test;
import net.datastructures.Entry;

/**
 * This class can be used to test the functionality of your MyHeap implementation.
 * You will find a few examples to guide you through the syntax of writing test cases.
 * Each test case uses its own heap instance to ensure that the test cases are independent 
 * of each other. All of the given examples should pass once you've implemented your heap.
 * 
 *
 * The annotation @Test before each test case is JUnit syntax. It basically lets the compiler know
 * that this is a unit test method. Use this annotation for *every* test method. This class is 
 * also like any other java class, so should you need to add private helper methods to use in your 
 * tests, you can do so, simply without the @Test annotation.
 * The general framework of a test case is:
 * 		- Name the test method descriptively, mentioning what is being tested (it is ok to have 
 * 		  slightly verbose method names here)
 * 		- Set-up the program state (ex: instantiate a heap and insert K,V pairs into it)
 * 		- Use assertions to validate that the progam is in the state you expect it to be
 * 
 * We've given you four example of test cases below that should help you understand syntax and the
 * general structure of tests.
 */

public class MyHeapTest {
	
	/**
	 * A simple test to ensure that insert() works.
	 */
	@Test
	public void testInsertOneElement() {
		// set-up
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		
		// Assert that your data structure is consistent using 
		// assertThat(actual, is(expected))
		assertThat(heap.size(), is(1));
		assertThat(heap.min().getKey(), is(1));
	}

	/**
	 * This is an example to check that the order of the heap is sorted as per the keys
	 * by comparing a list of the actual and expected keys.
	 */
	@Test
	public void testRemoveMinHeapOrderUsingList() {
	MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");
		
		// the expected ordering that keys come in
		List<Integer> expectedKeys = Arrays.asList(11, 13, 16, 44, 64);
		
		// the actual ordering of keys in the heap
		List<Integer> actualKeys = new ArrayList<Integer>();
		while(!heap.isEmpty()) {
			actualKeys.add(heap.removeMin().getKey());
		}
		
		// check that the actual ordering matches the expected ordering by using one assert
		// Note that assertThat(actual, is(expected)), when used on lists/ arrays, also checks that the
		// ordering is the same.
		assertThat(actualKeys, is(expectedKeys));
	}
	
	/**
	 * This is an example of testing heap ordering by ensuring that the min key is always at the root
	 * by checking it explicitly each time, using multiple asserts rather than a list.
	 */
	@Test
	public void testRemoveMinHeapOrder() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");
		
		
		// test the heap ordering by asserting on all elements
		assertThat(heap.removeMin().getKey(), is(11));
		assertThat(heap.removeMin().getKey(), is(13));
		assertThat(heap.removeMin().getKey(), is(16));
		assertThat(heap.removeMin().getKey(), is(44));
		assertThat(heap.removeMin().getKey(), is(64));
	}
	

	/**
	 * This is an example of how to test whether an exception you expect to be thrown on a certain line of code
	 * is actually thrown. As shown, you'd simply add the expected exception right after the @Test annotation.
	 * This test will pass if the exception expected is thrown by the test and fail otherwise.
	 * 
	 * Here, we're checking to see if an IllegalStateException is being correctly thrown after we try to
	 * call setComparator on a non-empty heap.
	 */
	@Test(expected=IllegalStateException.class)
	public void testSetComparatorThrowsIllegalStateException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		heap.setComparator(new IntegerComparator());
	}

	/**
	 * Tests if the min method will throw a EmptyPriorityQueueException.
	 */

	@Test(expected = EmptyPriorityQueueException.class)
	public void isEmpty(){
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.min();
	}

	/**
	 * Tests if the removeMin method will throw a EmptyPriorityQueueException.
	 */

	@Test(expected = EmptyPriorityQueueException.class)
		public void isEmpty2(){
			MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
			heap.removeMin();
		}

	/**
	 * The following two methods test if the replaceKey and insert methods correctly throw
	 * InvalidKey exceptions.
	 */

	@Test(expected = InvalidKeyException.class)
	    public void invalidKey() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.replaceKey(heap.insert(5, "Help"), null);
	}

	@Test(expected = InvalidKeyException.class)
	public void invalidKey2() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(null, "lord");
	}

	/**
	 * The following three methods test if the remove, replaceKey, and replaceValue methods
	 * correctly throw InvalidEntryExceptions.
	 */

	@Test(expected = InvalidEntryException.class)
		public void invalidEntry() {
			MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
			heap.remove(null);
		}


	@Test(expected = InvalidEntryException.class)
		public void invalidEntry2() {
			MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
			heap.replaceKey(null, 4);
		}


	@Test(expected = InvalidEntryException.class)
		public void invalidEntry3() {
			MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
			heap.replaceValue(null, "suffering");
		}

	/**
	 * This test tests the validity of my insert and remove methods, and because upHeap and
	 * downHeap are utilized in the test below, it also tests the validity of my upHeap
	 * and downHeap methods.
	 */

	@Test
	public void removeTest() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer, String> a = heap.insert(4, "bananna");
		Entry<Integer, String> b = heap.insert(5, "apple");
		Entry<Integer, String> c = heap.insert(9, "pear");

		heap.remove(a);

		assertThat(heap.getLastE(), is(c));
	}

	/**
	 * This test tests the validity of my replaceKey method by seeing if my Heap effectivly replaces
	 * the key.
	 */

	@Test
	public void replaceKTest() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer, String> a = heap.insert(4, "bananna");
		Entry<Integer, String> b = heap.insert(5, "apple");
		Entry<Integer, String> c = heap.insert(9, "pear");

		heap.replaceKey(a, 3);

		assertThat(a.getKey(), is(3));
	}

	/**
	 * This test tests the validity of my replaceValue method by seeing if my Heap effectivly replaces
	 * the key.
	 */

	@Test
	public void replaceVTest() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer, String> a = heap.insert(4, "bananna");
		Entry<Integer, String> b = heap.insert(5, "apple");
		Entry<Integer, String> c = heap.insert(9, "pear");

		heap.replaceValue(b, "kiwi");
		assertThat(b.getValue(), is("kiwi"));

	}

}
