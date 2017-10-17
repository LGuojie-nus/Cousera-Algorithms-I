import edu.princeton.cs.algs4.StdRandom; 
import edu.princeton.cs.algs4.StdStats; 
import edu.princeton.cs.algs4.StdOut; 
//import ObjectSizeFetcher;
//import edu.princeton.cs.algs4.Stopwatch;
public class PercolationStats {
    //private Percolation cases;
    private final int ntrials;
    //private int ngrids;
    private double []nOS;
    public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
       if (n<=0||trials<=0) throw new java.lang.IllegalArgumentException("Invalid indices provided.");


       
       int ngrids=n*n;
       ntrials=trials;
       nOS=new double[trials];
       for(int i=0;i<trials;++i){
           Percolation cases=new Percolation(n);
            
           while(cases.percolates()==false){
                //find a blocked site to open
                int r,c;
                do{
                    r=StdRandom.uniform(n)+1;
                    c=StdRandom.uniform(n)+1;
                    //StdOut.println("r  "+r);
                    //StdOut.println("c  "+c);
                }while(cases.isOpen(r,c)==true);
                cases.open(r,c);
                //end 
            }    
                       
            nOS[i]=1.0*cases.numberOfOpenSites()/ngrids;
            
       }
    }
    public double mean(){
        return StdStats.mean(nOS);
    }                          // sample mean of percolation threshold
    public double stddev(){
        
        return StdStats.stddev(nOS);
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo(){
     //T=trials
        double mmean=mean();
        double sstd=stddev();
        return mmean-1.96*sstd/Math.sqrt(ntrials);
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi(){//T=trials
        double mmean=mean();
        double sstd=stddev();
        return mmean+1.96*sstd/Math.sqrt(ntrials);
    }                  // high endpoint of 95% confidence interval
    public static void main(String[] args){
        int n,trials;
        if(args.length==0){
        	StdOut.println("no inputs! use default");
            n=400;trials=10;
        }
        else{
            n=Integer.parseInt(args[0]);trials=Integer.parseInt(args[1]);
        }
        

        //double []nOS=new double[trials];
        PercolationStats stats=new PercolationStats(n,trials);//n and trials
        //calculate running period
        //Stopwatch stopWatch = new Stopwatch();
        
        //double time=stopWatch.elapsedTime();//in seconds
        /*
        StdOut.println(nOpenSite);
        System.out.println(Arrays.toString(nOS));
        */
        double mean=stats.mean();
        double stddev=stats.stddev();
        
        
        //print results
        //System.out.printf("time taken     = %.2f seconds\n",time);
        System.out.printf("mean           = %.9f\n",mean);
        System.out.printf("stddev         = %.9f\n",stddev);
        double Hi=stats.confidenceHi();
        double Lo=stats.confidenceLo();
        
        System.out.printf("95percentage confidence interval = [%.9f,%.9f]\n",Lo,Hi);
        //StdOut.println("95% confidence interval = "+"["+StdStats.confidenceLo(mean,stddev,trials)+", "StdStats.confidenceHi(mean,stddev,trials)+"]");
        //System.out.printf("95% confidence interval = %.9f, %.9f\n",Lo,Hi);  
       
    }        
}