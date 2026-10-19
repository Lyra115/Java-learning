/*
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:25数媒2班
 */
 
package hw2;
 
/**
 * An ItemInfo object stores information about one item in the department store.
 *
 * Fields:
 *     1.name: non-empty String, no more than 20 characters
 *     2.price: positive double
 *     3.rfidTagNumber:9 hexadecimal characters (0-9, A-F),stored in upper case
 *     4.originalLocation: shelf position ('s'+5 digits), stored in lower case
 *     5.currentLocation: shelf ('s'+5 digits), cart ('c'+3 digits),
 *       or "out", stored in lower case
 *
 * All setters check errors and throw an InvalidDataException
 */
public class ItemInfo 
{
	private String name;
	private double price;
    private String rfidTagNumber;
    private String original;
    private String current;
    
    /**
     * Constructs an ItemInfo. The current location of the new item is set to
     * its original location.
     *
     * @param name
     *    the name of the item; must be non-empty and no more than 20 characters
     * @param price
     *    the price of the item; must be positive
     * @param rfidTagNumber
     *    9 characters long and represented in hexadecimal format(case is not important)
     * @param original
     *    the original shelf position, an 's' followed by 5 digits
     * @throws InvalidDataException
     *    if any of the arguments is not in the required format
     */
    public ItemInfo(String name, String rfidTagNumber,
            String original, double price)
            throws InvalidDataException
	{
		setName(name);
		setPrice(price);
		setRfidTagNumber(rfidTagNumber);
		setOriginal(original);   // also sets current location
	}
    
    /**
     * check an rfidTagNumber and set it to upper case and trimmed form.
     *
     * @param rfid
     *    the rfidTagNumber typed by the user
     * @return
     *    the right upper case form of rfid
     * @throws InvalidDataException
     *    if rfid is not 9 hexadecimal characters
     */
    public static String setrightRfid(String rfid)
    {
    	String r=null;
    	if(rfid!=null)
    	{
    		r=rfid.trim();
    	}
    	if(!isvalidRfid(r))
    		throw new InvalidDataException("Invalid Rfid:it must be 9 hexadecimal characters(0-9,A-F).");
    	return r.toUpperCase();
    }
    
    /**
     * Checks a location and returns it in lower case and trimmed form.
     *
     * @param location
     *    the location typed by the user
     * @return
     *    the lower case form of location
     * @throws InvalidDataException
     *    if location is not a shelf, a cart or "out"
     */
    public static String setrightLocation(String location)
    {
    	String loc=null;
    	if(location!=null)
    		loc=location.trim();
    	if(!isvalidLocation(loc))
    		throw new InvalidDataException("Invalid location:it must be a shelf (s+5 digits), "
    				+ "a cart (c+3 digits) or \"out\"");
    	return loc.toLowerCase();
    }
    /**
     * Tests whether a String is a valid rfidTagNumber.
     *
     * @param r
     *    the String to test
     * @return
     *    true if r is exactly 9 hexadecimal characters (case insensitive),
     *    false otherwise (including when rfid is null)
     */
    public static boolean isvalidRfid(String r)
    {
    	if(r==null || r.length()!=9)
    		return false;
    	for(int i=0;i<9;i++)
    	{
    		char c=Character.toUpperCase(r.charAt(i));
    		if(!(c>='0' && c<='9') && !(c>='A' && c<='F'))
    			return false;
    	}
    	return true;
    }
    
    /**
     * Test whether a String is a valid shelf position.
     *
     * @param loc
     *    the String to test
     * @return
     *    true if location is 's' (or 'S') followed by 5 digits
     */
    public static boolean isvalidShelf(String loc)
    {
    	if(loc==null || loc.length()!=6)
    		return false;
    	if(Character.toLowerCase(loc.charAt(0))!='s')
    		return false;
    	return isallDigits(loc);
    }
    
    /**
    * Test whether a String is a valid cart position.
    *
    * @param loc
    *    the String to test
    * @return
    *    true if location is 'c' (or 'C') followed by 3 digits
    */
    public static boolean isvalidCart(String loc)
    {
    	if(loc==null || loc.length()!=4)
    		return false;
    	if(Character.toLowerCase(loc.charAt(0))!='c')
    		return false;
    	return isallDigits(loc);
    }
    /**
     * checked out the location.
     *
     * @param loc
     *    the String to test
     * @return
     *    true if location equals "out"(ignoring case)
     */
    public static boolean isOut(String loc)
    {
    	return (loc!=null && loc.equalsIgnoreCase("out"));
    }
    
