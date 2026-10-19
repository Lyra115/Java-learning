/*
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:25数媒2班
 */

package hw2;

/**
 * An ItemList is a doubly-linked list of ItemInfoNode objects that represents
 * the inventory of the department store.  
 *
 * The list is always sorted in ascending order by rfidTagNumber.
 * Duplicate rfidTagNumbers are allowed and are stored next to each other.
 * The list uses only head and tail references
 * In the Big-O comments below, N is the number of items in the list.
 */ 
public class ItemList {
	private ItemInfoNode head;
	private ItemInfoNode tail;
	private int n;
	
	/**
     * Constructs an empty ItemList.
     * Postcondition: head and tail are null and the list is empty.
     */
	public ItemList()
	{
		head=null;
		tail=null;
		n=0;
	}
	
	/**
     * Gets the number of items in the list.
     *
     * @return the number of items
     */
	public int n()
	{
		return n;
	}
	
	/**
     * Tests whether the list is empty.
     *
     * @return true if the list is empty.
     */
	public boolean isEmpty()
	{
		return n==0;
	}
	
	/**
     * Creates a new item from the given data and inserts it into the list in
     * its correct position based on its rfidTagNumber. If items with the same
     * rfidTagNumber already exist, the new item is placed in front of them, so the
     * group of equal tags stays together (the newest item of the group comes first). The list is never sorted with a
     * sorting algorithm: the correct position is found by a single traversal.
     *
     * @param name
     *    the name of the item (non-empty, at most 20 characters)
     * @param rfidTag
     *    the 9 character hexadecimal rfid  tag (case is not important)
     * @param price
     *    the price of the item (positive)
     * @param initPosition
     *    the original shelf position, an 's' followed by 5 digits
     * @throws InvalidDataException
     *    if any argument is invalid; the list is not changed in that case
     * Postcondition: the list contains one more item and is still sorted by rfidTagNumber.
     */
	public void insertInfo(String name,String rfidTag,double price,
			String initPosition)throws InvalidDataException
	{

		/*Big-O: O(N). 
		* Validating the data and linking the new node take constant time, 
		*but finding the insertion point requires walking past every node that has a larger tag,
        *which can be all N nodes in the worst case.
        */
		ItemInfo info=new ItemInfo(name,rfidTag,initPosition,price);
		ItemInfoNode node=new ItemInfoNode();
		node.setInfo(info);
		
		ItemInfoNode cur=tail;
		while(cur!=null && cur.getInfo().getRfidTagNumber().compareTo(info.getRfidTagNumber())>=0)
		{
			cur=cur.getPrev();
		}
		if(cur==null)
		{
			// The new node should be in front of the list.
			node.setNext(head);
			if(head!=null)
				head.setPrev(node);
			else
				tail=node;
			head=node;    
		}
		else
		{
			//The new node is after cur.
			node.setPrev(cur);   
			node.setNext(cur.getNext());
			if(cur.getNext()!=null)
				cur.getNext().setPrev(node);
			else
				tail=node;
			cur.setNext(node);
		}
		n++;
	}
	
	/**
     * Removes all nodes whose current location is "out" and 
     * displays a table of every item that was removed. 
     * The list itself is kept intact (only  the purchased nodes are unlinked).
     */
	public void removeAllPurchased()
	{
		/*
		 * Big-O: O(N). 
		 * One traversal visits each node once,
		 *  and each removal is O(1)
		 */
		boolean check=true;
		ItemInfoNode cur=head;
		while(cur!=null)
		{
			ItemInfoNode next=cur.getNext();
			ItemInfo info=cur.getInfo();
			if(ItemInfo.isOut(info.getCurrent()))
			{
				if(check)
				{
					System.out.println();
					System.out.println("The following item(s) have removed from the system:");
					System.out.println();
                    printHead();
                    check=false;
				}
				printRow(info, info.getCurrent());
                removeNode(cur);
			}
			cur=next;
		}
		if(check)
			System.out.println("No purchased items to remove.");
	}
	
