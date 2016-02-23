# Module FISE2 - HPP 2015-2016

## Introduction to this course

This course introduces paradigms, concepts and tools for creating and benchmarking high-performance computer programs. We will be interested in how to provide efficient algorithms that runs on a sole computer (your laptop, a commoditiy server, ...).

For this, we will explore optimlizations techniques based on two main paradigms :
- [Mechanical Sympathy](http://mechanical-sympathy.blogspot.fr/) : we will see on modern computer architecture can influence the pratical efficiency of various algorithms. We will especially study the effect of the cache hierarchy, the CPU pipelines mechanisms, and the usage of CPU instruction sets (especially [SIMD instructions](https://www.kernel.org/pub/linux/kernel/people/geoff/cell/ps3-linux-docs/CellProgrammingTutorial/BasicsOfSIMDProgramming.html)).
- [Multithreading](http://docs.oracle.com/javase/tutorial/essential/concurrency/procthread.html) : we will observe diffferent scenario in which a programmer can identify that his program is memory, cpu or I/O limited. From these observations, we will then see different techniques for handling synchronisation when programming parallelism at the computer level (barriers, latches, CAS instructions). Architectures and good/bad practises of such multitheaded programs will also be studied.

The first part (Mechanical Sympathy) will be offered in **C++**, while the latter (Multithreading) will be offered in **Java**.

Following those two main course part, you will be invited to then experiment the thrill of high performance programming by trying to optimize an open source library, from a hand-picked list that we will provide to you (you will be able to choose between **C++** or **Java** library, in order to match your programming language tastes).

## Organsiation

The two instructors for this course are (by order of appearance) :
- Christophe Gravier <christophe.gravier@univ-st-etienne.fr>
- Julien Subercaze <julien.subercaze@univ-st-etienne.fr>

The timeframe for the aforementionned activities is as provided by the following picture :

## Labs materials

[Mechanical sympathy](./lab1/README.md)

[*-bound](./lab2/README.md)

[Advanced multithreading](./lab3/README.md)

[Project instructions](./project/README.md)


