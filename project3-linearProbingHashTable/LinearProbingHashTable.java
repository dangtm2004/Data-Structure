
/************
 *Dang Truong
 *CS 3345.0U2
 *Pro. Greg Ozbirn
 ****************/
 
public class LinearProbingHashTable<AnyType1 extends Comparable<? super AnyType1>, AnyType2 extends Comparable<? super AnyType2>>
{
	private int tableSize = nextPrime(13);
	private int size = 0; 
	
	@SuppressWarnings("unchecked")
	Entry<AnyType1, AnyType2>[] table = new Entry[tableSize];

	//construct the table
	public LinearProbingHashTable()
	{		
		for(int i = 0; i < tableSize; i++)
			table[i] = null;
	}
	
	private int getHashValue(AnyType1 k)
	{
		if(k == null)
			throw new UnderflowException("The key cannot be null");
			
		int hashValue = k.hashCode();
		
		hashValue = hashValue % tableSize;
		
		if(hashValue < 0)
			hashValue += tableSize;
			
		return hashValue;
	}
	
	private void checkRehash()
	{
			size = 0;
			int newSize = nextPrime(tableSize*2);
			@SuppressWarnings("unchecked")
			Entry<AnyType1, AnyType2>[] temp = new Entry[newSize]; // create new array
			int tempSize = tableSize;
			tableSize = newSize;
						
			for(int i = 0; i < tempSize; i++)
			{
				if(table[i] != null && table[i].isActive == true)
				{	
					int index = getHashValue(table[i].theKey);
				
					while(temp[index] != null)
					{
						index++;
						index = index % tableSize;
					}
					
					temp[index] = table[i];
					size++;					
				}				
			}				
			
			table = temp;
	}
	
	public boolean insert(AnyType1 k, AnyType2 v)
	{
		if(k == null)
			throw new UnderflowException("The key cannot be null");
			
		for(int i = 0; i < tableSize; i++)
		{
			if(table[i] != null)
			{
				int cmp = k.compareTo(table[i].theKey);
				if( cmp == 0 && table[i].isActive == true)
				{
					System.out.println("the key: " + table[i].theKey + " is duplicated.");
					return false;
				}					
			}			
		}		
				
		if(size >= tableSize/2)
			checkRehash();
			
		int temp = getHashValue(k);
		
		while(table[temp] != null)
		{
			temp++; 
			temp = temp % tableSize;
		}
		
		table[temp] = new Entry<>(k,v);
		size++;
		
		return true;
	}
	
	private static class Entry<AnyType1, AnyType2>
	{
		AnyType1 theKey;
		AnyType2 theValue;
		boolean isActive = true;
		String markDeleted = "";
		// Constructors
		Entry()
		{
			theKey = null;
			theValue = null;
		}
		Entry(AnyType1 k, AnyType2 v)
		{
			theKey = k;
			theValue = v;
		}
	}
	
	public String toString()
	{
		Entry<AnyType1, AnyType2> temp = new Entry<>();
		
		for(int i = 0; i < tableSize; i++)
		{
			if( table[i] != null)
			{				
				temp = table[i];
				System.out.println(" " + i + "    " + temp.theKey + ",    " + temp.theValue
					 + " " + temp.markDeleted);
			}
			else
				System.out.println(" " + i);		
		}
		return "";
	}
	
	public AnyType2 find(AnyType1 k)
	{
		if(k == null)
			throw new UnderflowException("The key cannot be null");
			
		AnyType2 temp = null;
		for(int i = 0; i < tableSize; i++)
		{
			if(table[i] != null && table[i].isActive == true)
			{
				int cmp = k.compareTo(table[i].theKey);
				if(cmp == 0)
				{
					return temp = table[i].theValue;
				}
			}
		}	
		return null;
	}
	
	public boolean delete(AnyType1 k)
	{
		if(k == null)
			throw new UnderflowException("The key cannot be null");
			
		for(int i = 0; i < tableSize; i++)
		{
			if(table[i] != null && table[i].isActive == true)
			{
				int cmp = k.compareTo(table[i].theKey);
				if(cmp == 0)
				{
					table[i].isActive = false;
					table[i].markDeleted = "(Deleted)";
					size--;
					return true;
				}
			}
		}
		return false;
	}
	
	public int getLocation(AnyType1 k)
	{
		if(k == null)
			throw new UnderflowException("The key cannot be null");
			
		for(int i = 0; i < tableSize; i++)
		{
			if(table[i] != null && table[i].isActive == true)
			{
				int cmp = k.compareTo(table[i].theKey);
				if(cmp == 0)
				{			
					System.out.println("The key was found.");
					return i;
				}
			}			
		}
		System.out.println("The key was not found.");
		return -1;
	}
	
	private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }
	
	private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
    
	public static void main (String[] args) 
	{
		LinearProbingHashTable<Integer,String> hashTable = new LinearProbingHashTable<>();
		String str = "This is project 3";
		
		// Test insert method
		System.out.println("Test insert method");
		for(int i = 0; i < 15; i += 3)
			System.out.println("insert Entry<" + i + ", " + str + ">: " + hashTable.insert(i,str));
		
		//Test toString method
		System.out.println("\nTest toString method");
		System.out.println(hashTable);
		
		//Test find method
		System.out.println("Test find method");
		System.out.println("The value of the key 3 is: " + hashTable.find(3));
		System.out.println("The value of the key 11 is: " + hashTable.find(11));
		
		//Test delete method
		System.out.println("\nTest delete method");
		System.out.println("Delete the key 3: " + hashTable.delete(3));
		System.out.println("Delete the key 11: " + hashTable.delete(11));
		System.out.println(hashTable);
		System.out.println("insert Entry<" + 3 + ", " + str + ">: " + hashTable.insert(3,str));
		System.out.println("insert Entry<" + 6 + ", " + str + ">: " + hashTable.insert(6,str));
		
		//Test rehash method
		System.out.println("\nTest rehash method");
		str = "Test rehash method";
		for(int i = 15; i < 30; i += 3)
			System.out.println("insert Entry<" + i + ", " + str + ">: " + hashTable.insert(i,str));
		System.out.println(hashTable);
		
		//Test getHashValue method
		System.out.println("Test getHashValue method");
		System.out.println("Hash value of the key 27: " + hashTable.getHashValue(27));
		
		//Test getLocation method
		System.out.println("\nTest getLocation method");
		System.out.println("Location of the key 27: " + hashTable.getLocation(27));
		System.out.println("Location of the key 28: " + hashTable.getLocation(28));
	}
}