	/**
     * Moves an item with the given rfidTagNumber from a source location to a
     * destination location. If several items match, exactly one of them is
     * moved.
     *
     * @param rfidTag
     *    the rfidTagNumber of the item to move
     * @param source
     *    the location the item is currently at (shelf or cart)
     * @param dest
     *    the location to move the item to (shelf, cart or "out")
     * @return
     *    true if an item with that rfidTagNumber was found at the source location (and was moved),
     *     false if no such item was found
     * @throws InvalidDataException
     *    if rfidTag is unformed, if source or dest is not a valid location
     *    (shelf, cart or "out"), or if source is "out"
     */
	public boolean moveItem(String rfidTag, String source, String dest)
			throws InvalidDataException
	{
		/* Big-O: O(N).
    	 *Validation is O(1).the search walks the sorted list 
    	 *until it finds a match (or passes the tag), at most N nodes.
         */
		String rfid=ItemInfo.setrightRfid(rfidTag);
		String sour=ItemInfo.setrightLocation(source);
        String des=ItemInfo.setrightLocation(dest);
        if(ItemInfo.isOut(sour))
        	throw new InvalidDataException("Items that are \"out\" cannot be moved.");
        ItemInfoNode cur=tail;
        while(cur!=null)
        {
        	ItemInfo info=cur.getInfo();
        	int a=info.getRfidTagNumber().compareTo(rfid);
        	if(a<0)
        		break;
        	if(a==0 && info.getCurrent().equals(sour))
        	{
        		info.setCurrent(des);
        		return true;
        	}
        	cur=cur.getPrev();
        }
        return false;
	}
	
	/**
     * Prints a neatly formatted table of all items in the list, 
     * sorted by rfidTagNumber. 
     */
	public void printAll()
	{
		/* Big-O:O(N) 
    	 *The list is already sorted, so a single traversal
         *that prints each node once is all that is needed.
        */
		System.out.println();
		printHead();
		for(ItemInfoNode cur=head;cur!=null;cur=cur.getNext())
		{
			printRow(cur.getInfo(), cur.getInfo().getCurrent());
		}
	}
	
	/**
     * Prints a neatly formatted table of all items 
     * whose current location is the given location, 
     * sorted by rfidTagNumber. 
     *
     * @param location
     *    the location to list: a shelf, a cart, or "out"
     * @throws InvalidDataException
     *    if location is not a valid location
     */
	 public void printByLocation(String location)
			 throws InvalidDataException
	 {
	     /* Big-O: O(N)
	      *Validation is O(1); one traversal of the sorted list
	      *visits each node once and prints only those at the location.
	      */
		 String loc=ItemInfo.setrightLocation(location);
		 System.out.println();
		 printHead();
		 boolean check=true;
		 for(ItemInfoNode cur=head;cur!=null;cur=cur.getNext())
		 {
			 ItemInfo info=cur.getInfo();
			 if(info.getCurrent().equals(loc))
			 {
				 printRow(info,info.getCurrent());
				 check=false;
			 }
		 }
		 if(check)
			 System.out.println("no items at location " + loc);
	 }
	 
	 /**
	  * Puts every item that is on the wrong shelf back on its original shelf
	  * and prints a table of all items that were moved 
	  * (showing the location each item was in before it was moved). 
	  * Items in carts or "out" are not affected. 
	  */
	 public void cleanStore()
	 {
		 /*
		  * Big-O:O(N)
		  * One traversal.the check and the move for each node are O(1)
		  */
		 boolean check=true;
		 for(ItemInfoNode cur=head;cur!=null;cur=cur.getNext())
		 {
			 ItemInfo info=cur.getInfo();
			 String loc=info.getCurrent();
			 if(ItemInfo.isvalidShelf(loc) && !loc.equals(info.getOriginal()))
			 {
				 if(check)
				 {
					 System.out.println();
	                 System.out.println("The following item(s) have been moved back to their original locations:");
	                 System.out.println();
	                 printHead();
	                 check=false;
				 }
				 printRow(info,loc);
				 info.setCurrent(info.getOriginal());
			 }
		 }
		 if(check)
			 System.out.println("Every item is already at right shelf.");
	 }
	 
