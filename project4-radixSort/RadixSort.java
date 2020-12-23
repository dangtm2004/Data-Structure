
/************
 *Dang Truong
 *CS 3345.0U2
 *Pro. Greg Ozbirn
 ****************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Math;
import java.io.FileWriter;
import java.io.IOException;

public class RadixSort
{
	private static final int size = 10;
	@SuppressWarnings("unchecked")
	private static Queue<Integer>[] queue = new Queue[size];
	
	RadixSort()
	{
		for(int i = 0; i < size; i++)
		{
			queue[i] = new LinkedList<>();
		}
	}
	
	public static void radixSort(ArrayList<Integer> intArr)
	{
		if(intArr == null)
			throw new UnderflowException("ArrayList is null");
			
		int countDigit = 1;
		
		for(int i = 0; i < intArr.size(); i++)
		{
			int divide = intArr.get(i);
			int tempCount = 1;
				
			//find the largest digit in a list. [535,65,2] countDigit = 3
			while(divide >= 10)
			{
				divide /= 10; 
				tempCount++;
			}
				
			int index = intArr.get(i) % 10;
			int temp = intArr.get(i);
				
			queue[index].add(temp);			
							
			if(tempCount > countDigit)
				countDigit = tempCount;
					
		}
		
		int tempDigit = 2;
		int power = 1;
		while(tempDigit <= countDigit)
		{
			for(int i = 0; i < queue.length; i++)
			{
				if(queue[i].peek() != null)
				{
					int size = queue[i].size();
						
					while(size > 0)//finish all number on queue[i] before move to queue[i+1]
					{
						int index = queue[i].peek() / (int)Math.pow(10,power);
						index = index % 10;
						int temp = queue[i].peek();
						queue[index].add(temp);
						queue[i].remove();
						size--;
					}			
				}			
			}
			tempDigit++;
			power++;
		}
	}
	
	public void printRadixSort()
	{
		for(int i = 0; i < size; i++)
		{
			if(queue[i] != null)
				System.out.println(queue[i]);
		}
	}
	
	public void writeToFile()
	{
		try
		{
			FileWriter file = new FileWriter("output.txt",false);//boolean true if we want text file can append
			
			
			for(int i = 0; i < queue.length; i++)
			{	
				file.write(queue[i] + " ");
				file.write(System.getProperty("line.separator"));
			}
			file.close();
			System.out.println("File is writed successful.");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	public static void main (String[] args) throws Exception
	{
		File file = new File("C:\\Users\\dangt\\OneDrive\\Documents\\JCreator Pro\\MyProjects\\Data Structure\\project4-radixSort\\input.txt");
		Scanner scanner = new Scanner(file);
		ArrayList<Integer> intArr = new ArrayList<>();
		
		//add data from text document to array
		System.out.println("Add Data From Text Document To Array");
		while(scanner.hasNextLine())
		{			
			intArr.add(Integer.valueOf(scanner.nextLine()));
		}
		
		System.out.println(intArr);
		
		//do RadixSort
		System.out.println("\nDo RadixSort");
		RadixSort sort = new RadixSort();
		sort.radixSort(intArr);
		sort.printRadixSort();		
			
		//write to file
		System.out.println("\nWrite to file");
		sort.writeToFile();
	}
}