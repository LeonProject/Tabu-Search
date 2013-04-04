import java.util.LinkedList;
import java.util.Queue;


/**
 * This class provides an implementation for TabuList
 * @author Saren
 *
 */

public class TabuList {
	private int maxSize;
	private Queue<Move> tabuMoves;

	/**
	 * Constructor of tabulist
	 * @param maxsize The size max of the TabuList
	 */
	public TabuList(int maxsize){
		this.maxSize = maxsize;
		this.tabuMoves = new LinkedList<Move>();
	}

	/**
	 * Adding an element to the TabuList
	 * @param move
	 */
	public void addTabuElement(Move move){
		if(!tabuMoves.contains(move)){
			if(tabuMoves.size() >= maxSize){
				tabuMoves.poll();
			}
			tabuMoves.add(move);
		}
	}

	/**
	 * 
	 * @param move The move to identity
	 * @return True if the tabulist contains the specified move, false otherwise
	 */
	public boolean isTabuElem(Move move){
		return this.tabuMoves.contains(move);
	}

	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
