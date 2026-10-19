/**
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:数媒2班
 */

/**
 * The Playlist class implements an abstract data type for a playlist of
 * audio files, supporting common operations such as adding, removing,
 * retrieving and printing SongRecord objects, as well as finding all the
 * songs in the Playlist that were performed by a given artist. The
 * SongRecord objects are stored in an array, and at most max SongRecord
 * objects are allowed in any one Playlist.
 * @author 吕嘉琦
 */
package hw1;

public class Playlist implements Cloneable {
	/** The maximum number of SongRecord objects allowed in a Playlist. */
	public static final int max=50;
	/** The (1-indexed) array that stores the SongRecords in this Playlist. */
	private SongRecord[] song;
	/** The number of SongRecord objects currently in this Playlist. */
	private int num;
	/** The name of this Playlist. */
	private String name;

	/**
	 * Constructs an instance of the Playlist class with no SongRecord
	 * objects in it and an empty name.
	 * Postcondition: This Playlist has been initialized to an empty list
	 * of SongRecords with an empty name.
	 */
	public Playlist()
	{
		this("");
	}
	/**
	 * Constructs an instance of the Playlist class with no SongRecord
	 * objects in it and the given name.
	 * @param name the name to give this Playlist
	 * Postcondition: This Playlist has been initialized to an empty list
	 * of SongRecords with the given name.
	 */
	public Playlist(String name) 
	{
		song=new SongRecord[max+1];
		num=0;
		this.name=name;
	}
	/**
	 * Gets the name of this Playlist.
	 * @return the name of this Playlist
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Sets the name of this Playlist.
	 * @param name the new name for this Playlist
	 */
	public void setName(String name)
	{
		this.name=name;
	}
	/**
	 * Generates a copy of this Playlist.
	 * @return a copy of this Playlist. Subsequent changes to the copy
	 *         will not affect the original, nor vice versa. Note that the
	 *         return value must be typecast to a Playlist before it can
	 *         be used.
	 */
	public Object clone() 
	{
		Playlist c=new Playlist(this.name);
		for (int i=1; i<=this.num; i++) 
		{
			c.song[i]=(SongRecord)song[i].clone();
		}
		c.num=this.num;
		return c;
	}

	/**
	 * Compares this Playlist to another object for equality.
	 * @param obj an object in which this Playlist is compared
	 * @return true if obj refers to a Playlist object with the same
	 *         SongRecords in the same order as this Playlist, false
	 *         otherwise. If obj is null or it is not a Playlist object,
	 *         the return value is false.
	 */
	public boolean equals(Object obj) 
	{
		if (obj == null || !(obj instanceof Playlist))
			return false;
		Playlist t=(Playlist) obj;
		if (t.num != this.num)
			return false;
		for (int i=1;i<=this.num;i++) {
			if (!(t.song[i].equals(this.song[i])))
				return false;
		}
		return true;
	}

	/**
	 * Determines the number of SongRecords currently in this Playlist.
	 * Precondition: This Playlist object has been instantiated.
	 * @return the number of SongRecords in this Playlist
	 */
	public int size() {
		return num;
	}

