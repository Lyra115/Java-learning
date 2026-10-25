package hw3;

/**
 * The Simulator class carries out the regular elevator simulation.
 * There is one FIFO queue of requests and every elevator handles one passenger at a time.
 * In every time unit: (1) a new request may arrive, (2) every idle elevator takes the
 * oldest request from the queue, (3) every busy elevator moves one floor.
 * The waiting time of a request ends when its elevator is on the source floor.
 */
public class Simulator {
	private static final boolean DEBUG=false;
	
	/**
     * Runs the simulation and prints the total waiting time, the number of
     * requests and the average waiting time (rounded to two decimal places).
     *
     * @param probability
     *    the chance that a request is placed in a time unit (0.0 to 1.0, inclusive)
     * @param floors
     *    the number of floors in the building (greater than 1)
     * @param elevators
     *    the number of elevators in the building (greater than 0)
     * @param length
     *    the length of the simulation in time units (greater than 0)
     * @throws InvalidInputException
     *    if any of the four parameters is outside its valid range
     */
	public static void simulate(double probability,int floors,int elevators,int length)
			throws InvalidInputException
	{
		if(!(probability>=0.0 && probability<=1.0))
			throw new InvalidInputException("The probability must be between 0.0 and 1.0.");
		if(floors<=1)
			throw new InvalidInputException("The number of floors must be greater than 1.");
		if(elevators<=0)
			throw new InvalidInputException("The number of elevators must be greater than 0.");
		if(length<=0)
			throw new InvalidInputException("The length of the simulation must be greater than 0.");
		
		BooleanSource source=new BooleanSource(probability);
		RequestQueue queue=new RequestQueue();
		Elevator[] e=new Elevator[elevators];
		for(int i=0;i<e.length;i++)
			e[i]=new Elevator();
		
		int waits=0;
		int requests=0;
		
		for(int time=1;time<=length;time++)
		{
			int requestBefore=requests;
			Request arrived=null;
			
			// 1. A new request may arrive (at most one per time unit).
			if(source.requestArrived())
			{
				arrived=new Request(floors);
				arrived.setTimeEntered(time);
				queue.enqueue(arrived);
			}
			
			// 2. Every idle elevator takes the oldest waiting request.
			for(int i=0;i<e.length;i++)
			{
				if(e[i].getElevatorState()==Elevator.IDLE && !queue.isEmpty())
				{
					e[i].setRequest(queue.dequeue());
					e[i].setElevatorState(Elevator.TO_SOURCE);
				}
			}
			
			// 3. Every busy elevator moves one floor.
			for(int i=0;i<e.length;i++)
			{
				if(e[i].getElevatorState()==Elevator.TO_SOURCE)
				{
					Request r=e[i].getRequest();
					if(e[i].getCurrentFloor()==r.getSourceFloor())
					{
						// The passenger is picked up: the waiting time ends.
						waits+=time-r.getTimeEntered();
						requests++;
						e[i].setElevatorState(Elevator.TO_DESTINATION);
					}
					else
					{
						move(e[i],r.getSourceFloor());
					}
				}
				
				if(e[i].getElevatorState()==Elevator.TO_DESTINATION)
				{
					Request r=e[i].getRequest();
					if(e[i].getCurrentFloor()!=r.getDestinationFloor())
					{
						move(e[i],r.getDestinationFloor());
					}
					if(e[i].getCurrentFloor()==r.getDestinationFloor())
					{
						e[i].setElevatorState(Elevator.IDLE);
						e[i].setRequest(null);
					}
				}
			}
			if (DEBUG)
			    printStep(time, arrived, queue,e, floors, waits,requests, requestBefore);
		}
		
		
		double average=0.0;
		if(requests>0)
			average=(double)waits/requests;
		
		System.out.println("Total Wait Time: "+waits);
		System.out.println("Total Requests: "+requests);
		System.out.printf("Average Wait Time: %.2f\n",average);
		
		if(requests==0)
			System.out.println("(No elevator reached the source floor of a request.)");
	}
	/**
     * Move an elevator one floor toward a floor.
     *
     * @param e
     *    the elevator to move
     * @param floor
     *    the floor the elevator is heading to
     */
	private static void move(Elevator e,int floor)
	{
		if(e.getCurrentFloor()<floor)
			e.setCurrentFloor(e.getCurrentFloor()+1);
		else if(e.getCurrentFloor()>floor)
			e.setCurrentFloor(e.getCurrentFloor()-1);
	}
	
	/**
     * Print the situation at the end of one time unit (used only when DEBUG is true).
     *
     * @param time
     *    the time unit that just ended
     * @param arrived
     *    the request that arrived in this time unit, or null
     * @param queue
     *    the queue of waiting requests
     * @param e
     *    all elevators
     * @param waits
     *    the total waiting time
     * @param requests
     *    the total requests
     * @param requestBefore
     *    the request count at the start of this time unit
     */
	private static void printStep(int time,Request arrived,RequestQueue queue,
			Elevator[] e,int floors,int waits,int requests,int requestBefore)
	{
		int width=String.valueOf(floors).length()+3;
		if(arrived==null)
			System.out.println("Step " + time + ": no new request");
		else
			System.out.println("Step " + time + ": new request "
		            + arrived.getSourceFloor() + " -> " + arrived.getDestinationFloor());
		
		String head=String.format("%-8s","  floor");
		for(int i=1;i<=floors;i++)
			head+=center(String.valueOf(i),width);
		System.out.println(head);
		
		for(int i=0;i<e.length;i++)
		{
			String l="I";
			String s="idle";
			if(e[i].getElevatorState()==Elevator.TO_SOURCE)
			{
				l="S";
				s="going to floor " + e[i].getRequest().getSourceFloor()
		                + " to pick up (" + e[i].getRequest().getSourceFloor()
		                + " -> " + e[i].getRequest().getDestinationFloor() + ")";
			}
			else if(e[i].getElevatorState()==Elevator.TO_DESTINATION)
			{
				l="D";
				s= "carrying passenger to floor " + e[i].getRequest().getDestinationFloor();
			}
			
			String line=String.format("%-8s","  E" + (i + 1));
			for(int j=1;j<=floors;j++)
			{
				if(j==e[i].getCurrentFloor())
					line+=center("[" +l+ "]", width);
				else
	                line+=center(".", width);
			}
			System.out.println(line + "  " + s);
		}
		String waiting = "empty";
	    if (!queue.isEmpty())
	    {
	        waiting = "";
	        for (int i=0; i<queue.size(); i++)
	            waiting += "(" + queue.get(i).getSourceFloor() + " -> "
	                + queue.get(i).getDestinationFloor() + ") ";
	    }
	    System.out.println("  queue: " + waiting);

	    if (requests != requestBefore)
	        System.out.println("  picked up! total wait = " +waits
	            + ", requests = " + requests);
	    System.out.println();
	}
	
	/**
	
	 * Centers a text in a field of the given width.
	
	 *
	
	 * @param text
	
	 *    the text to center
	
	 * @param width
	
	 *    the width of the field
	
	 * @return the text with spaces on both sides
	
	 */
	
	private static String center(String text, int width)
	{
	    int left=(width-text.length())/2;
	    int right=width-text.length() - left;
	    String result="";
	    for (int i=0;i<left;i++)
	        result+=" ";
	    result += text;
	    for (int i=0; i<right;i++)
	        result+=" ";
	    return result;
	}
}
