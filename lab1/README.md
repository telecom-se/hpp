# Mechanical Sympathy

It is quite obvious that when the CPU execute an instruction, it does not fecth the data each time from memory - this will make it wait too many cycles to actually get the data (waiting for it) before actually processing it. This observation is even more true considering the _Memory Wall_.

You can have the intuition that different levels of _caches_ store those data so that the CPU can have a fast access to them when required.

The real questions are :
- How many levels of caches does a CPU have ?
- How many times (number of CPU cycles) does it take to fetch a data from the memory to each of these cache ?
- How the CPU handle cache accesses while still actually executing instructions ?
- Last but not least : **Why do these low-level considerations are of the utmost practical interest for the programmer ?**

In what follows, different programming activities will make your observe and then conclude on answers to those questions.

## Who is actually executing your instructions ?

As a warm-up, we want to get an idea on the architecture of our CPU(s) that is actually handling data in our algorithms. All CPUs are not the same, right ?

Take the example of a "old" [Intel i5 760](http://ark.intel.com/fr/products/48496/Intel-Core-i5-760-Processor-8M-Cache-2_80-GHz) processor. It is based on a Nehalem microarchitecture, and a block schema for this architecture is provided by Texa A&M University :

![]({{site.baseurl}}/http://sc.tamu.edu/Images/NehalemMemBlock.PNG)

As a large number of modern processors, its architecture uses three level of cache : Level 1 (L1), Level 2 (L2), and Level 3 (L3 -- sometimes: Last Level Cach (LLC)). This is a very common cache hierarchy architecture.

> Task : What is your processor ? What is its cache hierarchy ? What are the sizes and latencies of each data storage units (cache and main memory) ?

> Task : Why does it matter ? (_we will discuss some numbers every programmers should know_)

## Play time !

In what follows, we will create some C++ programs in order to observe the effect of the cache hierarchy on simple data structure tasks.

For this, we will use the following data structure (borrowed, like most of thisp art of the labs, [from a nice document from U. Drepper](https://people.freebsd.org/~lstewart/articles/cpumemory.pdf)).

    const int NPAD = 0;
    
    struct l {
	   struct l *n;
	   long int pad[NPAD];
    };

This structure represents a cell in a linked list (The field `n` points to the next cell). The field `pad` will be of various size controlled by `NPAD` integer. When `NPAD` is `0`, `sizeof(struct l)` equals `sizeof(l*)`. More generally, `sizeof(struct l) == (NPAD+1)*sizeof(l*)`. We will be using this to make a cell more or less big, but for now `NPAD == 0`.

> Task : Write a C++ program that create a array of variable of type `l` so that the dimension of the array is 2^k bytes (k is an argument).

Given what's before, this can be misleading. This will make perfect sense in the next task. This program will be used to measure the _time_ needed to sequentially walk every value in a 2^k bytes array.

## Sequential Read Walks and block size influence

In order to sequentially walk this linked list of values "packed" into a contiguous memory area (which was been forced as we put all cells into a array), we will restrict ourself to traverse the array using the pointers in each cell (and not the `[]` notation).

Next, in order to actually measure the sequential read walk 'time' for various value for `k`, we will need to able to "measure" the elasped time for such a sequential read walk. Here things becomes to be quite messy !

> Task : Consider what can happen if we measure the elapased time for running a method in our program.

Alternatives exists in order to count (well ... estimate) the number of CPU *cycles* taken by a program : 
- [perl](https://perf.wiki.kernel.org/index.php/Main_Page)
- [Valgrind](http://valgrind.org/)
- [VTune amplifier XE](https://software.intel.com/en-us/intel-vtune-amplifier-xe)
- ...

As we are relying on free software and we want to measure the number of CPU cycles for a single function, we will opt for another alternative offered by the [FTTW software](http://www.fftw.org/download.html), that is its Cycles counter module.

> Task : For k in {10 .. 28}, measure the estimated CPU cycles per linked list element required to traverse the linked list of memory size 2^k - as built at the previous task. What do you observe ? Why ?

You will now try to see how the size of the structure influence this result.

> Task : Same as previous task BUT change `NPAD` from `0` to `15`. What do you observe w.r.t. the figure when `NPAD == 0`? What can you make as a hypothesis ?

> Task : In order to confirm your thought - or guide you if you are clueless - plot the cache-misses ratio using the `perf` software. Hint: you can make stat on the events `cache-misses` and `cache-references` in order to have the ratio of cache-misses. What do you conclude ? What is the additional observation you couldn't make in the previous task ?

## From left-to-right or right-to-left ?

We want to compare the previous results for a different traversal of the linked list. We have traverse the link sequentially, from left to right. The question is whether the direction of the walk influence the results.

> Task : Re-run the previous experiments for both `NPAD`values, but sequentially traverse the linked list from end to start. What do you conclude ?

## Sequential vs Random Read Walks and influence of prefetching

Would it be from left-to-right or right-to-left, we have traverse the linked list in a predictable pattern. What happens if you randomly traverse that list, i.e. we jump from one cell to another with no specific predictable order ?

In order to do so, you will have to first "shuffle" the linked list (i.e. to wire the cells in no particular predicable order).

> Task : Set `NPAD`to `0`, and see for the different values of `k`, the correlation between CPU cycles and cache-misses ratio. Plot the differences between sequential and random walks in the list. Can you explain what you get ?

## Branch prediction


## Side remarks
On instruction caches.
On write behaviours.







