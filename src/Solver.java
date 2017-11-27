import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;
import java.util.Comparator;

public class Solver {
	
	public class SearchNode{
		private int moves;
		private Board board;
		private SearchNode preNode;
		
		private SearchNode(Board board) {
			moves = 0;
			this.board = board;
			preNode = null;
		}
	}
	
	private SearchNode goalNode;
	
	private class PriorityOrder implements Comparator<SearchNode>{
		public int compare(SearchNode sn1, SearchNode sn2) {
			int priority1 = sn1.board.manhattan() + sn1.moves;
			int priority2 = sn2.board.manhattan() + sn2.moves;
			
			if(priority1 > priority2) return 1;
			else if(priority1 < priority2) return -1;
			else return 0;
		}
	}
	
	public Solver(Board board) {
		// main search node
		PriorityOrder po = new PriorityOrder();
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>(po);
		SearchNode sn = new SearchNode(board);
		
		// twin search node
		PriorityOrder twinPo = new PriorityOrder();
		MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(twinPo);
		SearchNode twinSn = new SearchNode(board.twin());
		pq.insert(sn);
		twinPq.insert(twinSn);
		
		//delete and return the minimum priority node from MinPQ
		SearchNode minNode = pq.delMin();
		SearchNode twinMinNode = twinPq.delMin();
		while(!minNode.board.isGoal() && !twinMinNode.board.isGoal()) {
			for(Board b: minNode.board.neighbors()) {
				if((minNode.preNode == null) || !b.equals(minNode.preNode.board)) {
					SearchNode newNode = new SearchNode(b);
					newNode.moves = minNode.moves +1;
					newNode.preNode = minNode;
					pq.insert(newNode);
				}			
			}
			minNode = pq.delMin();
			
			
			for(Board b: twinMinNode.board.neighbors()) {
				if(minNode.preNode == null || !b.equals(twinMinNode.preNode.board)) {
					SearchNode newTwinNode = new SearchNode(b);
					newTwinNode.moves = twinMinNode.moves +1;
					newTwinNode.preNode = twinMinNode;
					twinPq.insert(newTwinNode);	
				}
			}
			twinMinNode = twinPq.delMin();
		}
		
		if(minNode.board.isGoal()) goalNode = minNode;
			else goalNode = null;
		
	}
	// whether the initial board is solvable
	public boolean isSolvable() {
		return goalNode != null ;
	}
	//
	public int moves() {
		if(goalNode == null) return -1;
		else return goalNode.moves;
	}
	
	public Iterable<Board> solution(){
		if(!isSolvable()) return null;
		Stack<Board> boardStack = new Stack<Board>();
		for(SearchNode sn=goalNode; sn != null; sn=sn.preNode) {
			boardStack.push(sn.board);
		}
		return boardStack;
	}
	
	 public static void main(String[] args) {
	        // TODO Auto-generated method stub
	        // create initial board from file
	        In in = new In(args[0]);
	        int N = in.readInt();
	        int[][] blocks = new int[N][N];
	        for (int i = 0; i < N; i++)
	            for (int j = 0; j < N; j++)
	                blocks[i][j] = in.readInt();
	        Board initial = new Board(blocks);

	        // solve the puzzle
	        Solver solver = new Solver(initial);

	        // print solution to standard output
	        if (!solver.isSolvable())
	            StdOut.println("No solution possible");
	        else {
	            StdOut.println("Minimum number of moves = " + solver.moves());
	            for (Board board : solver.solution())
	                StdOut.println(board);
	        }
	    }

}
