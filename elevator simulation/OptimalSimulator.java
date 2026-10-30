package hw3;
import java.util.ArrayList;

/**
 * The OptimalSimulator class carries out the optimal elevator simulation (extra credit).
 *
 * Differences from the regular Simulator: an elevator has a direction (up or down),
 * it can carry an unlimited number of passengers, and it picks up passengers
 * on the way. Waiting passengers are kept in one list for every floor.
 * 
 * Algorithm used to service the requests (a "scan" algorithm, like a real elevator):
 *   1. With the given probability a new request is added to the list of its source floor.
 *   2. Every elevator stops at the floor it is on:
 *      a. All its passengers whose destination is this floor get out.
 *      b. If the elevator is moving, every waiting passenger on this floor who wants
 *         to go in the direction of the elevator gets in. The waiting time of each
 *         of them (current time unit - time entered) is added to the waiting sum
 *         and the request count is increased. A passenger whose destination is
 *         the source floor is served by any elevator that stops there.
 *      c. A moving elevator keeps its direction as long as it has a request
 *         (a passenger inside, or a waiting passenger on any floor) in that direction.
 *         When it has none, it becomes idle.
 *      d. An idle elevator with waiting passengers on its floor takes the direction
 *         of the oldest of them and picks up all passengers who go that way.
 *   3. Every elevator moves one floor:
 *      a. A moving elevator moves one floor in its direction.
 *      b. An idle elevator may move in any direction: it heads for the nearest floor
 *         that has waiting passengers and that no other elevator is already heading to.
 *         If there is no such floor it stays idle.
 *         
 * Elevator directions are kept in a boolean array (true = up, false = down).
 */
public class OptimalSimulator
{
	/** Set to true to print every step of the simulation. */
	private static final boolean DEBUG=false;

