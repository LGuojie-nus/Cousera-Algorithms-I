import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

//unit test passed on 16 OCT, except function Board()
public final class Board{
	private final int[][]tile;
	private int manDist; //manhattan distance
	public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
	{	
		if (blocks==null)throw new java.lang.IllegalArgumentException();
		int n=blocks.length;
		tile=new int[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++)
				tile[i][j]=blocks[i][j];
		}                       
		//manhattan
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(tile[i][j]==0)continue; //0 is not counted in this calculation
				manDist=manDist+Math.abs(getCol(i,j)-j)+Math.abs(getRow(i,j)-i);

			}

		}

	}           // (where blocks[i][j] = block in row i, column j)
	public int dimension() {return tile.length;}                 // board dimension n
	public int hamming()                   // number of blocks out of place
	{	
		int hamming=0;
		int n=tile.length;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(i==n-1 && j==n-1) continue;
				if(tile[i][j]!=n*i+j+1)hamming++;
			}
		}
		return hamming;
	}
	public int manhattan()                 // sum of Manhattan distances between blocks and goal
	{
		return manDist;
	}

	public boolean isGoal()                // is this board the goal board?
	{	int n=tile.length;
	if(tile[n-1][n-1]!=0)return false;
	for(int i=0;i<n;i++) {
		for(int j=0;j<n;j++) {
			if(i==n-1 && j==n-1) continue;
			if(tile[i][j]!=n*i+j+1)return false;

		}
	}		
	return true;
	}
	public Board twin() {
		//swap first or second pair, in case one of them four blocks is 0;
		if(tile[0][0]!=0 && tile[1][0]!=0)return swapTile(0,0,1,0);
		else return swapTile(0,1,1,1);
	}

	public boolean equals(Object other)         // does this board equal y?
	{
		int n=tile.length;
		if (other == this) return true;
		if (other == null) return false;
		if (other.getClass() != this.getClass()) return false;
		Board that=(Board)other;
		if(this.dimension()!=that.dimension()) return false;
		for(int i=0;i<n;i++) { 
			for(int j=0;j<n;j++)
				if(this.tile[i][j]!=that.tile[i][j])return false;
		}
		return true;
	}

	private boolean isValid(int i,int j) {
		int n=tile.length;
		if((i<0 || i>=n)||(j<0 || j>=n)) return false;
		else return true;

	}
	public Iterable<Board> neighbors()     // all neighboring boards
	{
		int n=tile.length;
		Queue<Board> q=new Queue<Board>();
		int i=0,j=0,flag=0;
		for(i=0;i<n;i++) { 
			for(j=0;j<n;j++) {
				if(tile[i][j]==0) {
					flag=1;
					break;
				}
			}
			if(flag==1)break;	
		}
		if(isValid(i-1,j))q.enqueue(swapTile(i,j,i-1,j));
		if(isValid(i+1,j))q.enqueue(swapTile(i,j,i+1,j));
		if(isValid(i,j-1))q.enqueue(swapTile(i,j,i,j-1));
		if(isValid(i,j+1))q.enqueue(swapTile(i,j,i,j+1));
		return q;



	}
	public String toString()               // string representation of this board (in the output format specified below)
	{	
		int n=tile.length;
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", tile[i][j]));
			}
			s.append("\n");
		}
		return s.toString();

	}

	private int getCol(int i,int j) {
		int n=tile.length;
		return (tile[i][j]-1)%n;
	}
	private int getRow(int i,int j) {
		int n=tile.length;
		return (tile[i][j]-1)/n;
	}
	private Board swapTile(int i1,int j1,int i2,int j2)                    // a board that is obtained by exchanging any pair of blocks
	{	
		int n=tile.length;
		int[][]tileTwin=new int[n][n];

		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				tileTwin[i][j]=tile[i][j];
		int temp;
		temp=tileTwin[i1][j1];
		tileTwin[i1][j1]=tileTwin[i2][j2];
		tileTwin[i2][j2]=temp;
		Board swapedBoard=new Board(tileTwin);
		return swapedBoard;

	}

	public static void main(String[] args) { // unit tests (not graded)
		// for each command-line argument
		for (String filename : args) {

			// read in the board specified in the filename
			In in = new In(filename);
			int n = in.readInt();
			int[][] tiles = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					tiles[i][j] = in.readInt();
				}
			}

			// solve the slider puzzle
			Board initial = new Board(tiles);

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					StdOut.println((tiles[i][j]+": "+initial.getRow(i, j)+"-"+initial.getCol(i, j)));

				}
			}
			System.out.println("manhattan: "+initial.manhattan());
			System.out.print(initial.toString());
			System.out.println("###Ends###");
			Board twin=initial.twin();
			System.out.println("Unit Test #");
			System.out.println("initial:");
			System.out.print(initial.toString());
			System.out.println("twin:");
			System.out.print(twin.toString());
			System.out.println("hamming: "+initial.hamming());
			System.out.println("manhattan: "+initial.manhattan());
			System.out.println("manhattan-instance var: "+initial.manhattan());
			System.out.println("isGoal: "+initial.isGoal());
			System.out.println("equal-same board-return true: "+initial.equals(new Board(tiles)));
			System.out.println("equal-twin board-return false: "+initial.equals(twin));
			System.out.println("###Ends###");
			System.out.println();

		}
	}
}