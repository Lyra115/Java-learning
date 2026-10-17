/**
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:数媒2班
 */

/**
 * The PlaylistOperations Java application tests the methods of the
 * Playlist class and allows the user to manipulate a single current
 * Playlist object (and, as an extra credit feature, several named
 * Playlists) by performing operations selected from a menu.
 * @author 吕嘉琦
 */
package hw1;
import java.util.Scanner;
public class PlaylistOperations 
{
	/** The maximum number of Playlists that can be managed at once. */
	private static final int max=50;
	/** Used to read the user's menu choices and input from the keyboard. */
	private static Scanner input=new Scanner(System.in);
	/** Holds all of the Playlists that have been created so far. */
	private static Playlist[] playlists=new Playlist[max];
	/** The number of Playlists currently stored in playlists. */
	private static int numPlaylists=0;
	/** The current Playlist being operated on. */
	private static Playlist currentPlaylist;
	/**
	 * Runs a menu-driven application which first creates an empty
	 * Playlist and then repeatedly prompts the user to select a menu
	 * operation, requests any additional information required by that
	 * operation, and performs it, until the user chooses to quit.
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		boolean quit=false;
		currentPlaylist=new Playlist();
		playlists[0]=currentPlaylist;
		numPlaylists=1;
		while(!quit)
		{
			System.out.println("A) Add Song");
			System.out.println("B) Print Songs by Artist");
			System.out.println("G) Get Song");
			System.out.println("R) Remove Song");
			System.out.println("P) Print All Songs");
			System.out.println("S) Size");
			System.out.println("T) Add Song with Audio File");
			System.out.println("L) Play Song");
			System.out.println("N) New Playlist");
			System.out.println("V) Change Current Playlist");
			System.out.println("C) Copy Current Playlist");
			System.out.println("E) Compare Current Playlist");
			System.out.println("D) Display All Playlist Names");
			System.out.println("Q) Quit");
			System.out.println();
			System.out.print("Select a menu option: ");
			String choice=input.nextLine().trim().toUpperCase();
			switch(choice)
			{
				case "A": addSong(false);
							break;
				case "T":addSong(true);
							break;
				case "B":printSongsByArtist();
							break;
				case "G":getSong();
							break;
				case "R":removeSong();
							break;
				case "P":currentPlaylist.printAllSongs();
							break;
				case "S":System.out.println("There are "+currentPlaylist.size()
							+" song(s) in the current playlist.");
							break;
				case "N":newPlaylist();
							break;
				case "V":changePlaylist();
							break;
				case "C":copyPlaylist();
							break;
				case "E":comparePlaylist();
							break;
				case "D":displayPlaylistName();
							break;
				case "L":playSong();
							break;
				case "Q":quit=true;
							break;
				default:System.out.println("Invalid menu option.");
						break;
			}
		}
		System.out.println("Program terminating normally...");
		
	}
	/**
	 * Prints the given prompt and returns the line of text the user
	 * types in response, with leading and trailing whitespace removed.
	 * @param s the prompt to display to the user
	 * @return the trimmed line of input entered by the user
	 */
	private static String ask(String s)
	{
		System.out.print(s);
		return input.nextLine().trim();
	}
	/**
	 * Prompts the user for all of the information needed to build a new
	 * SongRecord (title, artist, length and, optionally, an audio
	 * filename) and the position at which to insert it, then adds the
	 * new SongRecord to the current Playlist, echoing the input and
	 * reporting whether the operation succeeded.
	 * @param haveFile true if the user should also be asked for the
	 *        song's audio filename (the "Add Song with Audio File"
	 *        option), false otherwise
	 */
	private static void addSong(boolean haveFile)
	{
		String title=ask("Enter the song title: ");
		String artist=ask("Enter the song artist: ");
		String minutes=ask("Enter the song length (minutes): ");
		String seconds=ask("Enter the song length (seconds): ");
		String pos=ask("Enter the position: ");
		String filename="";
		if(haveFile)
			filename=ask("Enter the song's filename:");
		int minute,second,position;
		try
		{
			minute=Integer.parseInt(minutes);
			second=Integer.parseInt(seconds);
			position=Integer.parseInt(pos);
		}catch(NumberFormatException e)
		{
			System.out.println("Invalid input: minutes, seconds and position must be numbers.");
			return;
		}
		SongRecord song=new SongRecord();
		song.setTitle(title);
		song.setArtist(artist);
		song.setFilename(filename);
		try
		{
			song.setMinute(minute);
			song.setSecond(second);
		}catch(IllegalArgumentException e)
		{
			System.out.println("Invalid song length.");
			return;
		}
		try
		{
			currentPlaylist.addSong(song, position);
			System.out.println("Song Added: "+title+" By "+artist);
		}catch(IllegalArgumentException e)
		{
			System.out.println("Invalid position for adding the new song.");
		}catch(FullPlaylistException e)
		{
			System.out.println("The Playlist is full.");
		}
	}
	/**
	 * Prompts the user for an artist name and prints, in tabular form,
	 * all of the songs in the current Playlist performed by that artist.
	 */
	private static void printSongsByArtist()
	{
		String artist=ask("Enter the artist:");
		Playlist.getSongsByArtist(currentPlaylist,artist).printAllSongs();
	}
	/**
	 * Prompts the user for a position and prints, in tabular form, the
	 * single SongRecord at that position in the current Playlist, or an
	 * error message if the position is invalid or not a number.
	 */
	private static void getSong()
	{
		String pos=ask("Enter the position: ");
		int position;
		try
		{
			position=Integer.parseInt(pos);
		}catch(NumberFormatException e)
		{
			System.out.println("Invalid position.");
			return;
		}
		try
		{
			SongRecord song=currentPlaylist.getSong(position);
			System.out.print(Playlist.printHead());
			System.out.println(String.format("%-10d%s", position,song));
		
		}catch(IllegalArgumentException e)
		{
			System.out.println("No song at position "+position+".");
		}
	}
	/**
	 * Prompts the user for a position and removes the SongRecord at that
	 * position from the current Playlist, reporting whether the
	 * operation succeeded.
	 */
	private static void removeSong()
	{
		String pos=ask("Enter the position: ");
		int position;
		try
		{
			position=Integer.parseInt(pos);
		}catch(NumberFormatException e)
		{
			System.out.println("Invalid position.");
			return;
		}
		try
		{
			currentPlaylist.removeSong(position);
			System.out.println("Song Removed at position "+position);
		}catch(IllegalArgumentException e)
		{
			System.out.println("No song at position "+position+" to remove.");
		}
	}
	/**
	 * Prompts the user for a position and "plays" the SongRecord at that
	 * position in the current Playlist by printing its title, artist and
	 * associated audio filename, or an error message if the position is
	 * invalid, not a number, or no audio file is associated with the
	 * song.
	 * (Extra credit: play the song.)
	 */
	private static void playSong()
	{
		String pos=ask("Enter the position: ");
		int position;
		try
		{
			position=Integer.parseInt(pos);
		}catch(NumberFormatException e)
		{
			System.out.println("Invalid position.");
			return;
		}
		try
		{
			SongRecord song=currentPlaylist.getSong(position);
			if(song.getFilename()==null || song.getFilename().equals(""))
				System.out.println("The song has no audio file.");
			else
				System.out.println("Playing \""+song.getTitle()+"\" by "
						+song.getArtist()+" from file "+song.getFilename());
		}catch(IllegalArgumentException e)
		{
			System.out.println("No song at position " + position + " to play.");
		}
	}
	/**
	 * Searches the Playlists created so far for one with the given name.
	 * @param name the name of the Playlist to find
	 * @return the Playlist with the given name, or null if no such
	 *         Playlist has been created
	 */
	private static Playlist findPlaylist(String name)
	{
		for(int i=0;i<numPlaylists;i++)
		{
			if(playlists[i].getName().equals(name))
				return playlists[i];
		}
		return null;
	}
	/**
	 * Prompts the user for a new playlist name and, if no Playlist with
	 * that name already exists, creates an empty Playlist with that name
	 * and sets it as the current Playlist.
	 * (Extra credit: manage multiple playlists.)
	 */
	private static void newPlaylist()
	{
		String name=ask("Enter the playlist name:");
		if(findPlaylist(name)!=null)
			System.out.println("A playlist named " + name + " already exists.");
		else
		{
			currentPlaylist=new Playlist(name);
			playlists[numPlaylists]=currentPlaylist;
			numPlaylists++;
			System.out.println("Playlist " + name 
					+ " created and set as the current playlist.");
		}
	}
	/**
	 * Prompts the user for the name of an existing playlist and, if
	 * found, sets it as the current Playlist.
	 * (Extra credit: manage multiple playlists.)
	 */
	private static void changePlaylist()
	{
		String name=ask("Enter the name of the playlist:");
		Playlist exist=findPlaylist(name);
		if(exist==null)
			System.out.println("No playlist named "+name+".");
		else
		{
			currentPlaylist=exist;
			System.out.println("Current playlist changed to "+name+".");
		}
	}
	/**
	 * Prompts the user for a new playlist name and, if no Playlist with
	 * that name already exists, creates a new Playlist containing a copy
	 * of every song in the current Playlist under the new name.
	 * (Extra credit: manage multiple playlists.)
	 */
	private static void copyPlaylist()
	{
		String name=ask("Enter the new playlist name:");
		if(findPlaylist(name)!=null)  
			System.out.println("The playlist named "+name+" already exists.");
		else
		{
			Playlist copy=(Playlist)currentPlaylist.clone();
			copy.setName(name);
			playlists[numPlaylists]=copy;
			numPlaylists++;
			System.out.println("Songs copied into the new playlist "+name+".");
		}
	}
	/**
	 * Prompts the user for the name of an existing playlist and reports
	 * whether it contains the same songs, in the same order, as the
	 * current Playlist.
	 * (Extra credit: manage multiple playlists.)
	 */
	private static void comparePlaylist()
	{
		String name=ask("Enter the playlist name:");
		Playlist t=findPlaylist(name);
		if(t==null)
			System.out.println("No playlist named "+name+".");
		else if(currentPlaylist.equals(t))
			System.out.println("The playlists have the same songs.");
		else
			System.out.println("The playlists have different songs.");
	}
	/**
	 * Prints the name of every Playlist created so far, one per line.
	 * (Extra credit: manage multiple playlists.)
	 */
	private static void displayPlaylistName()
	{
		for(int i=0;i<numPlaylists;i++)
		{
			System.out.println(playlists[i].getName());
		}
	}
}