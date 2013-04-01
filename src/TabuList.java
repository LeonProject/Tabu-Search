import java.util.ArrayList;
import java.util.List;


/**
 * This class provides an implementation for TabuList
 * @author Saren
 *
 */

public class TabuList {
		private int maxSize;
		private List<Move> tabuMoves;
		
		/**
		 * Constructor
		 * @param maxsize The size max of the TabuList
		 */
		public TabuList(int maxsize){
			this.maxSize = maxsize;
			this.tabuMoves = new ArrayList<Move>();
		}

		/**
		 * Adding an element to the TabuList
		 * @param move
		 */
		public void addTabuElement(Move move){
			if(!tabuMoves.contains(move)){
					tabuMoves.add(move);
			}
		}
		
		/**
		 * 
		 * @param move The move to identiy
		 * @return True if the move
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
