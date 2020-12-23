
/*********
 *	Dang Truong
	CS 3345.0U2
	Pro. Greg Ozbirn
********/


/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        clear( );
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void clear( )
    {
        beginMarker = new Node<AnyType>( null, null, null );
        endMarker = new Node<AnyType>( null, beginMarker, null );
        beginMarker.next = endMarker;
        
        theSize = 0;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );   
        return true;         
    }
    
    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    
    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */    
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<AnyType>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
    }   
    
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        
        p.data = newVal;   
        return oldVal;
    }
    
    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corrsponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corrsponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */    
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;
        
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
            
        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;            
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }
    
    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        
        return p.data;
    }
    
    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );
        
        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );
    
        return new String( sb );
    }
    
    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private boolean okToRemove = false;
        
        public boolean hasNext( )
        {
            return current != endMarker;
        }
        
        public AnyType next( )
        {
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( ); 
                   
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        
        public void remove( )
        {
            if( !okToRemove )
                throw new IllegalStateException( );
                
            MyLinkedList.this.remove( current.prev );
            okToRemove = false;       
        }
    }
    
    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    
    /**
     * This function receives a value and return a acount of the number of time this item
     * is found in the list
     *return a count
     */
     public int itemCount(AnyType d)
     {
     	Node<AnyType> p;
     	int count = 0;
     	p = beginMarker.next;
     	for(int i = 0; i < theSize; i++)
     	{
     		if(p.data == d)
     		{
     			count++;
     		}
     		p = p.next;
     	}
     	return count;
     }
     /**
      *receive 2 index and swap 2 nodes of the index
      */
      
     public void swap(int idx1, int idx2)
     {
     	if( idx1 < 1 || idx1 > theSize || idx2 < 1 || idx2 > theSize )
            throw new IndexOutOfBoundsException( "The index1 is passed: " + idx1 + "The index2 is passed: " + idx2 + "; size of the list: " + theSize );
            
     	Node<AnyType> p1 = beginMarker.next;
     	Node<AnyType> p2 = beginMarker.next;
     	Node<AnyType> p3 = beginMarker.next;
     	int i;
     	if(idx1 < idx2)
     	{
     		for(i = 1; i < idx1; i++)
     		{
     			p1 = p1.next;
     			p2 = p2.next;
     		}  
     		for(int j = i; j < idx2; j++)
     		{
     			p2 = p2.next;
     		}
     		
     		p1.prev.next = p2;
     		p2.prev.next = p1;
     		
     		p3 = p2.prev;
     		p2.prev = p1.prev;
     		p1.prev = p3;
     		
     		p3 = p2.next;
     		p2.next = p1.next;
     		p1.next = p3;
     		
     		p3.prev = p1;
     		
     		p3 = p2.next;
     		p3.prev = p2;
     		 		
     	}
     	
     	if(idx1 > idx2)
     	{
     		for(i = 1; i < idx2; i++)
     		{
     			p1 = p1.next;
     			p2 = p2.next;
     		}  
     		for(int j = i; j < idx1; j++)
     		{
     			p2 = p2.next;
     		}
     		
     		p1.prev.next = p2;
     		p2.prev.next = p1;
     		
     		p3 = p2.prev;
     		p2.prev = p1.prev;
     		p1.prev = p3;
     		
     		p3 = p2.next;
     		p2.next = p1.next;
     		p1.next = p3;
     		
     		p3.prev = p1;
     		
     		p3 = p2.next;
     		p3.prev = p2;
     		 		
     	}
     }
     
     /**
      * receives two indexs and return an array list of node value from
      * first index to second index
      * idx1 is first index
      * idx2 is second index
      */
      public void sublist(int idx1, int idx2)
      {
      	if( idx1 < 1 || idx1 > theSize || idx2 < 1 || idx2 > theSize )
            throw new IndexOutOfBoundsException( "The index1 is passed: " + idx1 + "The index2 is passed: " + idx2 + "; size of the list: " + theSize );
            
      	int x = idx1 - idx2;
      	Node<AnyType> p = beginMarker.next;
      	
      	@SuppressWarnings("unchecked")  	
      	AnyType[] arr = (AnyType[]) new Object[x = java.lang.Math.abs(x) + 1]; 
      	
      	if(idx1 <= idx2)
      	{
      		for(int i = 1; i <= idx1; i++)
      		{
      			if(i == idx1)
      			{
      				for(int j = 0; j < x; j++)
      				{
      					arr[j] = p.data;
      					p = p.next;
      					i++;
      				}
      			}
      			p = p.next;
      		}
      	}
      	
      	if(idx2 < idx1)
      	{
      		for(int i = 1; i <= idx1; i++)
      		{
      			if(i == idx1)
      			{
      				for(int j = 0; j < x; j++)
      				{
      					arr[j] = p.data;
      					p = p.prev;
      				}
      			}
      			p = p.next;
      		}
      	}
      	
      	System.out.print("[ ");
      	for(int i = 0; i < x; i++)
      	{
      		System.out.print(arr[i] + " ");
      	}
      	System.out.print("] \n");
      }
     
     /**
      * receive 2 index and return an ArrayList of node value
      *	corresponding with index
      */
     public void select(int idx1, int idx2)
     {
     	if( idx1 < 1 || idx1 > theSize || idx2 < 1 || idx2 > theSize )
            throw new IndexOutOfBoundsException( "The index1 is passed: " + idx1 + "The index2 is passed: " + idx2 + "; size of the list: " + theSize );
            
     	Node<AnyType> p = beginMarker.next;
     	@SuppressWarnings("unchecked")
     	AnyType [] arr = (AnyType[]) new Object[2];
     
     	for(int i = 0; i < idx1; i++)
     	{
     		if(i == idx1-1)
     		{
     			arr[0] = p.data;
     			System.out.println("The value of the possition " + idx1 + " is: " + arr[0]);
     		}
     		p = p.next;  			
     	}
     	p = beginMarker.next;
     	for(int i = 0; i < idx2; i++)
     	{
     		if(i == idx2-1)
     		{
     			arr[1] = p.data;
     			System.out.println("The value of the possition " + idx2 + " is: " + arr[1]);
     		}
     		p = p.next;  			
     	}	 	
     }
	
	/**
	 * push a list in a stack and return in reverse order
	 */
	 public MyLinkedList<AnyType> reverse()
	 {
	 	MyLinkedList<AnyType> tmp = new MyLinkedList<AnyType>();
	 	Node<AnyType> p = beginMarker.next;
	 	@SuppressWarnings("unchecked")
	 	AnyType[] stack = (AnyType[]) new Object[theSize];
	 	
	 	for(int i = 0; i < theSize; i++)
	 	{
	 		stack[i] = p.data;
	 		p = p.next;	
	 	}
	 	
	 	for(int i = theSize-1; i >= 0 ; i--)
	 	{ 		
	 		tmp.add(stack[i]);
	 	}
	 	
	 	return tmp;
	 }
	 
	 /**
	  * receive index position and number of elements
	  * remove the node at index position for the number of elements specified
	  * idx is index
	  * num is number of elements
	  * tmpList is the list that will be erased
	  */ 
	 public void erase(int idx, int num)
	 {
	 	if( idx < 1 || idx > theSize || num < 0 || num > theSize )
            throw new IndexOutOfBoundsException( "The number is passed: " + num + " The index is passed: " + idx + "; size of the list: " + theSize );
            
	 	Node<AnyType> p = beginMarker.next;
	 	for(int i = 1; i < idx + 1; i++)
	 	{
	 		if(i == idx)
	 		{
	 			for(int j = i; j < i + num; j++)
	 			{
	 				p.next.prev = p.prev;
	 				p.prev.next = p.next;
	 				theSize--;
	 				p = p.next;
	 			}
	 		}
	 		p = p.next;
	 	}
	 }
	 
	 /**
	  * receive a list and index position as parameters
	  * copy all the passed list into the existing list at the position specified by parameter
	  */
	  public void insertList(AnyType[] tmp, int idx)
	  {
	  	if( idx < 1 || idx > theSize )
            throw new IndexOutOfBoundsException( "The index is passed: " + idx + "; size of the list: " + theSize );
            
	  	for(int i = 0; i < idx; i++)
	  	{
	  		if(i == idx - 1)
	  		{
	  			for(int j = tmp.length - 1; j >= 0; j--)
	  			{
	  				add(i, tmp[j]);
	  			}
	  		}
	  	}
	  }
	  
	  /**
	   * receives an integer and shifts the list
	   * example: +2: abcde -> cdeab, -3: abcde -> cdeab
	   */
	   
	   public void shift(int num)
	   {
	   		Node<AnyType> p1 = beginMarker.next;
	   		Node<AnyType> p2 = beginMarker.next;
	   		
	   		if( java.lang.Math.abs(num) > theSize )
            throw new IndexOutOfBoundsException( "The number is passed: " + num + "; size of the list: " + theSize );
	   	
	   		if(num > 0)
	   		{
	   			for(int i = 1; i< num; i++)
	   			{
	   				p2 = p2.next;
	   			}		
	   			
	   			for(int i = 1; i < theSize; i++)
	   			{
	   				p1 = p1.next;
	   			}
	   			
	   			beginMarker.next.prev = p1;
	   			p1.next = beginMarker.next;
	   			beginMarker.next = p2.next;
	   			beginMarker.prev = null;
	   			p2.next = endMarker;
	   			p1 = beginMarker.next;
	   		}
	   		if(num < 0)
	   		{
	   			for(int i = 0; i > num; i--)
	   			{
	   				p2 = p2.prev;
	   			}
	   			
	   			for(int i = 1; i < theSize; i++)
	   			{
	   				p1 = p1.next;
	   			}
	   			
	   			p1.next = beginMarker.next;
	   			p1.next.prev = p1;
	   			beginMarker.next = p2;
	   			p2.prev.next = endMarker;
	   		}
	   }
     
    private int theSize;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
    
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<Integer>( );
        //MyLinkedList<Integer> tmp = new MyLinkedList<Integer>( );
        Integer[] tmp = {95,74,27};
        
        for( int i = 0; i < 5; i++ )
            lst.add( i );
        for( int i = 20; i < 25; i++ )
            lst.add( 0, i );
        
        lst.add(20);
        System.out.println( "the list: " + lst );
        
        ////Test for itemCount
        System.out.print("\n\nTest for itemCount method: \n");
        System.out.print("the number 20 is counted " + lst.itemCount(20)+ " time \n");
        
        ////Test for swap method
        System.out.print("\n\nTest for swap method: \n");
        System.out.println("the original list: " + lst);
        System.out.println("swap the node at position 1 with the node at position 11");
        lst.swap(1,11);
        System.out.println(lst);
        
        ////Test for sublist method
        System.out.print("\n\nTest for sublist method: \n");
        System.out.println( "the list: " + lst );
        System.out.println("Get the item at possition 1 to 6");
        System.out.print( "the sublist: ");
        lst.sublist(1,6);
        System.out.println("Get the item at possition 6 to 1");
        System.out.print( "the sublist: ");
        lst.sublist(6,1);
        
        ////Test for select
        System.out.print("\n\nTest for select method: \n");
        System.out.println("Get the item at possition 1 and 11");
        System.out.println( "the list: " + lst );
        lst.select(1,11);
        
        ////Test for reverse
        System.out.println("\n\nTest for reverse method: ");
        System.out.println("The original list: ");
        System.out.println(lst);
        System.out.println("The list was reversed: ");
        System.out.println(lst.reverse());        
        
        ////Test for erase
        System.out.println("\n\nTest for erase method: ");
        System.out.println("erase at index 1 for 10 elements");
        System.out.println("before erasing: " + lst);
        lst.erase(1,10);
        System.out.println("after erasing: " + lst);
        
        ////Test for insertList
        System.out.println("\n\nTest for insertList method: ");
        System.out.println("pass a list: [95, 74, 27] at position 1 of orginal list");
        System.out.println("before inserting: " + lst);
        lst.insertList(tmp,1);
        System.out.println("after inserting: " + lst);
       
        ////Test for shift
        System.out.println("\n\nTest for shift method: ");
        System.out.println("the original list: " + lst);
        System.out.println("shift with value is 2 ");
        lst.shift(2);
        System.out.println(lst);
        System.out.println("shift with value is -3 ");
        lst.shift(-3);
        System.out.println(lst);
        
        
        
        
        /**
        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );
        
        System.out.println( lst );
        
        java.util.Iterator<Integer> itr = lst.iterator( );
        while( itr.hasNext( ) )
        {
            itr.next( );
            itr.remove( );
            System.out.println( lst );
        }
        */
    }
}