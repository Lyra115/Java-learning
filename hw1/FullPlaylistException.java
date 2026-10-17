/**
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:数媒2班
 */

/**
 * The FullPlaylistException class represents the exception that is thrown
 * when an attempt is made to add a new SongRecord to a Playlist that has
 * already reached its maximum allowed number of songs.
 * @author 吕嘉琦
 */
package hw1;

public class FullPlaylistException extends Exception
{
	/**
	 * Constructs a new FullPlaylistException with the specified detail message.
	 * @param e the detail message describing why the Playlist is full
	 */
	public FullPlaylistException(String e)
	{
		super(e);
	}
}