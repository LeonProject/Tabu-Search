public class Tools {

	/**
	 * Cost or fitness of an alldifferent constraint
	 * @param sol
	 * @return
	 */
	private static int costAllDifferent(int[] sol) {
		int n = 0;
		for (int i=0; i<sol.length; ++i) {
			for (int j=i+1; j<sol.length; ++j) {
				if (sol[i] == sol[j]) ++n;
			}
		}
		return n;
	}
	
	// Fitness of a solution for the n-queens problem
	public static int fitness(int[] sol) {
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

	

}
