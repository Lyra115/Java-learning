package hw3;
/**
 * Thrown when dequeue is called on an empty RequestQueue.
 */
@SuppressWarnings("serial")
public class EmptyQueueException extends RuntimeException
{
	/**
     * Constructs an exception with a message describing the problem.
     *
     * @param e
     *    a description of the problem
     */
	public EmptyQueueException(String e)
    {
        super(e);
    }
}
