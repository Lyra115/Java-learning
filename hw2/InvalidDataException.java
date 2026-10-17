/*
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:25数媒2班
 */

package hw2;

/**
 * Thrown when data is invalid.
 * Examples: unformed rfid tag, bad location, non-positive price,
 * name too long, illegal move, or invalid cart number.
 */
public class InvalidDataException extends RuntimeException
{
	/**
     * Constructs an exception with a String describing what was invalid.
     *
     * @param e
     *    a description of the problem that is shown to the user
     */
	public InvalidDataException(String e)
	{
		super(e);
	}
}
