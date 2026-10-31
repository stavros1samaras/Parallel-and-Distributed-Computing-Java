# Parallel and Distributed Computing — Assignments

A collection of the assignments completed for the **"Parallel and Distributed Computing"** course
at the **University of Macedonia** (Πανεπιστήμιο Μακεδονίας), Department of Applied Informatics.

All programs are written in **Java** and are organised by assignment. The first group (assignments 1–8)
covers shared-memory parallelism (threads, locks, semaphores, synchronisation, parallel algorithms),
while the second group (assignments 9–12) covers distributed systems (TCP sockets, client/server,
multithreading on the server, and Java RMI).

---

## Repository structure

```
github/
├── parallel computing/
│   ├── assignment 1/       Threads: creation, start/join, thread states
│   ├── assignment 2/       Data races vs. correct synchronisation (synchronized / locks)
│   ├── assignment 3/       Synchronised methods, blocks, locks, dining philosophers
│   ├── assignment 4/       Producer–Consumer (locks / semaphores / synchronized), car park
│   ├── assignment 5 (not done all)/   Static thread distribution (vector/matrix add, RGB→grayscale)
│   ├── assignment 6/       Lock contention, reentrant locks, brute-force string matching
│   ├── assignment 7/       Histogram, word count, sieve of Eratosthenes (static/dynamic/cyclic)
│   └── assignment 8/       Parallel numeric integration (π) and merge sort
└── distributed computing/
    ├── assignment 9/       TCP echo server/client (iterative, multithreaded)
    ├── assignment 10/      Multithreaded count server, chat server, caching
    ├── assignment 11/      Master/worker sum of π, count server with request/reply objects
    └── assignment 12/      Java RMI (counter, e-mail management), socket server
```

> **Note:** `assignment 5 (not done all)` is only partially completed.

---

## Prerequisites

- **JDK 8 or newer** (the code uses only the standard Java SE library — `java.net`, `java.util.concurrent`, `java.rmi`).
- `javac` and `java` available on your `PATH`. Verify with:

```bash
java -version
javac -version
```

- A terminal (on Windows: PowerShell, CMD or Git Bash). **No external libraries or build tools are required.**

---

## How to run — Parallel Computing (assignments 1–8)

Every program is a standalone class with a `main` method.
Go into the folder of the assignment you want, compile all files, then run the entry class:

```bash
cd "parallel computing/assignment 7/q2"
javac *.java
java WordCountPar
```

### Folders that declare a `package`

A few folders start with a `package` declaration, so the class must be run with its
fully-qualified name **from the parent folder**:

```bash
cd "parallel computing/assignment 8"     # parent of q1/ and q2/
javac q1/*.java q2/*.java
java q1.NumIntPar
java q2.MergeSort
```

The affected folders (the package name always equals the folder name) are:

- `assignment 4/q1/Semaphores` → `java Semaphores.ProducerConsumer` (run from `assignment 4/q1`)
- `assignment 4/q1/sync` → `java sync.ProducerConsumer` (run from `assignment 4/q1`)
- `assignment 8/q1` → `java q1.NumIntPar` (run from `assignment 8`)
- `assignment 8/q2` → `java q2.MergeSort` (run from `assignment 8`)

Useful entry points:

| Assignment | Folder                                                  | Entry class                                                                                                               |
| ---------- | ------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| 1          | `assignment 1`                                          | `firstAndSecondQuestion`, `thirdAndFourthQuestion`                                                                        |
| 2          | `assignment 2`                                          | `firstCodeWithOpt`, `firstCodeWithLock`, `firstCodeWithOptWithLock`, `firstCodeFromMainLock` (and the `second*` variants) |
| 3          | `q1`, `q2`, `q3`                                        | `firstCodeSync`, `DetailedSync`, `Diners`, `Diners2` …                                                                    |
| 4          | `q1/{locks,Semaphores,sync}`, `q2`, `q3/{loc,sem,sync}` | `ProducerConsumer`, `CarParkMain`                                                                                         |
| 5          | `q1`, `q2`                                              | `VectorAddStaticThreads`, `MatrixAddStaticThreads`, `RGBtoGrayScaleStaticThreads`                                         |
| 6          | `q1`, `q2`, `q3`                                        | `ReentrantLocks`, `BruteForceStringMatchPar`, `NoLocksLinkedLists`                                                        |
| 7          | `q1`, `q2`, `q3`                                        | `StringHistogramParArray`, `WordCountPar`, `SieveOfEratosthenesStatic`                                                    |
| 8          | `q1`, `q2`                                              | `NumIntPar`, `MergeSort` (packaged — see above)                                                                           |

Notes:

- `RGBtoGrayScaleStaticThreads` (assignment 5, q2) takes the input and output image as arguments, e.g.
  `java RGBtoGrayScaleStaticThreads input.jpg output.jpg` (supply your own image).
- The number of threads is usually set with a constant inside the source file (e.g. `N_THREADS`) —
  adjust it and recompile to experiment with different configurations.
- Compiled `.class` files are not included — run `javac` before running anything.

