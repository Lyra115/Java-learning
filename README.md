# Java Data Structure Applications / Java 数据结构应用

本仓库包含两个面向实际流程的 Java 控制台应用：有序音频播放列表管理，以及基于 RFID 的零售库存跟踪。两个项目都直接实现底层数据结构，未将核心存储委托给集合框架。

This repository contains two Java console applications built around practical workflows: ordered audio playlist management and RFID-based retail inventory tracking. Both implement their core storage structures directly rather than delegating them to collection classes.

## Projects / 项目

| Project / 项目 | Purpose / 功能 | Core implementation / 核心实现 |
| --- | --- | --- |
| [Audio Playlist Manager / 音频播放列表管理器](playlist%20of%20audio%20files/README.md) | Organize song metadata and manage named playlists / 管理歌曲信息与多个具名播放列表 | Fixed-capacity array, positional updates, deep copying / 定长数组、按位置更新与深拷贝 |
| [RFID Inventory Manager / RFID 库存管理器](doubly-linked%20list%20application/README.md) | Track items across shelves, carts, and checkout / 跟踪货架、购物车与结账状态 | Custom doubly linked list, ordered insertion, node removal / 自定义双向链表、有序插入与节点删除 |

两个应用仅依赖 Java 标准库。它们体现了领域数据、存储操作与控制台交互的拆分，也记录了遍历、插入、查找和删除的复杂度。

Both applications use only the Java standard library. They separate domain records, storage operations, and console interaction, and document the costs of traversal, insertion, lookup, and removal.

## Getting started / 开始使用

从仓库根目录克隆 `homework` 分支，并按项目 README 中的命令编译和运行：

Clone the `homework` branch, then build and run either project using the commands in its README:

```sh
git clone --branch homework --single-branch https://github.com/Lyra115/Java-learning.git
cd Java-learning
```

需要可用的 JDK（包含 `javac` 和 `java`）。项目指南将编译产物写入 `out` 目录。

A JDK with `javac` and `java` available is required. The project guides place compiled classes in the `out` directory.

## Scope / 项目范围

这两个程序均为交互式内存应用，运行期间维护状态；不包含数据库持久化、图形界面、外部服务集成或真实 RFID 硬件接入。

Both applications are interactive and in-memory. They maintain state during a session and do not include database persistence, a graphical interface, external service integrations, or physical RFID hardware.
