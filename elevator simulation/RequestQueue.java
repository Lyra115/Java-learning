package hw3;
import java.util.ArrayList;
/**
 * A RequestQueue is a standard first-in first-out (FIFO) queue of Request objects.
 * It is a subclass of the Java API class ArrayList. 
 * The front of the queue is index 0 of the list and the rear of the queue is the last index.
 */
@SuppressWarnings("serial")
public class RequestQueue extends ArrayList<Request>
{

	/**
     * Construct an empty RequestQueue.
     */
	public RequestQueue()
	{
		super();
	}
	
	/**
     * Add a request to the rear of the queue.
     *
     * @param request
     *    the request to add; must not be null
     * @throws InvalidInputException
     *    if request is null
     */
	public void enqueue(Request request)throws InvalidInputException
	{
		if(request==null)
			throw new InvalidInputException("The enqueue request cannot be null.");
		add(request);
	}
	
	/**
     * Remove and return the request at the front of the queue.
     *
     * @return the request that was at the front of the queue
     * @throws EmptyQueueException
     *    if the queue is empty
     */
	public Request dequeue()throws EmptyQueueException
	{
		if(isEmpty())
			throw new EmptyQueueException("Cannot dequeue from an empty queue.");
		return remove(0);
	}
	
	/**
     * Get the number of requests in the queue.
     *
     * @return the number of requests in the queue
     */
	@Override
	public int size()
	{
		return super.size();
	}
	
	/**
     * Test whether the queue is empty.
     *
     * @return true if the queue contains no requests
     */
	@Override
	public boolean isEmpty()
	{
		return super.isEmpty();
	}
}
