package heap;

import net.datastructures.*;

/**
 * An implementation of a complete binary tree by means 
 * of a linked structure (LinkedBinaryTree). The LinkedBinaryTree class 
 * takes care of most of the mechanics of modifying 
 * the tree (you should read through the NDS4 documentation 
 * in order to fully understand how this class works. There's a link on
 * the website), but you will need 
 * to think about how to implement a CompleteBinaryTree such that
 * additions and removals operate *only* on the last node (hint: think
 * about other useful data structures). You must also ensure that you do not
 * violate the assignment runtime requirements when deciding how you will
 * track nodes within the tree.
 *  
 */

public class MyLinkedHeapTree<E> extends LinkedBinaryTree<E> 
		implements CompleteBinaryTree<E> {
	private NodeDeque<Position<E>> _node;

	
	/**
	 * Default constructor. The tree begins empty.
	 */
	public MyLinkedHeapTree() {
		_node = new NodeDeque();
		
	}

	/**
	 * Adds an element to the tree just after the last node. Returns the newly
	 * created position for the element.
	 *
	 * Note: You don't need to instantiate a new Position<E> as a local variable.
	 * Look at the NDS4 documentation for LinkedBinaryTree for how to add a
	 * new Position<E> to the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @param element to be added to the tree as the new last node
	 * @return the Position of the newly inserted element
	 */
	@Override
	public Position<E> add(E element) {

		if (_node.isEmpty()) { // adding when the deque is empty
			this.addRoot(element);
			_node.addLast(this.root);
			return this.root();
		}
		else {
			if (!this.hasLeft(_node.getFirst()) && !this.hasRight(_node.getFirst())) { // if parent node has no children
				this.insertLeft(_node.getFirst(), element);
				_node.addLast(this.left(_node.getFirst()));
				return this.left(_node.getFirst());
			}
			else if (this.hasLeft(_node.getFirst()) && !this.hasRight(_node.getFirst())) { // if parent node has a left child but no right child
				this.insertRight(_node.getFirst(), element);
				_node.addLast(this.right(_node.getFirst()));
				Position<E> positiontoRemove = right(_node.getFirst());
				_node.removeFirst();

				return positiontoRemove;
			}
		}
		return null; // base case, should not be reached
	}

	/**
	 * Removes and returns the element stored in the last node of the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @return the element formerly stored in the last node (prior to its removal)
	 * @throws EmptyTreeException if the tree is empty and no last node exists
	 */
	@Override
	public E remove() throws EmptyTreeException {

		if (_node.isEmpty() || _node.getLast() == null) {
			throw new EmptyTreeException("Tree is empty");
		}
		else {
			if (this.isRoot(_node.getLast())) { // if the selected node is the root
				return this.remove(_node.getLast());
			}
			if (this.hasRight(parent(_node.getLast()))){ // for right node
				_node.addFirst(parent(_node.getLast()));
				E element = this.remove(_node.getLast());
				_node.removeLast();
				return element;
		    }
			if (!this.hasRight(parent(_node.getLast()))) { // for left node
				E element = this.remove(_node.getLast());
				_node.removeLast();
				return element;
			}

		}
		return null; //base case, should never reach
	}

	/**
	 * This method returns the last entry in the deck.
	 * @return
	 */

	public Position<E> getLatest() {
		return _node.getLast();
	}

	/**
	 * This method returns my nodeDeque.
	 * @return
	 */

	public NodeDeque<Position<E>> getNode() {
		return _node;
	}
	
	/*
	 * Feel free to add helper methods here.
	 */ 
	
}
