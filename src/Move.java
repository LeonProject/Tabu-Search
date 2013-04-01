
/**
 * This class provides an implementation for a Move
 * @author Florian
 *
 */
//TODO : Modifier variable et rajouter variables moveFrom et moveTo -> Modif equals + Ajout Get/set 
public class Move {
	private int variable;
	private int value;
	
	/**
	 * Constructor
	 * @param variable The variable of the Move
	 * @param value The value of the Move
	 */
	public Move(int variable, int value){
		this.variable = variable;
		this.value = value;
	}

	/**
	 * Variable Getter
	 * @return the variable
	 */
	public int getVariable() {
		return variable;
	}

	/**
	 * Variable Setter
	 * @param variable the variable to set
	 */
	public void setVariable(int variable) {
		this.variable = variable;
	}

	/**
	 * Value Getter
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Value Setter
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if(this.getClass() == object.getClass()){
			Move tmp = (Move) object;
			return(this.variable == tmp.getVariable() && this.value == this.getValue());
		}
		
		return false;
	}
	
	
	
	
}
