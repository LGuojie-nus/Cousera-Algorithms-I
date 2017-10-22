import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
//import edu.princeton.cs.algs4.Stopwatch;

public class Solver {
	private MinPQ<Node> pq;
	private MinPQ<Node> pqTwin;
	private Node goalNode;
	private class Node implements Comparable<Node>{  //class can be public
		public int move;							//nested class members can be public
		public int manhattan;
		public Board board;
		public Node prevNode;

		public Node(Board currBoard,Node prevNode,int currMove)
		{
			board=currBoard;
			this.move=currMove;
			this.manhattan=currBoard.manhattan();
			this.prevNode=prevNode;
		}

		@Override
		public int compareTo(Node that) {
			// TODO Auto-generated method stub
			int thisp=this.move+this.manhattan;
			int thatp=that.move+that.manhattan;
			if (thisp<thatp)return -1;	
			return 1;
		}

	}

	public Solver(Board initial) {
		if (initial==null)throw new java.lang.IllegalArgumentException();		
		int currMove=0;
		Node currNode,currTwin,prevNode,prevTwin;
		Board prevBoard=null,prevTwinBoard=null;
		prevNode=null;prevTwin=null;
		Iterable<Board> iter;
		pq=new MinPQ<Node>();
		pqTwin=new MinPQ<Node>();
		currNode=new Node(initial,null,currMove);
		currTwin=new Node(initial.twin(),null,currMove);
		pq.insert(currNode);
		pqTwin.insert(currTwin);


		while(!currNode.board.isGoal()&&!currTwin.board.isGoal()) {
			currNode=pq.delMin();
			prevNode=currNode.prevNode;
			if(prevNode==null)prevBoard=null;
			else prevBoard=prevNode.board;
			currMove=currNode.move;
			if(currNode.board.isGoal())break;
			iter=currNode.board.neighbors();	//search neighbou
			for(Board b:iter){
				if (!b.equals(prevBoard)) {
					pq.insert(new Node(b,currNode,currMove+1));
				}
			}

			//Twin
			currTwin=pqTwin.delMin();
			prevTwin=currTwin.prevNode;
			if(prevTwin==null)prevTwinBoard=null;
			else prevTwinBoard=prevTwin.board;
			currMove=currTwin.move;
			if(currTwin.board.isGoal())break;
			iter=currTwin.board.neighbors();	//search neighbor
			for(Board b:iter){
				if (!b.equals(prevTwinBoard)) {
					pqTwin.insert(new Node(b,currTwin,currMove+1));
				}
			}

		}

		/*************/
		if(currNode.board.isGoal()) {
			goalNode=currNode;
		}
		if(currTwin.board.isGoal()){
			goalNode=null;
		}

	}           // find a solution to the initial board (using the A* algorithm)


	public boolean isSolvable() {

		return goalNode!=null;


	}            // is the initial board solvable?
	public int moves() {
		if(!isSolvable())return -1;
		else return goalNode.move;
	}                     // min number of moves to solve initial board; -1 if unsolvable



	public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
	{	
		if(!isSolvable())return null;

		int i=0;
		Queue<Board> a=new Queue<Board>();
		Queue<Board> b=new Queue<Board>();
		Node tempNode=goalNode;
		a.enqueue(tempNode.board);

		while(tempNode.prevNode!=null) {
			tempNode=tempNode.prevNode;
			a.enqueue(tempNode.board);
		}

		int size=a.size();
		Board[] temp=new Board[size];
		do {
			temp[i++]=a.dequeue();
		}while(i<size);

		for(int j=i-1;j>=0;j--)
			b.enqueue(temp[j]);

		return b;
	}


	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);


		int N = in.readInt();

		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();

		Board initial = new Board(blocks);
		Solver solver = new Solver(initial);

		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);      
		}

	}

}


