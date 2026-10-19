# RFID Inventory Manager / RFID 库存管理器

该 Java 控制台应用跟踪零售商品从原货架到购物车、结账及离库的状态变化。存储层使用按 RFID 排序的自定义双向链表。

This Java console application tracks retail items from their original shelf through cart movement, checkout, and removal from active inventory. Its storage layer is a custom doubly linked list kept in RFID order.

## Features / 功能

- 新增包含名称、RFID 标签、价格和原货架位置的商品记录。  
  Insert inventory records with a name, RFID tag, price, and original shelf.
- 插入时维持 RFID 顺序，并允许标签重复。  
  Keep records sorted by RFID during insertion, including duplicate tags.
- 在货架或购物车位置之间移动单件商品。  
  Move one matching item between a shelf or cart and another location.
- 按位置列出、筛选或通过 RFID 查找记录。  
  List all records, filter by location, or find items by RFID.
- 结算购物车、计算总额并标记已购商品。  
  Check out a cart, calculate its total, and mark its items as purchased.
- 归还错放商品并从活动库存中移除已购记录。  
  Return misplaced items and remove purchased records from active inventory.

## Data model / 数据模型

| Field / 字段 | Format / 格式 |
| --- | --- |
| Name / 名称 | 非空，最多 20 个字符 / Non-empty, up to 20 characters |
| RFID | 9 位十六进制字符，规范化为大写 / Nine hexadecimal characters, normalized to uppercase |
| Original location / 原货架 | 例如 `s12345` / For example, `s12345` |
| Current location / 当前位置 | 货架码、购物车码（如 `c105`）或 `out` / Shelf code, cart code such as `c105`, or `out` |
| Price / 价格 | 正数，显示两位小数 / Positive value displayed to two decimal places |

RFID 标签可以重复，每个链表节点对应一件商品。移动操作同时匹配标签和来源位置，只移动其中一条记录；控制台不允许手动移动到 `out`，该状态由结账操作设置。

RFID tags can repeat, with each node representing one item. A move matches both tag and source location and updates one record. Manual moves to `out` are rejected; checkout sets that state.

## Implementation / 实现

| Class / 类 | Responsibility / 职责 |
| --- | --- |
| `ItemInfo` | 库存字段、输入校验与规范化 / Inventory fields, validation, and normalization |
| `ItemInfoNode` | 商品引用及前后节点链接 / Item reference and previous/next links |
| `ItemList` | 有序插入、遍历、移动、结账及删除 / Ordered insertion, traversal, movement, checkout, and removal |
| `DepartmentStore` | 交互式控制台菜单 / Interactive console menu |
| `InvalidDataException` | 领域数据校验错误 / Domain validation errors |

插入从尾节点向前查找并连接到合适位置；展示操作可直接按链表顺序遍历。删除已购商品时先保存后继引用，因此一次遍历可连续删除多个节点。删除边界节点（包括最后一个节点）时会同步更新头尾引用。移动商品只改变位置字段，不改变 RFID 排序。

Insertion searches backward from the tail and links each new node into its ordered position. Display operations traverse the existing order without sorting. During purchased-item removal, the next reference is saved before unlinking, allowing consecutive removals in one traversal. Head and tail references are updated when deleting boundary nodes, including the last remaining node. Moving an item changes its location but not its RFID order.

对于 n 条库存记录，读取长度或判断是否为空为 O(1)；查找并插入、移动、列出或筛选最坏为 O(n)；结账、归还错放商品和批量删除为 O(n)；定位节点后摘链为 O(1)。存储空间为 O(n)，每个节点维护两条链接。

For n inventory records, reading size or checking emptiness is O(1). Finding and inserting, moving, listing, or filtering is O(n) in the worst case. Checkout, restoring misplaced items, and bulk removal are O(n); unlinking an already located node is O(1). Storage is O(n), with two links per node.

## Build and run / 编译运行

在仓库根目录执行：

Run from the repository root:

```sh
javac -encoding UTF-8 -d out "doubly-linked list application/InvalidDataException.java" "doubly-linked list application/ItemInfo.java" "doubly-linked list application/ItemInfoNode.java" "doubly-linked list application/ItemList.java" "doubly-linked list application/DepartmentStore.java"
java -cp out hw2.DepartmentStore
```

无需外部依赖或构建工具。

No external libraries or build tool are required.

## Console commands / 控制台命令

| Command / 命令 | Action / 操作 |
| --- | --- |
| I | 新增商品 / Insert an item |
| M | 移动匹配的商品 / Move one matching item |
| L / P / R | 按位置列出、打印全部或按 RFID 查找 / List by location, print all, or find by RFID |
| O | 结算购物车 / Check out a cart |
| C | 将错放的货架商品归还原位 / Return misplaced shelf items |
| U | 移除已购商品 / Remove purchased items |
| Q | 退出 / Exit |

快速演示：使用 `I` 添加商品，在 `M` 中将其从货架移入购物车，以 `L` 查看购物车，再用 `O` 结账、`U` 移除商品，最后以 `P` 查看剩余库存。

Quick demo: add an item with `I`, move it from a shelf to a cart with `M`, inspect the cart with `L`, check out with `O`, remove purchased items with `U`, and inspect the remaining inventory with `P`.

## Scope / 范围

该程序是内存模拟，没有连接 RFID 硬件或持久存储。价格当前使用 `double`；生产系统可进一步改用十进制定点金额表示。

This is an in-memory simulation with no RFID hardware integration or persistent storage. Prices currently use `double`; a production-oriented extension could use decimal monetary arithmetic.
