import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
public class KdTree {
	private Node root;
	private int Size;
	private static final boolean X=true;
	private static final boolean Y=false;
	private double dist;  //use in nearest()
	public KdTree() { //constructor
		root=null;
		Size=0;
	}
	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree
		private boolean compareX;		//true compareX/vertical line, false compare y/horizontal
		public Node(Point2D p,boolean compareX,RectHV rect) {
			if(p==null)throw new IllegalArgumentException();
			this.p=p;
			this.rect=rect;
			this.compareX=compareX;
		}
		public int compare(Point2D p) {
			if (this.compareX) {
				if(p.x()<this.p.x())
					return -1;
				else if(p.x()>this.p.x())
					return 1;
				else if(p.y()==this.p.y())
					return 0;
				else return 1;
			}
			else {
				if(p.y()<this.p.y())
					return -1;
				else if(p.y()>this.p.y()) 
					return 1;
				else if(p.x()==this.p.x())
					return 0;
				else return 1;
			}
		}
	}


	public boolean contains(Point2D p)
	{ 
		if(p==null)throw new IllegalArgumentException();
		return get(root,p); 
	}

	private boolean get(Node x,Point2D p)
	{ // Return value associated with key in the subtree rooted at x;
		// return null if key not present in subtree rooted at x.
		if (x == null) return false;
		int cmp = x.compare(p);
		if (cmp < 0) return get(x.lb,p);
		else if (cmp > 0) return get(x.rt,p);
		else return true;
	}
	public boolean isEmpty() {return size()==0;}

	public int size() {return size(root);}
	private int size(Node x)
	{
		if (x == null) return 0;
		else return Size;
	}

	public void insert(Point2D p) {
		if(p==null)throw new IllegalArgumentException();
		if(!this.contains(p)) {
			root = put(root,p,X,null,0);Size++;		
		}
	}

	private RectHV newRect(Node x,int cmp) { //x=prev
		if(x==null)return new RectHV(0,0,1,1);
		if(cmp<0) {
			if(x.compareX)
				return new RectHV(x.rect.xmin(),x.rect.ymin(),x.p.x(),x.rect.ymax());
			else 
				return new RectHV(x.rect.xmin(),x.rect.ymin(),x.rect.xmax(),x.p.y());
		}	
		else {
			if(x.compareX)
				return new RectHV(x.p.x(),x.rect.ymin(),x.rect.xmax(),x.rect.ymax());
			else 
				return new RectHV(x.rect.xmin(),x.p.y(),x.rect.xmax(),x.rect.ymax());
		}
	}
	private Node put(Node x,Point2D p,boolean xy,Node prev,int cmps)
	{
		// Change key’s value to val if key in subtree rooted at x.
		// Otherwise, add new node to subtree associating key with val.
		if (x == null) {
			return new Node(p,xy,newRect(prev,cmps));//int cmp = p.compareTo(x.p);	
		}
		int cmp=x.compare(p);
		if (cmp < 0) 	  x.lb = put(x.lb,p,x.compareX==Y,x,cmp);
		else if (cmp > 0) x.rt = put(x.rt,p,x.compareX==Y,x,cmp);
		else { x.p=p;}
		return x;
	}
	public void draw() {
		draw(root);
	}
	private void draw(Node x) {
		if (x==null)return;
		else {
			StdDraw.point(x.p.x(),x.p.y());
			draw(x.lb);
			draw(x.rt);
		}

	}

	public Point2D nearest(Point2D queryP) 
	{  
		if(queryP==null)throw new IllegalArgumentException();
		dist=Double.POSITIVE_INFINITY;
		return findNearest(root,null,queryP);
	}

	

	private Point2D findNearest(Node x,Point2D currP,Point2D queryP) {
		if(x==null)return null;
		if(x.rect.distanceSquaredTo(queryP)<=dist) {
			if(x.p.distanceSquaredTo(queryP)<dist) {
				dist=x.p.distanceSquaredTo(queryP);
				currP=x.p;
			}
			if(x.rt!=null&&x.lb!=null) {
				if(x.rt.rect.distanceSquaredTo(queryP)<x.lb.rect.distanceSquaredTo(queryP)) {
					currP=findNearest(x.rt,currP,queryP);
					currP=findNearest(x.lb,currP,queryP);									}
				else {currP=findNearest(x.lb,currP,queryP);currP=findNearest(x.rt,currP,queryP);}
			}
			else if(x.rt!=null)
				currP=findNearest(x.rt,currP,queryP);
			else if(x.lb!=null)
				currP=findNearest(x.lb,currP,queryP);
		}
		return currP;
	}


	/***************
	 * range module
	 * @param rect
	 * @return
	 */
	public Iterable<Point2D> range(RectHV rect)
	{	
		if(rect==null)throw new IllegalArgumentException();
		Queue<Point2D> qIter=new Queue<Point2D>();
		inside(root,rect,qIter); 
		return qIter;
	} 
	private void inside(Node x,RectHV rect,Queue<Point2D> q) {
		if(x==null)return;
		if(rect.contains(x.p)) {
			q.enqueue(x.p);        //enque this point
			//inside(x.lb,rect,q);	//search both side
			//inside(x.rt,rect,q);
		}
		if(x.lb!=null && rect.intersects(x.lb.rect)) {
			inside(x.lb,rect,q);
		}
		if(x.rt!=null && rect.intersects(x.rt.rect)) {
			inside(x.rt,rect,q);
		}
	}
}
