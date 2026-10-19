# RFID Inventory Manager

A Java console application that tracks retail items from their original shelf through cart movement, checkout, and removal from active inventory. Its storage layer is a custom doubly linked list kept in RFID order.

## Features

- Insert inventory records containing a name, RFID tag, price, and original shelf.
- Keep records sorted by RFID during insertion, including duplicate tags.
- Move one matching item between a source shelf or cart and a destination.
- List all records, filter by location, or find records with a given RFID tag.
- Check out a cart, calculate its total, and mark its items as purchased.
- Return misplaced shelf items to their original shelves.
- Remove purchased items from the active inventory.

## Data model

| Field | Format or meaning |
| --- | --- |
| Name | Non-empty, up to 20 characters |
| RFID | Nine hexadecimal characters; normalized to uppercase |
| Original location | Shelf code such as `s12345` |
| Current location | Shelf code, cart code such as `c105`, or `out` |
| Price | Positive price, displayed to two decimal places |

RFID tags can repeat: each node represents an individual item. The move operation selects one record matching both the tag and source location. The console rejects manual moves using `out`; checkout sets that state.

## Implementation

| Class | Responsibility |
| --- | --- |
| `ItemInfo` | Inventory fields, validation, and input normalization |
| `ItemInfoNode` | Item reference and previous/next node links |
| `ItemList` | Ordered insertion, traversal, movement, checkout, and removal |
| `DepartmentStore` | Interactive console menu |
| `InvalidDataException` | Domain validation errors |

Insertion searches backward from the tail, then links the new node into its ordered position. Display operations can traverse the existing order without a separate sort. Purchased-item removal saves the next reference before unlinking the current node, allowing consecutive removals in one traversal.

Head and tail references are updated when deleting boundary nodes, including the last remaining node. Moving an item changes its location without changing its RFID, so the list order is preserved.

## Operation costs

For `n` inventory records and fixed-length RFID tags:

| Operation | Time |
| --- | --- |
| Read size or check whether the list is empty | O(1) |
| Find and insert at the ordered position | O(n) worst case |
| Find and move an item | O(n) worst case |
| List or filter records | O(n) worst case |
| Checkout, restore misplaced shelf items, or remove purchased items | O(n) |
| Unlink an already located node | O(1) |

Storage is O(n), with two links per node. Ordered lookup can stop after passing the target RFID but still requires linear traversal in the worst case.

## Build and run

Run from the repository root:

```sh
javac -encoding UTF-8 -d out "doubly-linked list application/InvalidDataException.java" "doubly-linked list application/ItemInfo.java" "doubly-linked list application/ItemInfoNode.java" "doubly-linked list application/ItemList.java" "doubly-linked list application/DepartmentStore.java"
java -cp out hw2.DepartmentStore
```

No external libraries or build tool are required.

## Console commands

| Command | Action |
| --- | --- |
| I | Insert an item |
| M | Move one matching item |
| L / P / R | List by location / print all / find by RFID |
| O | Check out a cart |
| C | Return misplaced shelf items to their original shelves |
| U | Remove purchased items |
| Q | Exit |

Example walkthrough:

1. Use `I` to add `Dress Shirt`, RFID `00A5532FF`, shelf `s12345`, and price `30.00`.
2. Use `M` to move it from `s12345` to `c105`.
3. Use `L` with `c105` to inspect the cart.
4. Use `O` with `c105` to check out; the item becomes `out` and the total is displayed.
5. Use `U` to remove the purchased record, then `P` to inspect the remaining inventory.

## Design discussion

The project connects pointer manipulation with inventory state transitions. Useful discussion points include duplicate-key handling, maintaining list invariants, deletion during traversal, and the distinction between constant-time unlinking and linear-time search.

This is an in-memory simulation, with no RFID hardware integration or persistent storage. Prices currently use `double`; decimal monetary arithmetic would be a useful extension for a production-oriented version.
