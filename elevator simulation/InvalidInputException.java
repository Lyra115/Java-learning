package hw3;

/**
 * Thrown when a value given to the elevator simulation is outside its valid range,
 * for example a probability greater than 1.0 or a building with less than 2 floors.
 */
public class InvalidInputException extends RuntimeException
{
	/**
     * Constructs an exception with a String describing what was invalid.
     *
     * @param e
     *    a description of the problem
     */
	public InvalidInputException(String e)
	{
		super(e);
	}
}
