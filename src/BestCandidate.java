import java.util.Arrays;

/**
 * This class provides an implementation for a Results which contains the best neighborhood, the best solution and the best move
 * @author Saren
 *
 */
public class BestCandidate {

	private Move move;
	private int[] solution;
	private int cost;

	public BestCandidate(){}
	
	public BestCandidate(Move move, int[] solution, int cost) {
		super();
		this.move = move;
		this.solution = solution;
		this.cost = cost;
	}
		
	/**
	 * @return the move
	 */
	public Move getMove() {
		return move;
	}
	/**
	 * @param move the move to set
	 */
	public void setMove(Move move) {
		this.move = move;
	}
	/**
	 * @return the solution
	 */
	public int[] getSolution() {
		return solution;
	}
	/**
	 * @param solution the solution to set
	 */
	public void setSolution(int[] solution) {
		this.solution = solution;
	}
	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BestCandidate [move=" + move.toString() + ", solution="
				+ Arrays.toString(solution) + ", cost=" + cost + "]";
	}
}