	 /**
	     * Check out each  item in the given cart (changes its location to  "out"),
	     *  prints a table of the items checked out (sorted by rfidTagNumber)
	     *  and returns their total cost. 
	     *
	     * @param cartNumber
	     *    the cart to check out, a 'c' followed by 3 digits
	     * @return
	     *    the total price of all items that were in the cart (0 if the cart
	     *    was empty)
	     * @throws InvalidDataException
	     *    if cartNumber is not a valid cart (a 'c' followed by 3 digits)
	     */
	 public double checkOut(String cartNumber)
	 		throws InvalidDataException
	 {
		 /*
		  * Big-O:O(N)
		  * One traversal of the list.
		  * printed and added to the total in O(1)
		  */
		 String cart=null;
		 if(cartNumber!=null)
			 cart=cartNumber.trim();
		 if(!ItemInfo.isvalidCart(cart))
			 throw new InvalidDataException("Invalid cart number must be a 'c' followed by 3 digits.");
		 cart=cart.toLowerCase();
		 double total=0.0;
		 boolean check=true;
		 for(ItemInfoNode cur=head;cur!=null;cur=cur.getNext())
		 {
			 ItemInfo info=cur.getInfo();
			 if(info.getCurrent().equals(cart))
			 {
				 if(check)
				 {
					 System.out.println();
	                 printHead();
	                 check=false;
				 }
				 printRow(info, cart);     
	             info.setCurrent("out");
	             total+=info.getPrice();
			 }
		 }
		 if(check)
			 System.out.println("The cart is empty.");
		 return total;
	 }
	 
	 /**
	     * Prints a neatly formatted table of all items that have
	     * the given rfidTagNumber, wherever they are located.
	     *
	     * @param rfidTag
	     *    the rfidTagNumber to look for (case is not important)
	     * @throws InvalidDataException
	     *    if rfidTag is not exactly 9 hexadecimal characters
	     */
	 public void printByRfid(String rfidTag)
			 				throws InvalidDataException
	 {
		 /*
		  * Big-O:O(N)
		  * In the worst case,all N nodes are visited.
		  */
		 String r=ItemInfo.setrightRfid(rfidTag);
		 System.out.println();
	     printHead();
	     boolean check=true;
	     for(ItemInfoNode cur=head;cur!=null;cur=cur.getNext())
	     {
	    	 ItemInfo info=cur.getInfo();
	    	 int a=info.getRfidTagNumber().compareTo(r);
	    	 if(a>0)
	    		 break;
	    	 if(a==0)
	    	 {
	    		 printRow(info, info.getCurrent());
	             check=false;
	    	 }
	    	 
	     }
	     if(check)
	    	 System.out.println("no items with RFID " +r);
	 }
	 
	/**
     * Unlinks the given node from the list 
     * Big-O:O(1) because the list is doubly  linked). 
     * The rest of the list is untouched.
     *
     * @param node
     * 			the node that will be removed
     */
	private void removeNode(ItemInfoNode node)
	{
		ItemInfoNode before=node.getPrev();
		ItemInfoNode after=node.getNext();
		if(before==null)
			head=after;
		else
			before.setNext(after);
		if(after==null)
			tail=before;
		else
			after.setPrev(before);
		node.setNext(null);
		node.setPrev(null);
		n--;
	}
	
	// Prints the two-line column heading and the separator line. 
	private static void printHead() 
	{
		System.out.printf("%-20s %-9s %-8s %-8s %8s%n", "","","Original","Current","");
		System.out.printf("%-20s %-9s %-8s %-8s %8s%n","Item Name","RFID","Location","Location","Price");
		System.out.printf("%-20s %-9s %-8s %-8s %8s%n", "--------------------", "---------",
                          "--------", "--------", "--------");
	}
	
	/**
     * Prints one row.
     *
     * @param info
     *    the item to print
     * @param show
     *    the text to show in the "Current Location" column (this lets
     *    cleanStore and checkOut show the location from before the change)
     */
	private static void printRow(ItemInfo info,String show)
	{
		System.out.printf("%-20s %-9s %-8s %-8s %8s%n", info.getName(), info.getRfidTagNumber(),
            info.getOriginal(), show,String.format("%.2f", info.getPrice()));
	}
}
 