	/**
	 * Runs the optimal simulation and prints the total waiting time, the
	 * number of requests picked up and the average waiting time (rounded to two
	 * decimal places).
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
	public static void simulate(double probability, int floors, int elevators, int length)
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

		ArrayList<ArrayList<Request>> waiting=new ArrayList<ArrayList<Request>>();
		for(int f=0;f<=floors;f++)
			waiting.add(new ArrayList<Request>());

		int[] floorOf=new int[elevators];
		boolean[] up=new boolean[elevators];     // true = up, false = down
		boolean[] idle=new boolean[elevators];
		int[] target=new int[elevators];         // floor an elevator left to pick up (0 = none)
		ArrayList<ArrayList<Request>> riders=new ArrayList<ArrayList<Request>>();
		for(int e=0;e<elevators;e++)
		{
			floorOf[e]=1;
			up[e]=true;
			idle[e]=true;
			target[e]=0;
			riders.add(new ArrayList<Request>());
		}

		// stats[0] = waiting sum, stats[1] = requests picked up, stats[2] = requests delivered
		long[] stats=new long[3];
		long generated=0;

		for(int time=1;time<=length;time++)
		{
			// 1. A new request may arrive (at most one per time unit).
			Request arrived=null;
			if(source.requestArrived())
			{
				arrived=new Request(floors);
				arrived.setTimeEntered(time);
				waiting.get(arrived.getSourceFloor()).add(arrived);
				generated++;
			}

			// 2. Every elevator stops at its floor.
			for(int e=0;e<elevators;e++)
			{
				int f=floorOf[e];
				ArrayList<Request> here=waiting.get(f);
				ArrayList<Request> inside=riders.get(e);

				// a. passengers get out
				int k=0;
				while(k<inside.size())
				{
					if(inside.get(k).getDestinationFloor()==f)
					{
						inside.remove(k);
						stats[2]++;
					}
					else
						k++;
				}
				if(target[e]==f)
					target[e]=0;

				// b. a moving elevator picks up passengers going its way
				if(!idle[e])
					board(here, inside, up[e], time, stats);

				// c. a moving elevator with no request ahead becomes idle
				if(!idle[e] && !requestAhead(waiting, inside, f, up[e], floors))
				{
					idle[e]=true;
					target[e]=0;
				}

				// d. an idle elevator picks up the passengers waiting on its floor
				if(idle[e] && !here.isEmpty())
				{
					boolean goUp=true;
					for(int j=0;j<here.size();j++)
					{
						Request r=here.get(j);
						if(r.getDestinationFloor()!=r.getSourceFloor())
						{
							goUp=r.getDestinationFloor()>r.getSourceFloor();
							break;
						}
					}
					if(board(here, inside, goUp, time, stats)>0)
					{
						idle[e]=false;
						up[e]=goUp;
					}
				}
			}

			// 3. Every elevator moves one floor.
			for(int e=0;e<elevators;e++)
			{
				if(!idle[e])
				{
					floorOf[e]+=up[e]?1:-1;
				}
				else
				{
					int best=nearestWaitingFloor(waiting, floorOf[e], floors, e, idle, target);
					if(best!=0)
					{
						target[e]=best;
						up[e]=best>floorOf[e];
						idle[e]=false;
						floorOf[e]+=up[e]?1:-1;
					}
				}
			}

			if(DEBUG)
				printStep(time, arrived, waiting, riders, floorOf, up, idle, stats);
		}

		if(DEBUG)
		{
			long stillWaiting=0;
			long stillRiding=0;
			for(int f=1;f<=floors;f++)
				stillWaiting+=waiting.get(f).size();
			for(int e=0;e<elevators;e++)
				stillRiding+=riders.get(e).size();
			System.out.println("Generated="+generated+" PickedUp="+stats[1]
				+" Delivered="+stats[2]+" Riding="+stillRiding
				+" Waiting="+stillWaiting
				+" Consistent="+(generated==stats[2]+stillRiding+stillWaiting));
		}

		double average=0.0;
		if(stats[1]>0)
			average=(double)stats[0]/stats[1];

		System.out.println("Total Wait Time: "+stats[0]);
		System.out.println("Total Requests: "+stats[1]);
		System.out.printf( "Average Wait Time: %.2f\n", average);
		if(stats[1]==0)
			System.out.println("(No elevator reached the source floor of a request.)");
	}

	/**
	 * Lets waiting passengers get into an elevator. A passenger gets in if
	 * he wants to go in the given direction, or if his destination is his
	 * source floor (he is served at once and does not ride).
	 *
	 * @param here
	 *    the passengers waiting on the floor (the ones who get in are removed)
	 * @param inside
	 *    the passengers inside the elevator (the ones who ride are added)
	 * @param directionUp
	 *    the direction of the elevator (true = up)
	 * @param time
	 *    the current time unit
	 * @param stats
	 *    stats[0] = waiting sum, stats[1] = requests picked up, stats[2] = requests delivered
	 * @return the number of passengers who got in and have to ride
	 */
	private static int board(ArrayList<Request> here, ArrayList<Request> inside,
		boolean directionUp, int time, long[] stats)
	{
		int riding=0;
		int i=0;
		while(i<here.size())
		{
			Request r=here.get(i);
			boolean sameFloor=r.getDestinationFloor()==r.getSourceFloor();
			boolean wantsUp=r.getDestinationFloor()>r.getSourceFloor();
			if(sameFloor || wantsUp==directionUp)
			{
				here.remove(i);
				stats[0]+=time-r.getTimeEntered();
				stats[1]++;
				if(sameFloor)
					stats[2]++;
				else
				{
					inside.add(r);
					riding++;
				}
			}
			else
				i++;
		}
		return riding;
	}

	/**
	 * Tells whether an elevator still has a request (a passenger inside, or a
	 * waiting passenger on some floor) in the direction it is moving.
	 *
	 * @param waiting
	 *    the lists of waiting passengers of all floors
	 * @param inside
	 *    the passengers inside the elevator
	 * @param floor
	 *    the floor of the elevator
	 * @param directionUp
	 *    the direction of the elevator (true = up)
	 * @param floors
	 *    the number of floors in the building
	 * @return true if the elevator must keep moving in its direction
	 */
	private static boolean requestAhead(ArrayList<ArrayList<Request>> waiting,
		ArrayList<Request> inside, int floor, boolean directionUp, int floors)
	{
		if(!inside.isEmpty())
			return true;
		if(directionUp)
		{
			for(int f=floor+1;f<=floors;f++)
				if(!waiting.get(f).isEmpty())
					return true;
		}
		else
		{
			for(int f=floor-1;f>=1;f--)
				if(!waiting.get(f).isEmpty())
					return true;
		}
		return false;
	}

