import java.util.ArrayList;
import java.util.List;

import JaCoP.core.IntDomain;
import JaCoP.core.ValueEnumeration;


/**
 * This class provides an implementation for NeighboorHood class
 * @author Florian
 *
 */
public class NeighborHood {
		private int[] solution;
		private IntDomain[] domains;
		private List<Move> moves;
		
		/**
		 * Constructor
		 * @param solution The solution from which a set of adjacent solutions can be reached
		 * @param domain The domain of the current solution
		 */
		public NeighborHood(int[] solution, IntDomain[] domain){
			this.solution = solution;
			this.domains = domain;
			this.moves = new ArrayList<Move>();
		}
		
		/**
		 * Determine a new NeighborHood 
		 */
		public void determineNeighboor(){
				
			for (int i=0; i<this.solution.length; i++){
				ValueEnumeration values = this.domains[i].valueEnumeration();
				
				for (int j=0; j<this.domains[i].getSize();j++){
					int valueElem = values.nextElement();
					
					//We keep only values which are different to the solution 
					if(valueElem != this.solution[i]){
						Move move = new Move(i, valueElem);
						this.moves.add(move);
					}
				}
			}
		}
		
		/**
		 * This method allow to reduce the current neighborhood, deleting elements which are also contained in the TabuList
		 * @param tabuList TabuList which contains wrong/forbidden moves
		 */
		public void reduceNeighborHood(TabuList tabuList){
			for (Move move : this.moves){
				if(tabuList.isTabuElem(move)){
					System.out.println(this.moves.toString());
					this.moves.remove(move);
				}
			}
		}
		
		/* TODO : ALgo
		 * 
		 * Move BestMove = NULL;
		 * int bestNeighborCost = Integer.MAX_VALUE;
		 * int[] bestNeighborHood = null;
		 * 
		 * for(Move move : moves){
		 * 		//int [] neighbor = new int[solution.length];
	 	 *		
		 *		//neighbor[variable] = value
		 *		//Evaluer coût neighbor   int cost = neighbor.fitness();
		 *		
		 *		if(neighborCost < bestNeighborCost)
		 *		{
		 *			bestMove = move;
		 *			bestNeighborCost = neighborCost;
		 *			bestNeighbor = neighbor;
		 *		}
		 *		
		 *		//Retourner meilleur voisinage, le meilleur déplacement et le meilleur coût
		 *
		 *			}
		 */
}
