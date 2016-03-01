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

![](http://sc.tamu.edu/Images/NehalemMemBlock.PNG)

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

> Task : Consider what can happen if we measure the elapased time for running a method in our program. Even doing something like `double elapsedTime = static_cast<double>(clock() - start) / CLOCKS_PER_SEC;`

Alternatives exists in order to count (well ... estimate) the number of CPU *cycles* taken by a program : 
- [perl](https://perf.wiki.kernel.org/index.php/Main_Page)
- [Valgrind](http://valgrind.org/)
- [VTune amplifier XE](https://software.intel.com/en-us/intel-vtune-amplifier-xe)
- ["optimize"](http://www.agner.org/optimize/)

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

## Branch predictions and compiler optimisations.

Consider the source code offered [here](https://gist.github.com/cgravier/566f8269f4e3c8c2ebc4).

Compile it with the `-O0`flag of `g++` (this limits compiler optimisations, we will put them fully back later).

> Task : What does that source code actually do ? What are the executoin differences between summing a sorted or unsorted array in this example ? Can you identify a `perf` metric that explain this ?

OK, now you should have a good idea on what is going on. Relax and take a breath by [reading about trains in 1800](http://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-an-unsorted-array).

> Task : Can you devise a branchless implementation of the conditional statement ?

Now [we will rerun the original code](https://gist.github.com/cgravier/566f8269f4e3c8c2ebc4), but this time by compiling it with `-O3`(GCC will make as many optimization as possible).

> Task : Compile with all optimizations : what do you observe ? What can you assume ?

In order to grasp what's going on, we will have a look at the assembly code generated by the compiler. You can display the assembly code using the `objdump` command line utility. For instance, if I want the assembly code for the binary file `main`, I will type : `objdump -d ./main`.

What will be of interest is the assembly code for our functions that perform the sum (`doSum`). You should be able to easily locate this section in the assembly code.

Using `-O3`flag, here is what I get :

    400c10:	83 ff 7f             	cmp    $0x7f,%edi
    400c13:	48 89 f0             	mov    %rsi,%rax
    400c16:	7e 06                	jle    400c1e <_Z9branchSumix+0xe>
    400c18:	48 63 ff             	movslq %edi,%rdi
    400c1b:	48 01 f8             	add    %rdi,%rax
    400c1e:	f3 c3                	repz retq 

Prompt the assembly for your `-O3` and `-O0` compilations.

> Task (Optional) : If you are brave enough, you can explain what are the differences between the two versions of CPU instructions that were generated by the compiler for the same C++ lines.

* In all case, remember that the actual instructions executed by your processor never exactly match what you did wrote in your programming language ! -- though you should now be no longer be destabilized by this statement. *


## Side remarks without tasks if time allows
On instruction caches.
On write behaviours.
On TLBs.


## Links
[Every number latency a programmer should know](https://gist.github.com/hellerbarde/2843375)

[The Haswell Microarchitecture - 4th Generation Processor] (http://www.ijcsit.com/docs/Volume%204/vol4Issue3/ijcsit2013040321.pdf)

[Haswell wikipedia] (https://en.wikipedia.org/wiki/Haswell_(microarchitecture))

[Haswell readworldtech](http://www.realworldtech.com/haswell-cpu/5/)

[CPU world](http://www.cpu-world.com/sspec/SR/SR1QH.html)


