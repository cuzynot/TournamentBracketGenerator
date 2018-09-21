/**
 * [Generator.java]
 * Abstract generator object that outlines necessesities for single and double elimination bracket generators
 * Authors: Yili Liu and Brian Li
 * September 21, 2018
 */

public abstract class Generator {

	Bracket bracket; //Holds (access to) the bracket object made

	/**
	 * getBracket
	 * This method returns the Bracket generated
	 * @return The generated Bracket object
	 */
	public Bracket getBracket() {
		return bracket;
	} //End of getBracket

} //End of class
