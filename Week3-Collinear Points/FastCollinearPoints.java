import java.util.Arrays;

public class FastCollinearPoints {
	private int numLineSeg=0;
	private LineSegment[] lineSeg;
	private int prevCap=5;
	private Point min,max;
	private Point[][] twoEnds=new Point[5][2];
	private final Point[] copyPoints;
    private final Point[] orderedPoints;
	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
	{
		if (points==null)
			throw new java.lang.IllegalArgumentException();
		for(int i=0;i<points.length;i++)
			if(points[i]==null) throw new java.lang.IllegalArgumentException();
		checkDuplicate(points);
		/*******************************8/
		 * 
		 * 
		 */
		
		
		/*************************************8/
		 * 
		 */
		copyPoints=new Point[points.length];
		orderedPoints=new Point[points.length];
		for(int i=0;i<points.length;i++) { 
			copyPoints[i]=points[i];
			orderedPoints[i]=points[i];
		}
		
		Point origin;
		for(int i=0;i<copyPoints.length;i++) {
			origin=copyPoints[i];
			Arrays.sort(orderedPoints,origin.slopeOrder()); //points array in order
			//find 4 points on a line
			//check 3 neighbour points
			for(int k=0;k+2<copyPoints.length;k++) {
				if ((origin.slopeOrder().compare(orderedPoints[k], orderedPoints[k+1])==0)&&(origin.slopeOrder().compare(orderedPoints[k+1], orderedPoints[k+2])==0)){					
					//double slope=origin.slopeTo(points[k]);
					//if (SlopeExisted(slope)) continue;
					//allSlopes[numSlope++]=slope;
					findTwoNewEnds(orderedPoints,origin,k);
					if(!EndsExisted()) {
						twoEnds[numLineSeg][0]=min;twoEnds[numLineSeg][1]=max;
						//System.out.println(min+", "+max);
						numLineSeg++;
					}
					else {//System.out.println("existed: "+min+", "+max);
					}
					
					if(numLineSeg>=prevCap) {
						prevCap=2*prevCap;
						resize(prevCap);
					}
				}	
			}
		}
		
	}
	private void resize(int capacity) {
		// textbook implementation
		Point[][] temp = new Point[capacity][2];
		for (int i = 0; i < numLineSeg; i++) {
			temp[i][0] = twoEnds[i][0];
			temp[i][1] = twoEnds[i][1];
		}
		twoEnds = temp;
	}
	/*
	private void resize2(int capacity) {
		// textbook implementation
		double[] temp = new double[capacity];
		for (int i = 0; i < numSlope; i++) {
			temp[i] = allSlopes[i];
			
		}
		allSlopes = temp;
	}*/
	private void findTwoNewEnds(Point[] points,Point origin,int k) {
		min=origin; max=origin;
		int start=k,end=k+2;
		
		/****************/
		
		//look forward
		int i=1,j=1;
		while(k-i>=0 && origin.slopeOrder().compare(points[k], points[k-i])==0)
				{start=k-i;i++;}
		
		
		//look backward
		while(k+j<orderedPoints.length && origin.slopeOrder().compare(points[k], points[k+j])==0)
				{end=k+j;j++;}
		
		/******************/
		
		for(i=start;i<=end;i++) {
			if(points[i].compareTo(min)==-1)
				min=points[i];
			if(points[i].compareTo(max)==1)
				max=points[i];
		}
	}

	private boolean EndsExisted(){
		if(numLineSeg==0)
			return false;
		else {
			for(int i=0;i<numLineSeg;i++) {
				if(twoEnds[i][0]==min && twoEnds[i][1]==max)
					return true;
				
			}
		}
		return false;
		
	}
	
	
	public int numberOfSegments()        // the number of line segments
	{return numLineSeg;}
	
	public LineSegment[] segments()                // the line segments
	{   lineSeg=new LineSegment[numLineSeg];
		for (int i = 0; i < numLineSeg; i++) 
			lineSeg[i]=new LineSegment(twoEnds[i][0],twoEnds[i][1]);
		return lineSeg;
	}

	private void test() {
		System.out.println("testing module:");
		System.out.println("numLineSeg:   "+numLineSeg);
		System.out.println("############");
		System.out.println("Points:   ");
		for (int i = 0; i < numLineSeg; i++) {
			System.out.println("Segments"+'['+i+']'+":  "+twoEnds[i][0]+", "+twoEnds[i][1]);
			
		}
		System.out.println("############");
		System.out.println("sizeOfLineSeg[]:   "+numLineSeg);
		
		
	}
	private void checkDuplicate(Point[] points) {
		for(int i=0;i<points.length;i++) {
			for(int j=i+1;j<points.length;j++) {	
				if (points[i].compareTo(points[j])==0)
					throw new java.lang.IllegalArgumentException();
			}
		}
	}
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        
	    	int x = in.readInt();
	        int y = in.readInt();
	        
	        points[i] = new Point(x, y);
	        
	    }
	    
	    // draw the points
	    //StdDraw.enableDoubleBuffering();
	    //StdDraw.setXscale(0, 32768);
	    //StdDraw.setYscale(0, 32768);
	    /*
	    for (Point p : points) {
	    	p.draw();
	    }
	    StdDraw.show();
	    */
	    
	    
	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    System.out.println(collinear.numberOfSegments());
	    
	    //collinear.test();
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        //segment.draw();

	    }
	    //StdDraw.show();
	     
	}




}