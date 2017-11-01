import java.util.TreeSet;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw; 
public class PointSET {
	private TreeSet<Point2D> pSet;
	public PointSET() {pSet=new TreeSet<Point2D>();} // construct an empty set of points 
	public boolean isEmpty(){return pSet.isEmpty();} // is the set empty? 
	public int size(){return pSet.size();} // number of points in the set 
	public void insert(Point2D p){
		if(p==null)throw new IllegalArgumentException();
		pSet.add(p);
	}// add the point to the set (if it is not already in the set)
	public boolean contains(Point2D p) {
		if(p==null)throw new IllegalArgumentException();
		return pSet.contains(p);	
	}            // does the set contain point p? 
	public void draw() {                          // draw all points to standard draw 
		if (pSet.isEmpty()) return;
		for(Point2D p:pSet)
			StdDraw.point(p.x(), p.y());
	}
	public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
	{	
		if(rect==null)throw new IllegalArgumentException();
		Queue<Point2D> qIter=new Queue<Point2D>();
		for(Point2D p:pSet) {
			if (rect.contains(p))qIter.enqueue(p);			
		}
		return qIter;
	}
	public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	{	
		if(p==null)throw new IllegalArgumentException();
		double dist=Double.POSITIVE_INFINITY;
		Point2D nearest=null;
		for(Point2D p0:pSet) {
			if (p0.distanceSquaredTo(p)<dist) {
				nearest=p0;
				dist=p0.distanceSquaredTo(p);	
			}
		}
		return nearest;
	}
	public static void main(String[] args)                  // unit testing of the methods (optional) 
	{
		
		
		
		
	}
}