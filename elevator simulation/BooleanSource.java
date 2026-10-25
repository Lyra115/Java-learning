package hw3;
/**
 * A BooleanSource produces random true/false answers.
 * Each call to requestArrived returns true with a fixed probability.
 * It is used to decide whether a new request is placed in a time unit.
 */
public class BooleanSource {
	private double probability;
	
	/**
     * Constructs a BooleanSource.
     *
     * @param probability
     *    the chance (between 0.0 and 1.0, inclusive) that requestArrived returns true
     * @throws InvalidInputException
     *    if probability is not between 0.0 and 1.0 inclusive
     */
	public BooleanSource(double probability) throws InvalidInputException
	{
		if(!(probability>=0.0 && probability<=1.0))
			throw new InvalidInputException("The probability must be between 0.0 and 1.0.");
		this.probability=probability;
	}
	
	/**
	
	 * Tells whether a new request has arrived.
	
	 *
	
	 * @return true with a chance equal to probability, otherwise false
	
	 */
	
	public boolean requestArrived()
	{
		return Math.random()<probability;
	}
}
