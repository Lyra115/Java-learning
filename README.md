# Java Data Structure Applications

Two Java console applications that connect custom data structures with practical workflows: ordered playlist management and RFID-based retail inventory tracking.

## Projects

| Project | Purpose | Core implementation |
| --- | --- | --- |
| [Audio Playlist Manager](playlist%20of%20audio%20files/README.md) | Organize song metadata and manage multiple playlists | Fixed-capacity array, positional updates, deep copying |
| [RFID Inventory Manager](doubly-linked%20list%20application/README.md) | Track items across shelves, carts, and checkout | Custom doubly linked list, ordered insertion, node removal |

Both applications use Java's standard library without external dependencies. Their core storage is implemented directly rather than delegated to collection classes.

## Engineering focus

- Separate domain records, storage operations, and console interaction.
- Maintain ordering while modifying arrays or linked nodes.
- Validate domain input and report errors through exceptions.
- Explain the cost of traversal, insertion, lookup, and deletion.
- Model useful workflows with small, readable Java classes.

## Getting started

Clone this branch, then follow the build instructions in either project:

```sh
git clone --branch homework --single-branch https://github.com/Lyra115/Java-learning.git
cd Java-learning
```

A JDK with `javac` and `java` available is required. The project guides compile into an `out` directory so package-qualified entry points work with the current source layout.

## Scope

These are interactive, in-memory console applications. State is held for the running session; database storage, a graphical interface, and external service integrations are not implemented.
