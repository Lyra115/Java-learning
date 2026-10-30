# Elevator Simulation — FIFO & Direction-Aware Scheduling / 电梯调度模拟

A Java console project that simulates passenger requests in a multi-elevator building. It includes a required **FIFO, single-passenger simulator** and an extra-credit **direction-aware, multi-passenger simulator**, plus a custom text-based visualization for debugging.

这是一个 Java 多电梯控制台模拟项目，包括必做的 **FIFO 单乘客调度**、拓展实现的 **同向顺路接客与多乘客调度**，以及我额外制作的逐时间步控制台可视化，方便观察状态转换和排查错误。

## Highlights / 项目亮点

- **Two scheduling modes / 两种调度模式:** choose `0` for the regular FIFO simulation or `1` for the SCAN-inspired, direction-aware extension.
- **Stateful simulation / 状态模拟:** each time unit can generate one random request; elevators move at most one floor per unit.
- **Accurate pickup statistics / 接客等待时间:** waiting time ends when a passenger is picked up, not when they reach the destination. Requests not picked up by the simulation deadline are excluded.
- **Input validation / 输入校验:** invalid probabilities, floor counts, elevator counts, and simulation lengths prompt the user to try again.
- **Custom debugging visualization / 自制调试可视化:** a switchable console floor grid shows elevator positions, states, new requests, queue contents, and pickup counters. This was added to make the simulation easier to verify step by step.

## Modes and algorithms / 调度算法

### 0 — Regular FIFO simulator / 普通 FIFO 模拟

One shared `RequestQueue` (an `ArrayList<Request>` subclass) stores requests in arrival order. Each idle elevator takes the oldest request. An elevator serves only one passenger at a time, first moving to the source floor and then to the destination. The states are `IDLE`, `TO_SOURCE`, and `TO_DESTINATION`.

所有请求进入一个 FIFO 队列。空闲电梯按先入先出顺序接单，每次只能处理一名乘客，先前往起点接客，再将其送到终点。

### 1 — Direction-aware extension / 同向多乘客拓展

`OptimalSimulator` implements a **SCAN-inspired dispatch heuristic**:

1. Keep waiting requests in a list for each floor.
2. Track every elevator's floor, current direction, target, and onboard passengers.
3. At a stop, drop off passengers and board all waiting passengers traveling in the elevator's direction.
4. Keep traveling while requests or onboard destinations remain ahead; otherwise become idle.
5. Send idle elevators toward the nearest eligible waiting floor, avoiding a target already claimed by another elevator when possible.

电梯沿当前方向接载同向乘客，可同时搭载多名乘客；方向上没有待处理任务时，重新选择任务。空闲电梯优先前往最近、未被其他电梯认领的请求楼层。

**Scope:** `OptimalSimulator` is the assignment's class name; this heuristic is **not a proof of globally optimal scheduling**. The two modes generate random requests independently, so one run of each cannot establish a controlled performance improvement.

**说明：** “Optimal” 是作业指定类名，这里采用启发式调度，并不保证数学意义上的全局最优。两种模式独立随机生成请求，单次结果不能直接作为严格的性能对比。

## My debugging feature / 我增加的调试功能

In `Simulator.java`, set `DEBUG=true` (the submitted default is `false`) and recompile. The console prints a floor-by-floor snapshot after each simulation time step.

在 `Simulator.java` 中将 `DEBUG` 改为 `true` 并重新编译，可以查看每一步的楼层网格、请求队列与电梯状态。默认关闭，以便长时间模拟时保持简洁输出。

Example of the text layout / 控制台示意：

```text
Step 1: new request 3 -> 5
  floor  1   2   3   4   5
  E1     .  [S]  .   .   .    going to floor 3 to pick up (3 -> 5)
  E2    [I]  .   .   .   .    idle
  queue: empty
```

`[I]` = idle, `[S]` = heading to pickup, `[D]` = carrying a passenger. `OptimalSimulator` also contains a separate optional trace with elevator direction and rider information.

`[I]` 表示空闲，`[S]` 表示前往接客，`[D]` 表示载客运行；优化模拟器也有独立的方向与乘客调试输出。**This is console text visualization, not a graphical user interface / 这是控制台文本可视化，不是图形界面。**

## Build and run / 编译运行

Requires a JDK (Java 17 works). Run these commands from the `elevator simulation` directory on macOS/Linux:

需要 JDK（已使用 Java 17 验证）。在 `elevator simulation` 文件夹下运行：

```sh
mkdir -p out
javac -encoding UTF-8 -d out *.java
java -cp out hw3.Analyzer
```

On Windows, create the `out` directory first (`mkdir out` in PowerShell/CMD), then run the same `javac` and `java` commands.

Enter four parameters on separate lines: arrival probability in `[0, 1]`, number of floors `> 1`, elevators `> 0`, and simulation time units `> 0`. Then choose `0` for regular or `1` for direction-aware simulation.

按提示分别输入请求概率、楼层数、电梯数、模拟时长，再输入 `0` 或 `1` 选择算法。

## Source structure / 代码结构

| File | Responsibility / 职责 |
| --- | --- |
| `Analyzer.java` | Console entry point, validation, and mode selection / 主程序、输入校验与模式选择 |
| `Simulator.java` | FIFO, one-passenger simulation and custom trace / 普通模拟与可视化 |
| `OptimalSimulator.java` | Direction-aware, multi-passenger extension / 同向多乘客优化模拟 |
| `Elevator.java` | Current floor, state, assigned request / 电梯状态 |
| `Request.java` | Passenger source, destination, arrival time / 请求信息 |
| `RequestQueue.java` | FIFO queue built on `ArrayList` / 队列 |
| `BooleanSource.java` | Probabilistic arrival generator / 随机请求生成 |
| `EmptyQueueException.java`, `InvalidInputException.java` | Error handling / 异常处理 |

The FIFO queue's `dequeue()` uses `ArrayList.remove(0)`, so removing the front item is O(q) in the number of queued requests. The direction-aware extension trades algorithmic simplicity for additional per-floor lists and dispatch scans.

普通队列使用 `ArrayList.remove(0)`，因此队首出队的时间复杂度为 O(q)；优化算法通过按楼层维护等待请求列表以及扫描调度，支持多乘客顺路接送。

## Verification and limitations / 验证与边界

- All nine Java source files compiled successfully with Java 17. / 九个 Java 文件已通过 Java 17 编译。
- Both mode choices (`0` and `1`) were exercised from `Analyzer`, each producing exactly one set of results. / 主程序两种模式均已运行，并分别输出一组统计结果。
- Input validation, zero-arrival behavior, two-decimal average formatting, and short simulated runs were checked. / 检查了输入校验、无请求情况、保留两位小数以及短时模拟。
- Averages are based on passengers **picked up within the time limit**; passengers still waiting are excluded. / 平均等待时间只统计模拟结束前已经接到的乘客。
- Random requests are not seeded, so runs are not identical. This is a console simulation with no GUI or physical elevator interface. / 随机过程未固定种子；这是控制台模拟，没有图形界面或真实电梯硬件接口。

This project grew from a Java data-structures assignment, with additional tracing and a separately selectable direction-aware extension. / 项目基于 Java 数据结构课程作业完成，并增加了便于调试的文本可视化和独立可选的调度拓展。
