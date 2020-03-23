# Module FISE2 - HPP 2019-2020

## Introduction to this course

This course introduces paradigms, concepts and tools for creating and benchmarking high-performance computer programs.
For the past years, the trend has been to scale by distributing applications to different servers.
While this is of the utmost practical interest for large-scale applications, it also comes with a significant I/O penalty as we will see in this module.

In this course, we will be focusing on how to make a single node applications (running on your laptop, a commodity server...) efficient in terms of performances.

For this, we will explore optimizations techniques based on two main paradigms :
- [Mechanical Sympathy](http://mechanical-sympathy.blogspot.fr/) : we will discover how modern computer architecture can influence the pratical efficiency of various algorithms. We will especially study the effect of the cache hierarchy, the CPU pipelines mechanisms, and the usage of CPU instruction sets (especially [SIMD instructions](https://www.kernel.org/pub/linux/kernel/people/geoff/cell/ps3-linux-docs/CellProgrammingTutorial/BasicsOfSIMDProgramming.html)).
- [Multithreading](http://docs.oracle.com/javase/tutorial/essential/concurrency/procthread.html) : we will observe different scenario in which a programmer can identify that his program is memory, cpu or I/O limited. From these observations, we will then see different techniques for handling synchronisation when programming parallelism at the computer level (barriers, latches, CAS instructions). Architectures and good/bad practises of such multitheaded programs will also be studied.

Most of the Labs will be offered in **Java** with respect to feedback of former students. However, the SIMD part will be offered in **C++**, as it is simpler to access low-level instructions. 

Ultimately, you will put your newly acquired skills and mindset to a HPP project running after the HPP course itself (see organization of the module below).


## Organization

The timeframe for the aforementionned activities is as provided by the following picture :
![](./resources/figures/orga.png)

The two instructors for this course are :
- Guillaume Muller <guillaume.muller@univ-st-etienne.fr>
- Christophe Gravier <christophe.gravier@univ-st-etienne.fr>


## Evaluation

You will have a total of 5 marks as follows (each colour in the previous organization table leads to notation).

HPP course:
- [33%] Your code on mechanical sympathy delivered for the 30th April 2020 [here](https://mootse.telecom-st-etienne.fr/mod/assign/view.php?id=13592)
- [33%] Your code on SIMD delivered for the 30th April 2020 [here](https://mootse.telecom-st-etienne.fr/mod/assign/view.php?id=13593) (optional)
- [33%] Your defense on new hardaware for HPP on the 7th May 2020


HPP Project:
- [50%] Your code for the project delivered for the 25th May 2020 [here](https://mootse.telecom-st-etienne.fr/mod/assign/view.php?id=13594)
- [50%] Your defense for the HPP project on the 25th May 2020 (no slides, presentation of architecture and results on your computer)


## Labs materials

- [Mechanical sympathy](./lab1/README.md)
- [New hardware for HPP](./lab3/README.md)
- [Mechanical sympathy / SIMD](./lab1simd/README.md)
- [HPP Project](./project/README.md)