> **Data files:** the large input corpora used by assignments 6 and 7
> (`E.coli`, `bible.txt`, `world192.txt`) were **deleted** to keep the repository small.
> Download them separately (e.g. the Pizza&Chili or Canterbury corpus) and place them in the
> corresponding `q` folder before running those programs.

---

## How to run — Distributed Computing (assignments 9–12)

These programs consist of a **server** and one or more **clients**. The server must be started first,
otherwise the client will fail to connect.

The default connection parameters, hardcoded as constants, are:

- `HOST = "localhost"`
- `PORT = 1234` (TCP assignments)
- RMI registry port `1099` (assignment 12)

### TCP exercises (assignments 9, 10, 11)

Open two terminals. In the **first** one, start the server; in the **second**, the client.

```bash
# Terminal 1 — server
cd "distributed computing/assignment 9/q1"
javac *.java
java EchoServerTCP

# Terminal 2 — client
cd "distributed computing/assignment 9/q1"
javac *.java
java EchoClientTCP
```

Type a message in the client terminal; the server echoes it back. Stop with `Ctrl+C` in each terminal.

If a folder declares a `package`, compile and run from its parent folder using the
fully-qualified class name, for example:

```bash
cd "distributed computing/assignment 9/q3"
javac q1/*.java
java q1.MultithreadedEchoServerTCP   # server
java q1.EchoClientTCP               # client (other terminal)
```

Common server/client pairs:

| Assignment | Folder                                        | Server                        | Client                                 |
| ---------- | --------------------------------------------- | ----------------------------- | -------------------------------------- |
| 9 q1       | `assignment 9/q1`                             | `EchoServerTCP`               | `EchoClientTCP`                        |
| 9 q2       | `assignment 9/q2`                             | `IterativeEchoServerTCP`      | `EchoClientTCP`                        |
| 9 q3       | `assignment 9/q3/q1` (`q1.*`), `q2` (`q2.*`)  | `MultithreadedEchoServerTCP`  | `EchoClientTCP`                        |
| 10 q1      | `assignment 10/q1/q1` (`q1.*`), `q2` (`q2.*`) | `MultithreadedCountServerTCP` | `CountClientTCP`                       |
| 10 q2      | `assignment 10/q2`                            | `MultithreadedChatServerTCP`  | `ChatClient`                           |
| 11 q1      | `assignment 11/q1` (`q1.*`), `q12` (`q12.*`)  | `MultithreadedSumMasterTCP`   | `SumWorkerTCP` (start several workers) |
| 11 q2      | `assignment 11/q2` (`q2.*`)                   | `MultithreadedCountServerTCP` | `CountClientTCP`                       |

Notes:

- Since all TCP servers use port `1234`, run **only one server at a time**.
- To run the client on a different machine, change `HOST` in the client source to the server's IP address.
- For the chat server (assignment 10, q2) start one server and two or more `ChatClient` instances.

### Assignment 11, q1 — master/worker

Start the master first, then start one or more workers:

```bash
cd "distributed computing/assignment 11"   # parent of q1/
javac q1/*.java
java q1.MultithreadedSumMasterTCP     # terminal 1
java q1.SumWorkerTCP                  # terminal 2 (repeat in more terminals for more workers)
```

### Assignment 12 — Java RMI

The RMI programs create their own registry on port `1099` inside the server code,
so you do **not** need to start `rmiregistry` manually. Start the server first, then the client.

**a) Simple RMI counter (`assignment 12/q1`)**

```bash
cd "distributed computing/assignment 12/q1"
javac *.java
java Server      # terminal 1
java Client      # terminal 2
```

**b) E-mail management (`assignment 12/q2/q21/email` and `assignment 12/q2/q22`)**

```bash
cd "distributed computing/assignment 12/q2/q21/email"
javac *.java
java ManagementEmailsServer   # terminal 1
java ManagementEmailsClient   # terminal 2
```

**c) RMI counter with client/server packages (`assignment 12/q2/q21/RMICounter`)**

```bash
cd "distributed computing/assignment 12/q2/q21/RMICounter"
javac server/*.java client/*.java
java -cp server Server        # terminal 1
java -cp client Client        # terminal 2
```

---

## Troubleshooting

| Problem                                  | Cause / fix                                                                                                  |
| ---------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| `Address already in use: bind`           | Another server is still running on port `1234`. Stop it (`Ctrl+C`) or change `PORT`.                         |
| `Connection refused`                     | The server was not started, or `HOST`/`PORT` do not match the server.                                        |
| `java.lang.ClassNotFoundException`       | You are not in the classpath root. For packaged folders run `java <package>.<Class>` from the parent folder. |
| `java.lang.NoClassDefFoundError: q1/...` | Same cause — the package folder must be reachable from the current directory.                                |
| `NotBoundException` (RMI)                | The RMI server is not running yet — start the server before the client.                                      |
| `javac: file not found`                  | Quote paths that contain spaces: `cd "parallel computing/assignment 8/q1"`.                                  |

---

## License / usage

These programs were written for educational purposes as part of the course assignments.
Feel free to use them as reference material.