	/**
	 * Inserts a new SongRecord into this Playlist at the given position.
	 * All SongRecords that were originally in positions greater than or
	 * equal to position are moved back one position.
	 * Precondition: This SongRecord object has been instantiated and
	 * 1 &lt;= position &lt;= songs_currently_in_playlist + 1. The number
	 * of SongRecord objects in this Playlist is less than max.
	 * Postcondition: The new SongRecord is now stored at the desired
	 * position in the Playlist.
	 * @param song the new SongRecord object to add to this Playlist
	 * @param position the position in the playlist (not the array)
	 *        where the song will be inserted. Inserting a song at
	 *        position (songs_currently_in_playlist + 1) is effectively
	 *        the same as adding a song to the end of the Playlist.
	 * @throws IllegalArgumentException if position is not within the
	 *         valid range
	 * @throws FullPlaylistException if there is no more room inside of
	 *         the Playlist to store the new SongRecord object
	 */
	public void addSong(SongRecord song,int position) 
			throws IllegalArgumentException,FullPlaylistException
	{
		if(position<1 || position>num+1)
			throw new IllegalArgumentException("Invalid position for adding the new song.");
		if(num>=max)
			throw new FullPlaylistException("The Playlist is full.");
		for(int i=num;i>=position;i--)
		{
			this.song[i+1]=this.song[i];
		}
		this.song[position]=song;
		num++;
	}
	/**
	 * Removes the SongRecord at the given position from this Playlist.
	 * All SongRecords that were originally in positions greater than
	 * position are moved forward one position.
	 * Precondition: This Playlist object has been instantiated and
	 * 1 &lt;= position &lt;= songs_currently_in_playlist.
	 * Postcondition: The SongRecord at the desired position in the
	 * Playlist has been removed.
	 * @param position the position in the playlist (not the array) where
	 *        the song will be removed from
	 * @throws IllegalArgumentException if position is not within the
	 *         valid range
	 */
	public void removeSong(int position) throws IllegalArgumentException
	{
		if(position<1 || position>num)
			throw new IllegalArgumentException("No song at position "+position+" to move.");
		for(int i=position;i<num;i++)
		{
			song[i]=song[i+1];
		}
		song[num]=null;
		num--;
	}
	/**
	 * Gets the SongRecord at the given position in this Playlist.
	 * Precondition: This Playlist object has been instantiated and
	 * 1 &lt;= position &lt;= songs_currently_in_playlist.
	 * @param position position (in the playlist, not the array) of the
	 *        SongRecord to retrieve
	 * @return the SongRecord at the specified position in this Playlist
	 * @throws IllegalArgumentException if position is not within the
	 *         valid range
	 */
	public SongRecord getSong(int position) throws IllegalArgumentException
	{
		if(position<1 || position>num)
			throw new IllegalArgumentException("No SongRecord at position "+position);
		return song[position];
	}
	/**
	 * Prints a neatly formatted table of each SongRecord in this Playlist
	 * on its own line with its position number, as shown in the sample
	 * output.
	 * Precondition: This Playlist object has been instantiated.
	 * Postcondition: A neatly formatted table of each SongRecord in the
	 * Playlist on its own line with its position number has been
	 * displayed to the user.
	 */
	public void printAllSongs()
	{
		System.out.print(toString());
	}
	/**
	 * Generates a new Playlist containing all the SongRecords in the
	 * original Playlist that were performed by the specified artist. The
	 * order of the SongRecords in the new Playlist relates to the order
	 * of the SongRecords in the original Playlist (e.g. if SongRecords 3,
	 * 6 and 8 of the original Playlist were performed by the artist, they
	 * are placed at positions 1, 2 and 3, respectively, in the new
	 * Playlist).
	 * Precondition: The Playlist referred to by originalList has been
	 * instantiated.
	 * @param originalList the original Playlist
	 * @param artist the name of the artist
	 * @return a new Playlist object containing all SongRecords in the
	 *         original Playlist performed by the specified artist, or
	 *         null if either originalList or artist is null
	 */
	public static Playlist getSongsByArtist(Playlist originalList,String artist)
	{
		if(originalList==null || artist==null)
			return null;
		Playlist r=new Playlist();
		for(int i=1;i<=originalList.num;i++)
		{
			if(originalList.song[i].getArtist().equals(artist))
			{
				r.num++;
				r.song[r.num]=(SongRecord) originalList.song[i].clone();	
			}
		}
		return r;
	}
	/**
	 * Builds the header used at the top of the formatted table produced
	 * by toString(): a row of column titles (Song#, Title, Artist,
	 * Length) followed by a divider line.
	 * @return the header portion of the formatted table
	 */
	public static String printHead()
	{     
		String r=String.format("%-10s%-26s%-26s%s\n",
				"Song#","Title","Artist","Length");
		for(int i=0;i<68;i++)
		{
			r+="-";
		}
		r+="\n";
		return r;
	}
	/**
	 * Gets the String representation of this Playlist object, which is a
	 * neatly formatted table of each SongRecord in the Playlist on its
	 * own line with its position number.
	 * @return the String representation of this Playlist object
	 */
	public String toString()
	{
		String r=printHead();
		for(int i=1;i<=num;i++)
		{
			r+=String.format("%-10d%s\n",i,song[i].toString());
		}
		return r;
	}
}