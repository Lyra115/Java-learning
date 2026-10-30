# Java Data Structure Applications / Java 数据结构应用

A collection of Java console projects demonstrating data structures, stateful simulations, and practical problem solving. Each project includes source code, bilingual documentation, and build instructions.

这里整理了我的 Java 数据结构与控制台应用作品，涵盖顺序存储、自定义链表和多电梯调度模拟。每个项目都有源代码、中英文说明及编译运行方法。

## Projects / 项目列表

| Project / 项目 | What it does / 功能 | Engineering highlights / 技术亮点 |
| --- | --- | --- |
| [Elevator Simulation / 电梯调度模拟](elevator%20simulation/README.md) | Simulate passenger requests and multiple elevators / 模拟请求与多电梯运行 | FIFO queue, state machine, SCAN-inspired multi-passenger dispatch, custom console visualization / 队列、状态机、同向多乘客调度与自制调试可视化 |
| [Audio Playlist Manager / 音频播放列表管理器](playlist%20of%20audio%20files/README.md) | Maintain song metadata and named playlists / 管理歌曲与具名播放列表 | Fixed-capacity array, positional updates, deep copy / 定长数组、按位置更新与深拷贝 |
| [RFID Inventory Manager / RFID 库存管理器](doubly-linked%20list%20application/README.md) | Track items through shelves, carts, and checkout / 跟踪商品在货架、购物车和结账流程中的状态 | Custom doubly linked list, ordered insertion, node removal / 自定义双向链表、有序插入与节点删除 |

## Getting started / 开始使用

Clone the `homework` branch, open a project folder, and follow that project's README. A working JDK is required.

克隆 `homework` 分支后，进入对应项目文件夹，按项目 README 中的命令编译运行。需要安装 JDK。

```sh
git clone --branch homework --single-branch https://github.com/Lyra115/Java-learning.git
cd Java-learning
```

## What these projects demonstrate / 项目能力

- Data structures chosen for specific tasks, with complexity notes where relevant. / 面向实际需求选择数据结构，并关注时间复杂度。
- Separation of input handling, domain state, and core operations. / 分离输入处理、领域状态和核心操作。
- Validation, error handling, and documented trade-offs. / 输入校验、异常处理及设计权衡。
- Instrumentation for understanding behavior: the elevator project adds a switchable step-by-step console view. / 电梯项目额外提供可切换的逐步控制台可视化，帮助理解运行过程。

The projects are educational console applications. They use the standard Java libraries; they do not rely on a database, graphical interface, or real RFID/elevator hardware.

这些项目是学习与实践性质的 Java 控制台程序，使用 Java 标准库，不依赖数据库、图形界面或真实 RFID/电梯硬件。
