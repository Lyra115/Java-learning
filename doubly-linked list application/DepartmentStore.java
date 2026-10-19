/*
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:25数媒2班
 */

package hw2;
/**
 * DepartmentStore contains the main method that provides a menu with following options.
 * and lets the user interact with an ItemList.
 *
 * Menu options (case insensitive):
 *   C - Clean store            I - Insert an item into the list
 *   L - List by location       M - Move an item in the store
 *   O - Checkout               P - Print all items in store
 *   R - Print by RFID number   U - Update inventory system
 *   Q - Exit the program
 *
 * Invalid input will be reported to the user.
 */
import java.util.Scanner;
public class DepartmentStore 
{
	/**
     * Runs the store simulator.
     *
     * @param args
     *    not used
     */
    public static void main(String[] args)
    {
        Scanner input=new Scanner(System.in);
        ItemList store=new ItemList();

        System.out.println("Welcome!");
        boolean check=true;
        while(check)
        {
            printMenu();
            String choice=prompt(input,"Please select an option: ");
            if (choice==null)
                break;                      
            choice=choice.trim().toUpperCase();

            if (choice.equals("I"))
                Insert(input, store);
            else if (choice.equals("M"))
                Move(input, store);
            else if (choice.equals("L"))
                ListByLocation(input, store);
            else if (choice.equals("P"))
                store.printAll();
            else if (choice.equals("O"))
                CheckOut(input, store);
            else if (choice.equals("C"))
                store.cleanStore();
            else if (choice.equals("U"))
                store.removeAllPurchased();
            else if(choice.equals("R"))
                PrintByRfid(input, store);
            else if (choice.equals("Q"))
                check=false;
            else
                System.out.println("Invalid option.\nPlease choose one of the letters in the menu.");
        }
        System.out.println("\nGoodbye!");
    }


    /** Handle I:Read the four fields and inserts the item. */
    private static void Insert(Scanner input,ItemList store)
    {
        String name=prompt(input, "Enter the name: ");
        String rfid=prompt(input, "Enter the RFID: ");
        String location=prompt(input, "Enter the original location: ");
        String pricee=prompt(input, "Enter the price: ");
        if (name==null || rfid==null || location==null
            || pricee==null)
        {
            System.out.println("Incomplete entry.Please try again.");
            return;
        }

        double price;
        try
        {
            price=Double.parseDouble(pricee.trim());
        }
        catch(NumberFormatException e)
        {
            System.out.println(pricee.trim()
                + " is not a valid price. Please try again.");
            return;
        }

        try
        {
            store.insertInfo(name, rfid, price, location);
            //System.out.println("Item added.");
        }
        catch (InvalidDataException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /** Handles the M command: move one item from a source to a destination. */
    private static void Move(Scanner input,ItemList store)
    {
        String rfid=prompt(input,"Enter the RFID: ");
        String source=prompt(input,"Enter the original location: ");
        String dest=prompt(input,"Enter the new location: ");
        if (rfid==null || source==null || dest==null)
        {
            System.out.println("Incomplete entry. Please try again.");
            return;
        }
        if(ItemInfo.isOut(source.trim()) || ItemInfo.isOut(dest.trim()))
        {
            System.out.println(" \"Out\" cannot be used as a source or destination in a move.");
            return;
        }

        try
        {
            if(!store.moveItem(rfid, source, dest))
                System.out.println("Item not found at that location.");
        }
        catch (InvalidDataException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /** Handle L :list all items at one location. */
    private static void ListByLocation(Scanner input, ItemList store)
    {
        String location=prompt(input, "Enter the location: ");
        if (location==null)
            return;
        try
        {
            store.printByLocation(location);
        }
        catch (InvalidDataException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /** Handles O: check out a cart and reports the total. */
    private static void CheckOut(Scanner input,ItemList store)
    {
        String cart=prompt(input, "Enter the cart number: ");
        if (cart==null)
            return;
        try
        {
            double total = store.checkOut(cart);
            System.out.printf("The total cost for all merchandise in cart %s was $%.2f",
                cart.trim().substring(1), total);
        }
        catch (InvalidDataException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /** Handle R:list all items with one tag. */
    private static void PrintByRfid(Scanner input,ItemList store)
    {
        String rfid=prompt(input,"Enter the RFID: ");
        if (rfid==null)
            return;
        try
        {
            store.printByRfid(rfid);
        }
        catch (InvalidDataException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Prints a prompt and reads one line of input.
     *
     * @param input
     *    the Scanner to read from
     * @param message
     *    the prompt to show
     * @return
     *    the line that was entered, or null if there is no more input
     */
    private static String prompt(Scanner input, String message)
    {
        System.out.print(message);
        if (!input.hasNextLine())
            return null;
        return input.nextLine();
    }
    
    /** Prints the main menu. */
    private static void printMenu()
    {
        System.out.println();
        System.out.println("C - Clean store");
        System.out.println("I - Insert an item into the list");
        System.out.println("L - List by location");
        System.out.println("M - Move an item in the store");
        System.out.println("O - Checkout");
        System.out.println("P - Print all items in store");
        System.out.println("R - Print by RFID tag number");
        System.out.println("U - Update inventory system");
        System.out.println("Q - Exit the program.");
        System.out.println();
    }
}
