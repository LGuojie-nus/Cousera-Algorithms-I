public class BruteCollinearPoints {

	private int numLineSeg=0;
	private LineSegment[] lineSeg;
	private int prevCap=5,prevCapPoints=5;
	private Point min,max;
	//Point[][] twoEnds=new Point[5][2];
	private Point[][] twoEnds=new Point[5][2];
	private Point[] SegPoints=new Point[5];
	private final Point[] copyPoints;
	
	public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		if (points==null)
			throw new java.lang.IllegalArgumentException();
		for(int i=0;i<points.length;i++)
			if(points[i]==null) throw new java.lang.IllegalArgumentException();
		checkDuplicate(points);
		
		//copy points array
		copyPoints=new Point[points.length];
		for(int i=0;i<points.length;i++)  
			copyPoints[i]=points[i];
		
		
		int i=0,pointsLength=points.length,numSegPoints=3;
		for(i=0;i<pointsLength;i++) 
			for(int j=i+1;j<pointsLength;j++)
				for(int k=j+1;k<pointsLength;k++) {
					if(points[i].slopeOrder().compare(points[j], points[k])==0) {
						numSegPoints=3;
						for(int l=k+1;l<pointsLength;l++) {
							if(points[i].slopeOrder().compare(points[j], points[l])==0) {
								
								SegPoints[numSegPoints++]=points[l];
								if(numSegPoints>=prevCapPoints)
									{prevCapPoints=2*prevCapPoints;resizeSeg(prevCapPoints,numSegPoints);}
							}	
						}
						if(numSegPoints!=3) {
							//emptySeg();
							SegPoints[0]=points[i];SegPoints[1]=points[j];SegPoints[2]=points[k];
							findEnds(numSegPoints);
							resetSeg();
							if(!EndsExisted()) {
								twoEnds[numLineSeg][0]=min;twoEnds[numLineSeg][1]=max;						
								numLineSeg++;	
								if(numLineSeg>=prevCap)
									{prevCap=2*prevCap;resizeEnds(prevCap);}
							}
							
						}	
					}
	}
		lineSeg=new LineSegment[numLineSeg];
		for (i = 0; i < numLineSeg; i++) 
			lineSeg[i]=new LineSegment(twoEnds[i][0],twoEnds[i][1]);
		
}
	
	private void checkDuplicate(Point[] points) {
		for(int i=0;i<points.length;i++) {
			for(int j=i+1;j<points.length;j++) {	
				if (points[i].compareTo(points[j])==0)
					throw new java.lang.IllegalArgumentException();
			}
		}
	}
	private void resetSeg() {
		
		SegPoints=new Point[5];
		prevCapPoints=5;
	}
	
	private void resizeSeg(int capacity,int numPoints) {

		// textbook implementation
		Point[] temp = new Point[capacity];
		for (int i = 0; i < numPoints ; i++) {
			temp[i] = SegPoints[i];
		}
		SegPoints = temp;
	}
	private void resizeEnds(int capacity) {
		// textbook implementation
		Point[][] temp = new Point[capacity][2];
		for (int i = 0; i < numLineSeg; i++) {
			temp[i][0] = twoEnds[i][0];
			temp[i][1] = twoEnds[i][1];
		}
		twoEnds = temp;
	}

	
	public int numberOfSegments()        // the number of line segments
	{
		return numLineSeg;



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
	
	private void findEnds(int numSegPoints) {
		min=SegPoints[0];max=SegPoints[0];
		for(int i=1;i<numSegPoints;i++) {
			if(SegPoints[i].compareTo(min)==1)
				min=SegPoints[i];
			if(SegPoints[i].compareTo(max)==-1)
				max=SegPoints[i];
		}
	} 


	public LineSegment[] segments()                // the line segments
	{   
		return lineSeg;
	}
	private void test() {
		System.out.println("testing module:");
		System.out.println("numLineSeg:   "+numLineSeg);
		System.out.println("############");
		System.out.println("Points:   ");
		/*
		for (int i = 0; i < numLineSeg; i++) {
			System.out.println("Segments"+'['+i+']'+":  "+twoEnds[i][0]+", "+twoEnds[i][1]);
			
		}
		*/
		System.out.println("############");
		//System.out.println("sizeOfLineSeg[]:   "+numLineSeg);
		
		
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
		//System.out.println("test "+n);
	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        collinear.numberOfSegments();
        collinear.segments();
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);	        //segment.draw();
	    }
        //mutate points[] array that was passed to constructor;
        points = new Point[n+1];
        for (int i = 0; i < n+1; i++) {
	        int x = 1;
	        int y = 10;
	        points[i] = new Point(x, y);
	        
	       
	    }
	    
        collinear.segments();
        
        
        System.out.println(collinear.numberOfSegments());
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);	        //segment.draw();
	    }
	   	
	}
}
