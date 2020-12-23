
import java.util.PriorityQueue;
import java.io.File;
import java.util.Scanner;
import java.util.Comparator;

public class Kruskals
{
	int edgeAccepted = 0;	
	PriorityQueue<Edge> pq = new PriorityQueue<>(5, idComparator);
	int numVertices = pq.size() + 1;
	DisjSets ds = new DisjSets(numVertices);
	Edge e = new Edge();
	String vertexU, vertexV;
	int countVertex = 0;
	
	private class Edge
	{
		String destination, sourse;
		int idDes, idSrs, weight;
		
		public Edge()
		{
			sourse = "";
			destination = "";
			weight = 0;
			idDes = 0;
			idSrs = 0;
		}
		
		public Edge(int num, String srs, String des, int w, int idDes, int idSrs)
		{
			sourse = srs;
			destination = des;
			weight = w;
			this.idDes = idDes;
			this.idSrs = idSrs;
		}
	}
	
	public void getEdge(String[] wd)
	{
		Edge temp = new Edge();
			
		for(int i = 1; i < wd.length; i++)
		{
			if(i % 2 == 0)
			{
				temp.sourse = wd[0];
				temp.destination = wd[i-1];
				temp.weight = Integer.parseInt(wd[i]);
			
			}
			pq.add(temp);
		
		}
	}
	
	public static Comparator<Edge> idComparator = new Comparator<Edge>()
	{
		public int compare(Edge e1, Edge e2)
		{
			if(e1.weight < e2.weight)
				return -1;
			else if(e1.weight > e2.weight)
				return 1;
				
			return 0;
		}
	};
	/*
	private void edgeComparator
	{
		public int compare(Edge e1, Edge e2)
		{
			if(e1.weight < e2.weight)
				return -1;
			else if(e1.weight > e2.weight)
				return 1;
			return 0;
		}
	}*/
	/*
	while(edgeAccted < numVertices - 1)
	{
		e = pq.poll();
		int uset = ds.find(e.sourse);
		String vset = ds.find(e.destination);
	}*/
	
	public static void main (String[] args) throws Exception
	{
		//File file = new File("C:\\Users\\dangt\\OneDrive\\Documents\\JCreator Pro\\MyProjects\\Data Structure\\project5-Kruskals\\assn9_data.csv");
		File file = new File("assn9_data.csv");
		Scanner scanner = new Scanner(file);
		Kruskals kruskals = new Kruskals();
		
		while(scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			String[] words = line.split(line);
			kruskals.getEdge(words);
		}
	}
}