	/**
	 * Finds the nearest floor with waiting passengers that no other elevator is
	 * already heading to. When two floors are equally near, the lower one is chosen.
	 *
	 * @param waiting
	 *    the lists of waiting passengers of all floors
	 * @param floor
	 *    the floor of the idle elevator
	 * @param floors
	 *    the number of floors in the building
	 * @param self
	 *    the index of the idle elevator
	 * @param idle
	 *    which elevators are idle
	 * @param target
	 *    the floor every elevator is heading to (0 = none)
	 * @return the floor found, or 0 if there is none
	 */
	private static int nearestWaitingFloor(ArrayList<ArrayList<Request>> waiting,
		int floor, int floors, int self, boolean[] idle, int[] target)
	{
		for(int d=1;d<floors;d++)
		{
			int[] candidates={floor-d, floor+d};
			for(int c=0;c<candidates.length;c++)
			{
				int f=candidates[c];
				if(f<1 || f>floors || waiting.get(f).isEmpty())
					continue;
				boolean claimed=false;
				for(int other=0;other<idle.length;other++)
					if(other!=self && !idle[other] && target[other]==f)
						claimed=true;
				if(!claimed)
					return f;
			}
		}
		return 0;
	}

	/**
	 * Draws the situation at the end of one time unit (used only when DEBUG is true).
	 * The first row shows how many passengers wait on each floor. Then every
	 * elevator is shown as a track with one cell per floor. The cell where the
	 * elevator is shows [^] going up, [v] going down or [I] idle.
	 *
	 * @param time
	 *    the time unit that just ended
	 * @param arrived
	 *    the request that arrived in this time unit, or null
	 * @param waiting
	 *    the lists of waiting passengers of all floors
	 * @param riders
	 *    the passengers inside every elevator
	 * @param floorOf
	 *    the floor of every elevator
	 * @param up
	 *    the direction of every elevator
	 * @param idle
	 *    which elevators are idle
	 * @param stats
	 *    the waiting sum and the request counts
	 */
	private static void printStep(int time, Request arrived, ArrayList<ArrayList<Request>> waiting,
		ArrayList<ArrayList<Request>> riders, int[] floorOf, boolean[] up, boolean[] idle,
		long[] stats)
	{
		int floors=waiting.size()-1;
		int width=String.valueOf(floors).length()+3;   // width of one cell

		if(arrived==null)
			System.out.println("Step "+time+": no new request");
		else
			System.out.println("Step "+time+": new request "
				+arrived.getSourceFloor()+" -> "+arrived.getDestinationFloor());

		String header=String.format("%-8s", "  floor");
		for(int f=1;f<=floors;f++)
			header+=center(String.valueOf(f), width);
		System.out.println(header);

		// how many passengers are waiting on each floor
		String line=String.format("%-8s", "  wait");
		String details="";
		for(int f=1;f<=floors;f++)
		{
			int n=waiting.get(f).size();
			line+=center(n==0?".":String.valueOf(n), width);
			for(int j=0;j<n;j++)
				details+="("+f+" -> "+waiting.get(f).get(j).getDestinationFloor()+") ";
		}
		if(details.equals(""))
			details="nobody waiting";
		System.out.println(line+"  "+details);

		for(int e=0;e<floorOf.length;e++)
		{
			String symbol="I";
			String note="idle";
			if(!idle[e])
			{
				symbol=up[e]?"^":"v";
				note=up[e]?"going up":"going down";
			}
			if(!riders.get(e).isEmpty())
			{
				note+=", passengers inside go to floor";
				for(int j=0;j<riders.get(e).size();j++)
					note+=" "+riders.get(e).get(j).getDestinationFloor();
			}
			line=String.format("%-8s", "  E"+(e+1));
			for(int f=1;f<=floors;f++)
			{
				if(f==floorOf[e])
					line+=center("["+symbol+"]", width);
				else
					line+=center(".", width);
			}
			System.out.println(line+"  "+note);
		}
		System.out.println("  total wait = "+stats[0]+", requests picked up = "+stats[1]);
		System.out.println();
	}

	/**
	 * Centers a text in a field of the given width.
	 *
	 * @param text
	 *    the text to center
	 * @param width
	 *    the width of the field (at least the length of the text)
	 * @return the text with spaces on both sides
	 */
	private static String center(String text, int width)
	{
		int left=(width-text.length())/2;
		int right=width-text.length()-left;
		String result="";
		for(int i=0;i<left;i++)
			result+=" ";
		result+=text;
		for(int i=0;i<right;i++)
			result+=" ";
		return result;
	}
}
