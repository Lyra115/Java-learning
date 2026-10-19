# Audio Playlist Manager / 音频播放列表管理器

该 Java 控制台应用管理歌曲元数据、播放列表顺序与多个具名播放列表。核心存储使用自定义定长数组实现。

This Java console application manages song metadata, playlist order, and multiple named playlists. Its core storage is implemented with a custom fixed-capacity array.

## Features / 功能

- 保存歌曲标题、艺人、时长及可选音频文件名。  
  Store a song's title, artist, duration, and optional audio filename.
- 在指定位置插入或删除歌曲，并保持其余歌曲顺序。  
  Insert or remove songs at a selected position while preserving order.
- 按位置获取歌曲、显示播放列表或按艺人筛选。  
  Retrieve a song by position, display a playlist, or filter by artist.
- 创建、切换、复制和比较多个具名播放列表。  
  Create, switch, copy, and compare named playlists.
- 校验非法位置、时长及播放列表容量。  
  Validate positions and durations and report capacity errors.

位置从 1 开始；每个播放列表最多容纳 50 首歌曲；艺人筛选区分大小写并要求完全匹配。

Positions are one-based. Each playlist holds up to 50 songs. Artist filtering uses exact, case-sensitive matching.

## Implementation / 实现

| Class / 类 | Responsibility / 职责 |
| --- | --- |
| `SongRecord` | 歌曲信息、时长校验、复制与值比较 / Song metadata, duration validation, copying, and value comparison |
| `Playlist` | 数组存储、位置更新、筛选、复制与相等性 / Array storage, positional updates, filtering, copying, and equality |
| `PlaylistOperations` | 交互菜单与具名播放列表管理 / Interactive menu and named-playlist management |
| `FullPlaylistException` | 插入歌曲时的容量错误 / Capacity error for song insertion |

插入时将后续元素右移；删除时左移并清除空出的引用。复制播放列表时会复制每个歌曲对象，因此副本与源列表不会共享可变歌曲记录。筛选会保留匹配歌曲的相对顺序。

Insertion shifts later entries right; removal shifts them left and clears the vacated reference. Copying a playlist also copies each song record, so the copy does not share mutable records with its source. Filtering preserves the relative order of matching songs.

对于包含 n 首歌曲的播放列表，读取长度和按位置获取为 O(1)；插入、删除、筛选、深拷贝及内容比较最坏为 O(n)。底层数组容量固定，深拷贝会创建独立数组和歌曲对象。

For a playlist with n songs, reading its size or retrieving by position is O(1). Insertion, removal, filtering, deep copying, and content comparison are O(n) in the worst case. The backing array has fixed capacity; a deep copy creates a separate array and song records.

## Build and run / 编译运行

在仓库根目录执行：

Run from the repository root:

```sh
javac -encoding UTF-8 -d out "playlist of audio files/FullPlaylistException.java" "playlist of audio files/SongRecord.java" "playlist of audio files/Playlist.java" "playlist of audio files/PlaylistOperations.java"
java -cp out hw1.PlaylistOperations
```

无需外部依赖或构建工具。

No external libraries or build tool are required.

## Console commands / 控制台命令

| Command / 命令 | Action / 操作 |
| --- | --- |
| A / T | 添加歌曲 / Add a song, with or without an audio filename |
| G / R | 按位置获取或删除歌曲 / Get or remove a song by position |
| P / B / S | 显示全部、按艺人筛选或查看数量 / Print all, filter by artist, or show size |
| N / V / D | 新建、切换播放列表或列出名称 / Create, switch, or list playlists |
| C / E | 复制当前列表或与其他列表比较 / Copy the current playlist or compare it |
| L / Q | 显示播放提示或退出 / Display a playback message or quit |

快速演示：用 `A` 添加两首歌曲，以 `P` 查看顺序；用 `C` 复制列表，在副本中删除一首歌曲，再用 `V` 切回原列表确认内容独立。

Quick demo: add two songs with `A`, inspect their order with `P`, copy the playlist with `C`, remove a song from the copy, then switch back with `V` to confirm that the original remains independent.

## Scope / 范围

`L` 命令只显示所选歌曲的标题、艺人和文件名，不会解码或播放音频。播放列表只保存在内存中，重启后不会保留。播放列表注册表也是 50 槽数组，目前未对超出该容量的新建或复制操作增加防护。

The `L` command displays a selected song's title, artist, and filename; it does not decode or play audio. Playlists are stored in memory and are not saved between sessions. The playlist registry is also a 50-slot array and currently has no guard against creating or copying beyond that capacity.
