import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


public class Board {
	
	private final int N;
    private int[] blocks;
    
    
    // get the row index of a number
    private int row(int number) {
    	return (int) Math.ceil(number/N);
    }
    // get the col index of a number
    private int col(int number) {
    	if(number % N == 0) return N;
    	else return number % N;
    }
    // construct a board from an n-by-n array of blocks(where blocks[i][j] = block in row i and column j)
	public Board(int[][] blocks) {
		N = blocks.length;
		this.blocks = new int[N*N];
		int k =0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				this.blocks[k] = blocks[i][j];
				k++;
			}
		}
	}
	
	// get board dimension
	public int dimension() {
		return N;
	}
	
	// number of blocks out of place
	// hamming++ if BLOCKS
	public int hamming() {
		int hamming = 0;
		for(int i=0;i<blocks.length;i++) {
			if(blocks[i] != 0 && blocks[i] != i+1) hamming++;
		}
		return hamming;
	}
	
	// sum of Manhattan distance between blocks and goal 
	public int manhattan() {
		int manhattan = 0;
		int row,col;
		int rowDiff, colDiff;
		for(int i=0;i<blocks.length;i++) {
			if(blocks[i] != 0 && blocks[i] != i+1) {
				row = row(blocks[i]);
				col = col(blocks[i]);
				rowDiff = Math.abs(i/N - row);
				colDiff = Math.abs(i%N - col);
				manhattan += (rowDiff+colDiff);
			}	
		}
		return manhattan;
	}
	
	// is this block the goal block?
	public boolean isGoal() {
		for(int i=0;i<blocks.length;i++) {
			if(blocks[i] != 0 && blocks[i] != i+1) {
				return false;
			}
		}
		return true;
	}
	// exchange two blocks value
	private void exch(int a[],int i, int j) {
		int temp = a[j];
		a[j] = a[i];
		a[i] = temp;
	}
	
	// a block obtained by exchanging two blocks
	public Board twin() {
		Board twinBoard;
		 
		twinBoard.blocks = this.blocks.clone();
		int rand1, rand2;
		rand1 = StdRandom.uniform(N);
		rand2 = StdRandom.uniform(N);
		while(twinBoard.blocks[rand1] == 0 || twinBoard.blocks[rand2] == 0) {
			rand1 = StdRandom.uniform(N);
			rand2 = StdRandom.uniform(N);
		}
		exch(twinBoard.blocks,rand1,rand2);
		
		return twinBoard;
	}
	
	public boolean equals(Object y) {
		if(y == this) return true;
		else if (y == null) return false;
		
		if(y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		return Arrays.equals(blocks, that.blocks);
		
	}
	
	// all neightboring blocks
	public Iterable<Board> neighbors(){
		int i;
		Board neighborBoard;
		Queue<Board> bq = new LinkedList<Board>();
		
	
	
	
	
	
	
	

	}
	
    public String toString() {
        StringBuilder s = new StringBuilder();
        int digit = 0;
        String format;
        
        /* print dimension first */
        s.append(N);
        s.append("\n");

        /* calculate digits of largest number */
        for (int n = blocks.length; n != 0; n /= 10)
            digit++;
        
        /* use digit to format output string */
        format = "%" + digit + "d ";
        for (int i = 0; i < blocks.length; i++) {
            s.append(String.format(format, blocks[i]));
            if ((i+1) % N == 0)
                s.append("\n");
        }
        return s.toString();
    }
	
}
	
