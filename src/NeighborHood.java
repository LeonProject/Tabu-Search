import java.util.Iterator;
import java.util.LinkedList;
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
	 * Constructor of NeighborHood
	 * @param solution The solution from which a set of adjacent solutions can be reached
	 * @param domain The domain of the current solution
	 */
	public NeighborHood(int[] solution, IntDomain[] domain){
		this.solution = solution;
		this.domains = domain;
		this.moves = new LinkedList<Move>();
	}

	/**
	 * Determine a new NeighborHood by adding moves
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
	 * This method allow reducing the current neighborhood, removing elements which are also contained in our TabuList
	 * @param tabuList TabuList which contains wrong/forbidden moves
	 */
	public void reduceNeighborHood(TabuList tabuList){
		Iterator<Move> it = moves.iterator();

		while(it.hasNext())
		{
			Move moveElem = it.next();
			if(tabuList.isTabuElem(moveElem))
			{
				it.remove();
			}
		}
	}

	/**
	 * This method returns a triple which contains the best neighborhood, the best cost and the best move found.
	 * This solution will be used in the tabu search 
	 * @return result A Result which contains the best neighborhood, the best cost and the best move found
	 */
	public BestCandidate getBestPossibleSolution(){
		BestCandidate result = new BestCandidate();

		int bestSolutionCostFound = Integer.MAX_VALUE;
		int[] bestSolutionFound = null;
		Move bestMoveFound = null;

		for(Move m : moves){
			int [] solutionFound = new int[solution.length];
			System.arraycopy(solution, 0, solutionFound, 0, solution.length);

			solutionFound[m.getVariable()] = m.getValue();
			int currentCost = Tools.fitness(solutionFound);

			if( currentCost < bestSolutionCostFound ){
				bestSolutionFound = solutionFound;
				bestSolutionCostFound = currentCost;
				bestMoveFound = m;
			}

		}
		result.setSolution(bestSolutionFound);
		result.setCost(bestSolutionCostFound);
		result.setMove(bestMoveFound);
		
		//System.out.println(result.toString());

		return result;
	}

}





