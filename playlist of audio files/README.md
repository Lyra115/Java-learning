# Audio Playlist Manager

A Java console application for organizing song metadata, maintaining playlist order, and working with multiple named playlists. The playlist data structure is built directly on a fixed-capacity array.

## Features

- Store a song's title, artist, duration, and optional audio filename.
- Insert or remove songs at a selected position while preserving list order.
- Retrieve a song, display the full playlist, or filter songs by artist.
- Create and switch between named playlists.
- Deep-copy playlists and compare ordered song contents.
- Report invalid positions, invalid durations, and full-playlist conditions.

Positions are one-based. Each playlist holds up to 50 songs. Artist filtering uses exact, case-sensitive matching.

## Implementation

| Class | Responsibility |
| --- | --- |
| `SongRecord` | Song metadata, duration validation, copying, and value comparison |
| `Playlist` | Array storage, positional updates, filtering, copying, and equality |
| `PlaylistOperations` | Interactive menu and management of named playlists |
| `FullPlaylistException` | Capacity error for song insertion |

Insertion shifts later entries right; removal shifts them left and clears the vacated reference. Playlist copying also copies each song record, so the copy does not share mutable song records with its source. Filtering preserves the relative order of matching songs.

## Operation costs

For `n` songs in a playlist, treating individual metadata operations as constant time:

| Operation | Time |
| --- | --- |
| Read size or retrieve a song by position | O(1) |
| Insert or remove at a position | O(n) worst case |
| Filter by artist | O(n) |
| Deep copy or compare playlist contents | O(n) |

The backing array has a fixed capacity. A deep copy creates a separate array and song records.

## Build and run

Run from the repository root:

```sh
javac -encoding UTF-8 -d out "playlist of audio files/FullPlaylistException.java" "playlist of audio files/SongRecord.java" "playlist of audio files/Playlist.java" "playlist of audio files/PlaylistOperations.java"
java -cp out hw1.PlaylistOperations
```

No external libraries or build tool are required.

## Console commands

| Command | Action |
| --- | --- |
| A / T | Add a song / add a song with an audio filename |
| G / R | Retrieve / remove a song by position |
| P / B / S | Display all songs / filter by artist / show size |
| N / V / D | Create a playlist / switch playlists / list playlist names |
| C / E | Copy the current playlist / compare it with another |
| L / Q | Display a playback message / quit |

For a short demo, add two songs with `A`, inspect their order with `P`, copy the playlist with `C`, remove a song from the copy, then switch back with `V` to inspect the original.

## Design discussion

This implementation makes the tradeoff between direct positional access and shifting costs visible. It also provides examples of defensive validation, value equality, and deep copying of mutable objects.

The `L` command prints the selected song's title, artist, and filename; it does not decode or play audio. Playlists are stored in memory and are not saved between sessions. The menu registry uses a 50-slot array for playlists; it currently has no guard for creating or copying beyond that capacity.
