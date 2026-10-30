package hw3;
import java.util.Scanner;
/**
 * The Analyzer class contains the main method of the elevator simulation.
 * It asks the user, on separate lines, for the four parameters of the
 * simulation, checks that each one is in its valid range (and asks again if
 * it is not), asks which simulation must be run (regular or optimal) and
 * then runs it.
 */
public class Analyzer {
	/**
     * Read the parameters from the keyboard and runs the simulation.
     *
     * @param args
     *    not used
     */
	public static void main(String[] args)
	{
		Scanner input=new Scanner(System.in);
		System.out.println("Welcome to the Elevator simulator!");
        System.out.println();
        
        Double probability=readProbability(input,
                "Please enter the probability of arrival for Requests: ");
        if (probability==null)
            return;
        Integer floors=readInt(input, "Please enter the number of floors: ", 2,
            "The number of floors must be an integer greater than 1.");
        if (floors==null)
            return;
        Integer elevators=readInt(input, "Please enter the number of elevators: ", 1,
            "The number of elevators must be an integer greater than 0.");
        if (elevators==null)
            return;
        Integer length=readInt(input,
            "Please enter the length of the simulation (in time units): ", 1,
            "The length of the simulation must be an integer greater than 0.");
        if (length == null)
            return;
        
        // (extra credit)
        boolean optimal = readMethod(input);

        System.out.println();
        if (optimal)
            OptimalSimulator.simulate(probability, floors, elevators, length);
        else
            Simulator.simulate(probability, floors, elevators, length);

	}
	
	/**
     * Ask for a probability until a number between 0.0 and 1.0 is typed.
     *
     * @param input
     *    the Scanner to read from
     * @param s
     *    the text shown before each attempt
     * @return the probability, or null if there is no more input
     */
	private static Double readProbability(Scanner input,String s)
	{
		while(true)
		{
			System.out.print(s);
			if(!input.hasNextLine())
				return null;
			String line=input.nextLine().trim();
			try
			{
				double probability=Double.parseDouble(line);
				if(probability>=0.0 && probability<=1.0)
					return probability;
			}catch(NumberFormatException e)
			{
				
			}
			System.out.println("The probability must be a number between 0.0 and 1.0.");
		}
	}
	/**
     * Ask for an integer until one that is at least min is typed.
     *
     * @param input
     *    the Scanner to read from
     * @param s
     *    the text shown before each attempt
     * @param min
     *    the smallest value that is allowed
     * @param message
     *    the message shown when the input is not valid
     * @return the integer, or null if there is no more input
     */
	private static Integer readInt(Scanner input,String s,int min,String message)
	{
		while(true)
		{
			System.out.print(s);
			if(!input.hasNextLine())
				return null;
			String line=input.nextLine().trim();
			try
			{
				int value=Integer.parseInt(line);
				if(value>=min)
					return value;
			}catch(NumberFormatException e)
			{
				
			}
			System.out.println(message);
		}
	}

	/**
     * Ask which simulation must be run. 
     * Only 0 and 1 are accepted.
     * An empty answer (or no more input) selects the regular simulation.
     *
     * @param input
     *    the Scanner to read from
     * @return true for the optimal simulation, false for the regular simulation
     */
	private static boolean readMethod(Scanner input)
	{
		System.out.println();
		System.out.println("Which simulation do you want to run?");
        System.out.println("  0 - regular simulation (one passenger per elevator)");
        System.out.println("  1 - optimal simulation (many passengers, up and down)");
        
        while(true)
        {
        	System.out.print("Please enter 0 or 1 (just press Enter for 0): ");
        	if(!input.hasNextLine())
        		return false;
        	String line=input.nextLine().trim();
        	if(line.equals("") || line.equals("0"))
        		return false;
        	if(line.equals("1"))
        		return true;
        	System.out.println("Please type 0 for regular or 1 for optimal.");
        }
	}
}