    /**
     * Tests whether a String is a valid current location
     * (a shelf, a cart, or "out").
     *
     * @param s
     *    the String to test
     * @return
     *    true if location is a shelf, a cart or "out"
     */
    public static boolean isvalidLocation(String s)
    {
    	return (isvalidShelf(s) || isvalidCart(s) || isOut(s));
    }
    /**
     * Tests whether every character after the first one is a digit.
     *
     * @param s
     *   the String to test
     * @return
     *   true if all characters after the first are digits
     */
    public static boolean isallDigits(String s)
    {
    	for(int i=1;i<s.length();i++)
    	{
    		char c=s.charAt(i);
    		if(c<'0' || c>'9')
    			return false;
    	}
    	return true;
    }
    // Accessors and mutators------------------------------------
    
    /**
     * Get the name of this item.
     * @return the name of this item
     */
    public String getName()
    {
        return name;
    }
 
    /**
     * Set the name of this item.
     *
     * @param name
     *    the new name; must be non-empty and no more than 20 characters
     * @throws InvalidDataException
     *    if name is null, empty, or longer than 20 characters
     */
    public void setName(String name) throws InvalidDataException
    {
       String n=null;
       if(name!=null)
       {
    	   n=name.trim();
       }
        if (n==null || n.length()==0)
            throw new InvalidDataException("The name of the item can not be empty.");
        if (n.length()>20)
            throw new InvalidDataException("The name of the item is longer than 20 characters.");
        this.name=n;
    }
    
    /**
     * Get the price of this item.
     * @return the price of this item
     */
    public double getPrice()
    {
        return price;
    }
 
    /**
     * Set the price of this item.
     *
     * @param price
     *    the new price; must be a positive number
     * @throws InvalidDataException
     *    if price is not positive 
     */
    public void setPrice(double price) throws InvalidDataException
    {
        if (price<=0)
            throw new InvalidDataException("The price of the item must be a positive number.");
        this.price = price;
    }
    
    /**
     * Get the rfidTagNumber of this item.
     *
     * @return the 9 character hexadecimal tag (in upper case)
     */
    public String getRfidTagNumber()
    {
    		return rfidTagNumber;
    }
    
    /**
     * Set the rfidTagNumber of this item. The tag is stored in upper case.
     *
     * @param rfidTagNumber
     *    the new tag  must be exactly 9 hexadecimal characters
     * @throws InvalidDataException
     *    if rfidTagNumber is not 9 hexadecimal characters
     */
    public void setRfidTagNumber(String rfidTagNumber)
    							throws InvalidDataException
    {
    	this.rfidTagNumber=setrightRfid(rfidTagNumber);
    }
    
    /**
     * Get the original location of this item.
     *
     * @return the original shelf position, e.g. "s12345"
     */
    public String getOriginal()
    {
    	return original;
    }
    /**
     * Sets the original location of this item. 
     * The current location of the item is set to the same place.
     *
     * @param original
     *    the new original shelf position, an 's' followed by 5 digits
     * @throws InvalidDataException
     *    if original is not a valid shelf position
     *    (carts and "out" are not allowed)
     */
    public void setOriginal(String original)
    						throws InvalidDataException
    {
    	String loc=null;
    	if(original!=null)
    	{
    		loc=original.trim();
    	}
    	if(!isvalidShelf(loc))
    		throw new InvalidDataException("Invalid original location:it must be an 's' followed by 5 digits");
    	this.original=loc.toLowerCase();
    	this.current=this.original;
    }
    		
    /**
     * Gets the current location of this item.
     *
     * @return the current location: 
     * 			(a shelf,a cart,or "out")
     */
    public String getCurrent()
    {
    	return current;
    }
    
    /**
     * Sets the current location of this item.
     *
     * @param current
     *    the new location: (a shelf, a cart, or "out")
     * @throws InvalidDataException
     * if current is not a shelf, a cart or "out"
     */
    public void setCurrent(String current)
    								throws InvalidDataException
    {
    	this.current=setrightLocation(current);
    }
}
