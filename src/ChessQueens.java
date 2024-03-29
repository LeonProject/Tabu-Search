import java.util.Arrays;
import java.util.Random;

import JaCoP.constraints.Alldifferent;
import JaCoP.constraints.XplusCeqZ;
import JaCoP.core.IntDomain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.ValueEnumeration;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainMedian;
import JaCoP.search.SelectChoicePoint;
import JaCoP.search.SimpleSelect;
import JaCoP.search.SmallestDomain;

public class ChessQueens {
	private Store store;
	private IntVar[] Q;		// main variables: Q[i] represents the column of the queen on the i-th row 

	public ChessQueens(int n) {
		store = new Store();
		Q = new IntVar[n];
		IntVar[] y = new IntVar[n];
		IntVar[] z = new IntVar[n];

		for (int i=0; i<n; ++i) {
			Q[i] = new IntVar(store,"Q" + i,0,n-1);
			y[i] = new IntVar(store,"y" + i,-i,n-1-i);
			z[i] = new IntVar(store,"z" + i,i,n-1+i);

			store.impose(new XplusCeqZ(Q[i],i,z[i]));
			store.impose(new XplusCeqZ(y[i],i,Q[i]));
		}

		// all different: no attack on columns
		store.impose(new Alldifferent(Q));
		store.impose(new Alldifferent(y));
		store.impose(new Alldifferent(z));
	}

	/**
	 * 
	 * @return tab The domain of the main variables
	 */
	public IntDomain[] getDomains() {
		IntDomain[] tab = new IntDomain[Q.length];
		for (int i=0; i<Q.length; ++i) {
			tab[i] = Q[i].domain;
			System.out.println("Domain de tab["+ i +"] : " + tab[i]);
		}
		return tab;
	}

	/**
	 * Generate randomly a solution within the domains
	 * @param domains The domain in which we generate randomly a solution
	 * @return solution The generated ramdomly solution
	 */
	public int[] generateSolution(IntDomain[] domains) {
		Random rand = new Random();
		int[] solution = new int[domains.length];

		for (int i=0; i<domains.length; ++i) {
			ValueEnumeration values = domains[i].valueEnumeration();
			int r = rand.nextInt(domains[i].getSize());   // 0 .. getSize()-1

			for (int j=0; j<=r; ++j) {
				solution[i] = values.nextElement();  // only the r-th is relevant
			}
		}

		//Print for display
		System.out.print("Generated Solution : [" + solution[0]);
		for (int i = 1; i<solution.length ;i++) {
			System.out.print(", ");
			System.out.print(solution[i]);
		}
		System.out.print("]");
		System.out.println("\n");

		return solution;
	}

	/**
	 * Cost or fitness of an alldifferent constraint
	 * @param sol
	 * @return
	 */
	public int costAllDifferent(int[] sol) {
		int n = 0;
		for (int i=0; i<sol.length; ++i) {
			for (int j=i+1; j<sol.length; ++j) {
				if (sol[i] == sol[j]) ++n;
			}
		}
		return n;
	}

	// Fitness of a solution for the n-queens problem
	public int fitness(int[] sol) {
		int n = 0;

		// allDifferent on Q
		n += costAllDifferent(sol);

		// allDifferent on y
		int[] aux = new int[sol.length];
		for (int i=0; i<sol.length; ++i) {
			aux[i] = sol[i] + i;
		}
		n += costAllDifferent(aux);

		// allDifferent on z
		for (int i=0; i<sol.length; ++i) {
			aux[i] = sol[i] - i;
		}
		n += costAllDifferent(aux);

		return n;
	}

	// Display a solution
	public static void printSolution(int[] sol) {
		System.out.print("{");
		for (int i=0; i<sol.length; ++i) {
			if (i!=0) System.out.print(", ");
			System.out.print(sol[i]);
		}
		System.out.print("}");
	}


	public boolean tabuSearch(int maxIteration, int tabuSize) {

		// Generate a first solution
		IntDomain[] domains = getDomains();
		int[] currentSol = generateSolution(domains);
		int[] bestSol = currentSol; //best known solution
		TabuList tabuList = new TabuList(tabuSize);

		//Cost of the current solution
		int currentCost = fitness(currentSol);
		int bestCost = currentCost;
		int nbIte = 0; 

		while(currentCost > 0 && nbIte < maxIteration){
			nbIte++;
		
			NeighborHood neighborHood = new NeighborHood(currentSol, domains);
			
			//Verifs à faire
			try{
			neighborHood.determineNeighboor();
			}
			catch(Exception e){
				return false;
			}
			neighborHood.reduceNeighborHood(tabuList);

			//Get BestCandidate from Neighborhood class
			BestCandidate bestCandidate = neighborHood.getBestPossibleSolution();
			currentCost = bestCandidate.getCost();
			currentSol = bestCandidate.getSolution();

			//Add the move to tabuList
			tabuList.addTabuElement(bestCandidate.getMove());

			if(currentCost < bestCost)
			{
				bestSol = currentSol;
				bestCost = currentCost;
			}
			
			System.out.println("*****************************************************************************");
			System.out.println("Meilleure solution : " + Arrays.toString(bestSol));
			System.out.println("Coût : " + bestCost);
			System.out.println("Nombre d'itérations : " + nbIte);
			
		}
		
		System.out.println("Solution Finale : " + Arrays.toString(bestSol));
		return bestCost == 0;
		
	}

	public boolean completeSearch() {
		DepthFirstSearch<IntVar> search = new DepthFirstSearch<IntVar>();

		search.getSolutionListener().searchAll(true);
		search.getSolutionListener().recordSolutions(true);

		SelectChoicePoint<IntVar> select =
				new SimpleSelect<IntVar>(Q,
						new SmallestDomain<IntVar>(),
						new IndomainMedian<IntVar>());

		boolean result = search.labeling(store, select);

		for (int i=1; i<=search.getSolutionListener().solutionsNo(); i++){
			System.out.print("Solution " + i + ": [");
			for (int j=0; j<search.getSolution(i).length; j++) {
				if (j!=0) System.out.print(", ");
				System.out.print(search.getSolution(i)[j]);
			}
			System.out.println("]");
		}

		return result;
	}

	public static void main(String[] args) {
		final int n = 100;
		ChessQueens model = new ChessQueens(n);

		int maxIteration = 100;
		int tabuSize = 25;

		//boolean result = model.completeSearch();

		boolean result2 = model.tabuSearch(maxIteration, tabuSize);
		if(result2){
			System.out.println("Une solution optimale a été trouvé !");
		}
		else {
			System.out.println("Une solution non-optimale a été trouvé !");
		}

	}

}
