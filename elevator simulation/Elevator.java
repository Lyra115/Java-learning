package hw3;

/**
 * An Elevator is one elevator of the building. It remembers the floor it is on,
 * its state (IDLE, TO_SOURCE or TO_DESTINATION) and the request it is handling.
 * An elevator handles only one request at a time.
 */
public class Elevator {
	
	/** State of an elevator that is not handling a request. */
	public static final int IDLE=0;
	
	 /** State of an elevator that is moving to the floor of its passenger. */
	public static final int TO_SOURCE=1;
	
	/** State of an elevator that is carrying its passenger to the destination. */
	public static final int TO_DESTINATION=2;
	
	private int currentFloor;
	private int elevatorState;
	private Request request;
	
	/**
     * Constructs an idle Elevator on floor 1 that has no request.
     */
	public Elevator()
	{
		request=null;
		elevatorState=IDLE;
		currentFloor=1;
	}
	
	/**
     * Get the floor the elevator is on.
     *
     * @return the current floor
     */
	public int getCurrentFloor()
	{
		return currentFloor;
	}
	
	/**
     * Sets the floor the elevator is on.
     *
     * @param currentFloor
     *    the new floor; must be at least 1
     * @throws InvalidInputException
     *    if currentFloor is less than 1
     */
	public void setCurrentFloor(int currentFloor)throws InvalidInputException
	{
		if(currentFloor<1)
			throw new InvalidInputException("A floor number must be at least 1.");
		this.currentFloor=currentFloor;
	}
	
	/**
     * Get the state of the elevator.
     *
     * @return IDLE, TO_SOURCE or TO_DESTINATION
     */
	public int getElevatorState()
	{
		return elevatorState;
	}
	
	/**
     * Set the state of the elevator.
     *
     * @param elevatorState
     *    the new state: IDLE, TO_SOURCE or TO_DESTINATION
     * @throws InvalidInputException
     *    if elevatorState is not one of the three constants
     */
	 public void setElevatorState(int elevatorState)throws InvalidInputException
	 {
		 if(elevatorState!=IDLE && elevatorState!=TO_SOURCE && elevatorState!=TO_DESTINATION)
			 throw new InvalidInputException("Invalid elevator state.");
		 this.elevatorState=elevatorState;
	 }
	 
	 /**
	  * Get the request the elevator is handling.
	  *
	  * @return the request, or null if the elevator is idle
	  */
	 public Request getRequest()
	 {
		 return request;
	 }
	 
	 /**
	  * Set the request the elevator is handling.
	  *
	  * @param request
	  *    the new request, or null if the elevator is idle
	  */
	 public void setRequest(Request request)
	 {
		 this.request=request;
	 }
}
