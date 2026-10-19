/**
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:数媒2班
 */

/**
 * TheSongRecord class contains information about a particular
 * audio file: its title, its artist, its length in minutes 
 * and seconds and the name of the audio file 
 * @author 吕嘉琦
 */
package hw1;

public class SongRecord
{
	private String title;
	private String artist;
	private int minute;
	private int second;
	private String filename;
	/**
	 *  Default constructor. 
	 *  Creates a SongRecord with an empty title, an empty
	 * artist, a length of 0:00 and no file name.
	 */
	public SongRecord()
	{
		title="";
		artist="";
		minute=0;
		second=0;
		filename="";
	}
	
	/**
	 * Get the title of the song.
	 * @return the title of the song
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Set the title of the song.
	 * @param title the new title
	 */
	public void setTitle(String title)
	{
		this.title=title;
	}
	
	/**
	 * Get the artist of the song.
	 * @return the artist of the song
	 */
	public String getArtist()
	{
		return artist;
	}
	
	/**
	 * Set the artist of the song.
	 * @param artist the new artist
	 */
	public void setArtist(String artist)
	{
		this.artist=artist;
	}
	
	/**
	 * Get the minutes of the song's length.
	 *
	 * @return the minutes of the song's length
	 */
	public int getMinute()
	{
		return minute;
	}
	
	/**
	 * Set the minutes of the song's length.
	 * @param minute the new number of minutes
	 * @throws IllegalArgumentException
	 *           if minute is negative
	 */
	public void setMinute(int minute) throws IllegalArgumentException
	{
		if(minute<0)
			throw new IllegalArgumentException("Invalid song length.");
		this.minute=minute;
	}
	
	/**
	 * Get the seconds of the song's length.
	 * @return the seconds of the song's length
	 */
	public int getSecond()
	{
		return second;
	}
	
	/**
	 * Sets the seconds of the song's length.
	 * @param second the new number of seconds
	 * @throws IllegalArgumentException
	 *           if second is less than 0 or greater than 59
	 */
	public void setSecond(int second) throws IllegalArgumentException
	{
		if(second<0 || second >59)
			throw new IllegalArgumentException("Invalid song length.");
		this.second=second;
	}
	
	/**
	 * Gets the name of the audio file of the song.
	 * @return the name of the audio file 
	 */
	public String getFilename()
	{
		return filename;
	}
	
	/**
	 * Sets the name of the audio file of the song.
	 * @param filename the new file name
	 */
	public void setFilename(String filename)
	{
		this.filename=filename;
	}
	
	/**
	 * Creates a copy of this SongRecord.
	 * @return a copy of this SongRecord (it must be typecast to SongRecord)
	 */
	public Object clone()
	{
		SongRecord c=new SongRecord();
		c.title=title;
		c.artist=artist;
		c.minute=minute;
		c.second=second;
		c.filename=filename;   
		return c;
	}
	
	/**
	 * Compares this SongRecord to another object. Two SongRecords are equal if
	 * their titles, artists and lengths are all the same.
	 *
	 * @param obj the object to compare with
	 * @return true if obj is a SongRecord with the same title, artist and length
	 *		   false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null || !(obj instanceof SongRecord))
			return false;
		SongRecord t=(SongRecord) obj;
		return title.equals(t.getTitle()) && artist.equals(t.getArtist())
				&& minute==t.getMinute() && second==t.getSecond();
	}
	/**
	 * Gets the information about the audio file on a single line: the title,
	 * the artist and the length (the seconds always have two digits).
	 *
	 * @return the title, artist and length of the song on one line
	 */
	public String toString()
	{
		return String.format("%-26s%-26s%d:%02d",title,artist,minute,second);
	}
	
}



