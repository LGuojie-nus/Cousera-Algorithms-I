//import java.io.File;
//import java.util.Scanner;
//import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; 


public class Permutation{



	public static void main(String[] args)
	{
		int k = Integer.parseInt(args[0]);

		RandomizedQueue<String> rq = new RandomizedQueue<String>();


		while (!StdIn.isEmpty())
		{ // 读 一 数并计算 计 和          
			//StdOut.println("input!");
			rq.enqueue(StdIn.readString());

		}
		//		Iterator<String> aa=rq.iterator();

		//StdOut.println("size "+rq.size());

		//iteration(rq);
		for(int i=1;i<=k;i++)
		{
			StdOut.println(rq.dequeue());
			//iteration(rq);

		}


		/*
		StdOut.println("foreach loop:(this only works when Iterable is interfaced)");  
		for (String s : rq)    
			StdOut.println(s);  

	}

		 */

	}

}

