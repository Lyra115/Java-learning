package hw3;
/**
 * A Request represents one passenger who asks for an elevator.
 * It stores the floor where the passenger is waiting (source floor),
 * the floor where the passenger wants to go (destination floor) and
 * the time unit at which the request was placed on the queue.
 */
public class Request {
	private int sourceFloor;
	private int destinationFloor;
	private int timeEntered;
	
	/**
     * Constructs a Request whose source floor and destination floor are
     * generated randomly, each between 1 and numFloors (inclusive).
     * The two floors may be equal. The time entered is undefined (0) until
     * it is set with setTimeEntered.
     *
     * @param numFloors
     *    the number of floors in the building; must be at least 1
     * @throws InvalidInputException
     *    if numFloors is less than 1
     */
	public Request(int numFloors) throws InvalidInputException
	{
		if(numFloors<1)
			throw new InvalidInputException("The number of floors must be at least 1.");
		sourceFloor=(int)(Math.random()*numFloors)+1;
		destinationFloor=(int)(Math.random()*numFloors)+1;
		timeEntered=0;
	}
	
	/**
     * Get the floor where the passenger is waiting.
     *
     * @return the source floor
     */
	public int getSourceFloor()
	{
		return sourceFloor;
	}
	
	/**
     * Set the floor where the passenger is waiting.
     *
     * @param sourceFloor
     *    the new source floor
     */
	public void setSourceFloor(int sourceFloor)
	{
		this.sourceFloor=sourceFloor;
	}
	
	/**
     * Get the floor where the passenger wants to go.
     *
     * @return the destination floor
     */
	public int getDestinationFloor()
	{
		return destinationFloor;
	}
	
	/**
     * Set the floor where the passenger wants to go.
     *
     * @param destinationFloor
     *    the new destination floor
     */
	public void setDestinationFloor(int destinationFloor)
	{
		this.destinationFloor=destinationFloor;
	}
	
	/**
     * Get the time unit at which this request was placed on the queue.
     *
     * @return the time entered
     */
	public int getTimeEntered()
	{
		return timeEntered;
	}
	
	/**
     * Set the time unit at which this request was placed on the queue.
     *
     * @param timeEntered
     *    the new time entered
     */
	public void setTimeEntered(int timeEntered)
	{
		this.timeEntered=timeEntered;
	}
}
