# Cousera-Algorithms I

##Week2
   StdRandom.shuffle() calls N^2/2 times StdRandom to shuffle an array of size N. 
   Should use StdRandom.uniform and swap to get a random elements, in this way, only 1 time StdRandom is called

##Week3
    Any action to modify original array/queue/stack and etc should be avoided, as it will cause problems in other operations, and this kind of bug is very difficult to detect later! 

##Week5
	-nearest()
	when recursively find nearest point, do compare left and right branch and go to the one with shorter distance to query point
	this step will prune many nodes to save time