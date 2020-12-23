// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
 
 
 
/************
 *Dang Truong
 *CS 3345.0U2
 *Pro. Greg Ozbirn
 ****************/
 
 
import java.util.Queue;
import java.util.LinkedList;
 
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
            // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }


      /** The tree root. */
    private BinaryNode<AnyType> root;
    
    /**
     * return an int of the number of node in the tree
     */
	public int size()
  	{
    	return size(root);
   	}
     
   	private int size(BinaryNode<AnyType> t)
   	{
    	if(t == null)
     		return 0;
     		
     	return 1 + size(t.left) + size(t.right);
  	}
     
     /**
      * Return the number of leaf nodes
      */
  	public int numLeaves()
   	{
     	return numLeaves(root);
   	}
      
  	private int numLeaves(BinaryNode<AnyType> t)
  	{
      	if(t == null)
      		return 0;
      	if(t.left == null && t.right == null)
      		return 1;
      	else
      		return numLeaves(t.left) + numLeaves(t.right);
  	}
	  
	  /**
	   * Return the number of nodes that have a left child
	   */	   
	public int numLeftChildren()
	{
	  	return numLeftChildren(root);
	}
	   
	private int numLeftChildren(BinaryNode<AnyType> t)
	{
	   	int c = 0;
	   	if(t == null)
	   		return c = 0;
	   	if(t.left != null)
	   		c += 1 + numLeftChildren(t.left);
	   	if(t.right != null)
	   		c += numLeftChildren(t.right);
	   		
	   	return c;
	}
	   
	   /**
	    * Return true if every node has either two children or no children
	    */
	public boolean isFull()
	{
	 	return isFull(root);
	}
	    
	private boolean isFull(BinaryNode<AnyType> t)
	{
	  	boolean flag = true;
	  	
	  	if(t == null)
	  		return flag = true;
	  		
	    if( t.left != null && t.right != null)
	    {
	    	if(flag == true)
	    	{
	    		return isFull(t.left) && isFull(t.right);
	    	}
	    }
	    else if(t.left == null && t.right == null)
	    	return flag = true;
	    else
	    {
	    	flag = false;
	    	return flag;
	    }
	    		
	    return flag;	
	}
	
	/**
	 * Recieves a node value and returns the depth of this node, or -1 if not found
	 */
	 public int nodeDepth(AnyType x)
	 {
	 	return nodeDepth(x, root);
	 }
	 
	 private int nodeDepth(AnyType x, BinaryNode<AnyType> t)
	 {
	 	int depth = 0;
	 	
	 	if(t == null)
	 		return -1;
	 	
	 	int compareResult = x.compareTo(t.element);
	 	
	 	if(compareResult < 0)
	 		return ++depth + nodeDepth(x, t.left);
	 	else if(compareResult > 0)
	 		return ++depth + nodeDepth(x, t.right);
	 	else
	 		return depth;
	 	
	 }
	
	/**
	 * Print by level, creates a queue
	 * Enqueue the root
	 * print the root
	 * Enqueue root's children
	 * Print the nodes
	 * Keep going until the queue empty
	 */
	public void printByLevels()
	{
		printByLevels(root);
	}
	
	private void printByLevels(BinaryNode<AnyType> t)
	{
		if(t == null)
		{
			System.out.println("The tree is empty.");
			return;
		}
			
		Queue<BinaryNode> queue = new LinkedList<>();
		queue.add(t);
	
		while(true)
		{
			int count = queue.size();
			
			if(count == 0)
				break;
			
			while(count > 0)
			{
				BinaryNode node = queue.peek();
				System.out.print(node.element + " ");
			
				if(node.left != null)
					queue.add(node.left);
				if(node.right != null)
					queue.add(node.right);
				
				queue.remove();
				count--;
			}
			System.out.println();	
		}
	}
	
        // Test program
    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );
        
        t.insert(8);
        t.insert(6);
        t.insert(4);
        t.insert(7);
        t.insert(12);
        t.insert(10);
        t.insert(14);
        t.insert(20);
            
        //Test for size method
        System.out.println("TEST FOR SIZE METHOD");
        System.out.println("The number of nodes in the tree: " + t.size() + "\n");
        
        //Test for numLeaves method
        System.out.println("TEST FOR NUM-LEAVES METHOD");
        System.out.println("The number of leaves in the tree: " + t.numLeaves() + "\n");
        
        //Test for numLeftChildren method
        System.out.println("TEST FOR NUM-LEFT-CHILDREN METHOD");
        System.out.println("The number of nodes that have left child in the tree: " + t.numLeftChildren() +"\n");
        
        //Test for isFull method
        System.out.println("TEST FOR IS-FULL METHOD");
        System.out.println("The tree is full: " + t.isFull() + "\n");
        
        //Test for nodeDepth method
        System.out.println("TEST FOR NODE-DEPTH METHOD");
        System.out.println("The depth of node 20 is: " + t.nodeDepth(20) + "\n");
        
        //Test for printByLevels method
        System.out.println("TEST FOR PRINT-BY-LEVELS METHOD");
        System.out.println("Print by levels: ");
        t.printByLevels();
/**
        for( int i = 1; i < NUMS; i+= 2 )
            t.remove( i );

        if( NUMS < 40 )
            t.printTree( );
        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 2; i < NUMS; i+=2 )
             if( !t.contains( i ) )
                 System.out.println( "Find error1!" );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( t.contains( i ) )
                System.out.println( "Find error2!" );
        }
        */
    }
}

