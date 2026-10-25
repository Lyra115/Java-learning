# Elevator Simulation / 电梯运行模拟

A Java console simulation of multiple elevators serving a shared FIFO request queue, with a custom step-by-step text visualization for debugging.

Java 多电梯控制台模拟：使用共享 FIFO 请求队列，并提供自行增加的逐步文本可视化，便于定位错误、观察运行过程。

## Features / 功能

- Random passenger arrivals and source/destination floors; one passenger per elevator.
- Three elevator states: idle, heading to source, heading to destination.
- Input validation and pickup waiting-time statistics.
- 随机请求、单乘客电梯、三种运行状态、输入校验与接客等待时间统计。

## Debug visualization / 调试可视化

In `Simulator.java`, change `private static final boolean DEBUG=false;` to `DEBUG=true;` and recompile. Each time unit prints a floor grid, elevator positions, I/S/D state markers, new arrivals, waiting queue and pickup statistics. This console text display was added to make state transitions and queue behavior easier to inspect. The default is false.

在 Simulator.java 中将 DEBUG 改为 true 并重新编译，即可逐步查看楼层、电梯位置、I（空闲）/S（前往接客）/D（载客前往目的地）、新请求、队列和接客统计。这是额外加入的控制台文本可视化功能。

## Build and run / 编译运行

Requires a JDK. From this project directory / 在本目录运行：

```sh
javac -encoding UTF-8 -d out *.java
java -cp out hw3.Analyzer
```

Enter arrival probability (0–1), floors (>1), elevators (>0), and simulation length (>0), each on a separate line.

依次输入到达概率、楼层数、电梯数和模拟时长，每项占一行。

## Implementation / 实现

Analyzer handles console input; Simulator advances time; Elevator stores state; Request stores passenger information; BooleanSource generates arrivals; RequestQueue extends ArrayList and provides FIFO operations. Two exception classes represent invalid input and empty queues.

RequestQueue uses remove(0), so dequeue costs O(q); enqueue is amortized O(1). Each time step scans the elevators. Debug output additionally traverses floors and queued requests.

队列出队需要移动后续元素，复杂度为 O(q)；入队均摊 O(1)。每个时间步遍历电梯，调试输出还需遍历楼层和队列。

## Verification / 验证

Source inspection completed. Runtime tests have not been executed in the preparation environment because a Java compiler was not available. Reproducible manual checks:

| Input / 操作 | Expected / 预期 |
| --- | --- |
| 0, 5, 2, 10 | Total Wait Time: 0; Total Requests: 0; Average Wait Time: 0.00 |
| Invalid probability, floors 1, elevators 0, length 0 | Corresponding input prompt repeats / 对应输入重新提示 |
| End input before all parameters | Exits without starting simulation / 不启动模拟 |
| DEBUG=true; 1, 5, 2, 10 | Ten step displays, floor grid, queue and summary / 十步可视化及汇总 |

## Current limits / 当前边界

`Total Requests` counts pickups, not all generated requests. Average waiting time covers only passengers picked up before the time limit; remaining requests are not drained. Randomness is not seeded. Source and destination can be equal. The optional optimized simulator is unfinished and disabled; only regular FIFO simulation is available. The Analyzer class description still mentions method selection, but that selection is commented out.

统计仅覆盖时限内已接到的乘客，结束后不继续清空队列；随机结果不可固定复现，起点与终点可以相同。可选优化算法尚未完成、未启用；当前运行普通 FIFO 模拟。Analyzer 的类注释仍提到模式选择，但相关调用已注释。