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

> Task : Create a simple block diagram of your processor, including all its cores, all level of cache, main memory and disk. Add latency and size numbers. Optionally, include TLB information if you happen to have read on it and get the idea. Students will present their perception of your processor, we will disucss it and I will draw my own schema.

> Task : Why does it matter ? (_we will discuss some numbers every programmers should know_)

## Play time !

In what follows, we will create some Java programs in order to observe the effect of the cache hierarchy on simple data structure tasks.

You are strongly encourage to first read [a very nice document from U. Drepper](https://people.freebsd.org/~lstewart/articles/cpumemory.pdf)).


## Read Walks using arrays

### Utility function
In what follows, we will need to be able to build fxed-size arraysof `2^k` bytes.
For this purpose, we will be creating arrays of random `int`s, provided that your JVM stores `int`s as 32 bits (4 bytes).
When we want to build arrays of `2^k` bytes, we will be building arrays of `(2^k) / 4` random `ìnt`'s.

> Task : Write a Java class that expose a single static function to create such an array, whose size is given as parameter. That is write `public static int[] makeArray(int k)`.

### On measuring execution time

We want to measure the time needed to walk the entire array from the begining to its end, in a sequential way. In order to measure the time spent, we will be using two methods :

1) A first approximation using `System.nanoTime()`

By measuring the time before running your code traversing the array, and compute the difference with the time after your program terminates.

	// ... Building the 2^k array
	long startTime = System.nanoTime();    
	// ... traversing each item of the array ...    
	long estimatedTime = System.nanoTime() - startTime;

2) CPU time using `jvisualvm`.

Along your JDK comes a nice tools to profile your running java program which is called `jvisualvm`. If you got the the `bin`folder of your installed JVM, you can launch it from there. For profiling CPU, the basis of what we'll need are [here](https://blogs.oracle.com/nbprofiler/entry/profiling_with_visualvm_part_1). 

> Task : Explain the difference between the two ways of measuring elapsed time.

### sequential read walk

We want to measure the elasped time for walking the entire arrays of the following dimensions : `k = {20 .. 32}`.

> Task : Code the sequential array traversal and measuring the elapsed time for each values of k. Plot the results and make a guess on execution time for greater values. At each operation make something of `yourArray[i]`, for instance I suggest that you compute the cumulated sum of int in the table (let's ignore `ìnt`overflows as we won't do anything with this value).

> Task : Do the same thing but traversing the array sequentially from end to start.


### random read walk

We now want to walk `(2^k / 4`) random values from the array (maybe with repetitions). `(2^k / 4)` is the number of `int`'s in the array, so we want to fetch as many `int`from the array while preventing a sequential access. At each iteration, you will be choosing the index `i` of the array cell you want to pick from a pseudo random generator bounded in `[0; 2^k / 4 -1])`. For such generator, looking on stackoverflow can lead you to [interesting piece of code](http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range).

> Task : Code the random (with possible repetitions) walk of arrays with different values for k, i.e.  `k = {20 .. 32}`.

> Task : How do a sequential and a random walk compares ? What is your intuition behind this ? (next activity covers the study of your possible intuition(s).

### CPU counters using perf

> Task : Run both a sequential and random walk of the array (for instance for k = 24) and observe CPU cycles using the [perf](https://perf.wiki.kernel.org/index.php/Main_Page) linux tool installed on your machines.



## Read Walks using linked list

In this part we will be interested in traversing a linked list in a sequential manner for varying cell size in bytes. We will study the impact of traversing a linkedlist that lies in a contiguous memory space with respect to a a linkedlist that lies in a non contiguous space. Remember that traversing a linkedlist is not prefetched by current CPUs -- although you can see [some proposal in the academic litterature](Dependence Based Prefetching for Linked Data Structures).

### Our linkedlist

We will be using a simple LinkedList that we provide below :

	class Liste {
		int[] array = { 1, 2, 3, 4 };
		Liste next;
	}

Each cell holds a 4-`int` 's array along the pointer to the next cell. This is the pointer we will be using to travers the linkedlist sequentially.

### Linkedlist in a contiguous memory space

> Task : Provide a method to build a `Liste`of `2^k`bytes. (provided each cell contains 5 `ìnt`s, that is 4 `int`'s for the array and one as the pointer to the next cell), the number of cells is `2^k / 4 / 5` (derived from previous tasks).
In order to realize this task, you have to make sure that all cells lies in a contiguous memory space. To perfrom this, it is possible to create an array of `size`Liste elements (the elemnts will be contiguous as proporty of an array). Then you can wire each cells to the next one in the array so that you can later measure the traversal of the linkedlist using its pointers `next`, while being sure that all elements are contiguous.

> Task : Observe the execution time for `k = {20 .. 32}`


### LinkedList in a non-contiguous space

> Task : Provide another method to build a `Liste` so that you are no longer guarantee that the `liste`elements lie in a contiguous memory space. 

It is possible that, although not instanciated within an aray, that the consecutive call to `new Liste`will tend to instanciate the Liste elements in a contiguous manner. But know that having a garbage collector call will rearrange those lements in an unpredictable maner, and especially less likely to be contiguous. Usually we call `System.gc()`when we want to have the garbage collector called. This is a bit tricky because this actually means to favour a garbage collection but do not ensure it to be syncrhonous with the call to this function. Instead, to enforce the garbage colleciton when called, we will called the following function (to add in your code base) as a neat solution to perform this pause the world to garbage collection (taken from [here](http://stackoverflow.com/questions/10039474/java-guaranteed-garbage-collection-using-jlibs)) :

	/**
	 * This method guarantees that garbage collection is done unlike
	 * <code>{@link System#gc()}</code>
	 */
	public static void gc() {
		Object obj = new Object();
		@SuppressWarnings("rawtypes")
		WeakReference ref = new WeakReference<Object>(obj);
		obj = null;
		while (ref.get() != null) {
			System.gc();
		}
	}

> Task : Observe the execution time for `k = {20 .. 32}`

### Analyse

> Task : Compare the results for both contiguous linkedlist and non contiguous linkedlist. Can you make an educated guess on what you observe ?

> Task : Use `perf` software to look at different CPU counters values. Can you spot the ones that explain the observed behavior ?





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


> Task : If you are brave enough, you can explain what are the differences between the two versions of CPU instructions that were generated by the compiler for the same C++ lines.

For this, you can refer to [This guide on x86 assembly code](http://www.cs.virginia.edu/~evans/cs216/guides/x86.html) and some more [common assembler instructions](http://www.jegerlehner.ch/intel/IntelCodeTable.pdf).

* In all case, remember that the actual instructions executed by your processor never exactly match what you did wrote in your programming language ! -- though you should now be no longer be destabilized by this statement. *


## Side remarks without tasks if time allows
On instruction caches.
On write behaviours.
On TLBs.


## Useful and additional links

### About your CPU  :

[The Haswell Microarchitecture - 4th Generation Processor] (http://www.ijcsit.com/docs/Volume%204/vol4Issue3/ijcsit2013040321.pdf)

[Haswell wikipedia] (https://en.wikipedia.org/wiki/Haswell_(microarchitecture))

[Haswell readworldtech](http://www.realworldtech.com/haswell-cpu/5/)

[CPU world](http://www.cpu-world.com/sspec/SR/SR1QH.html)

[Haswell 7-cpu](http://www.7-cpu.com/cpu/Haswell.html) latencies !

[Every number latency a programmer should know](https://gist.github.com/hellerbarde/2843375)


### About CPU counters

[Intel architectures for software developers Vol 3B, p297](http://www.intel.com/content/www/us/en/architecture-and-technology/64-ia-32-architectures-software-developer-vol-3b-part-2-manual.html)


### About branch prediction :

[Branch pediction : performance of some mergesort implementations](http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-172-performance-engineering-of-software-systems-fall-2010/video-lectures/lecture-5-performance-engineering-with-profiling-tools/MIT6_172F10_lec05.pdf)



# SIMD

## What is SIMD ?

SIMD means Single Instruction Multiple Data. The objective is apply a single operation on multiple data at the same time. This is sometimes referred as vectorization (as opposed to "scalar" operations).

Think about slicing a cake : using a knife, you make one slice at a time (this is like a scalar operation in programming world). Would you have a knife that makes it possible to create 4 slices with one cut, you would do vectorization.

These instruction are CPU vendor specific (Intel, ARM, AMD, ...)

Maybe you already read some acronyms, without knowing that they are actually the Intel Vector Instructions :
- MMX (1996, Pentium)
- SSE (1999, Pentium 3)
- SSE2 (2001, Pentium 4)
- SSE3 (2004, Pentium 4E)
- SSSE3 (2006 ,Core Duo)
- SSE4 (2007, Core 2 Duo Penryn)
- AVX (2008, Sandy Bridge)
- TSX (2011, Haswell)

The question is first WHO perform the vectorization of the code ?
- compiler performs auto-vectorization
- you can also do it "by hand" when necessary.


## A first example with auto-vectorization

Here is a first program that you will compile :

```
#include <iostream>
using namespace std;

int main() {

	const int ArraySize = 8;
	int a[ArraySize] = {2, 2, 3, 4, 5, 5, 2, 6};
	int b[ArraySize] = {1, 3, 6, 3, 3, 1, 7, 2};
	int c[ArraySize] = {0};

	for ( unsigned int i = 0; i < ArraySize; ++i)
	{
	     c[i] = a[i] * b[i];
	}

	cout << "First value : " << c[0];
}
```

Compile this program using :

```
g++ -O3 -ftree-vectorize -ftree-vectorizer-verbose=2 -mavx -march=native -o main main.cpp
```

> Task : Why do we activate the following options : the `-mavx`, the `-ftree-vectorize`, and the `-ftree-vectorizer-verbose=2` ?

We will try to understand if this code was actually vectorized by the compiler.

> Task : Run the following command : `objdump -d main`. Do you think your programm was vectorize ? Why ?

In order to help reading Intel Vector instrinsics instructions, you can refer to [this](https://software.intel.com/sites/landingpage/IntrinsicsGuide/)

Trick: you can also insert comments in the assembler code using `asm volatile ("# my comment");`
If you insert such comment before and after the loop you want to check, it makes it easier to locate the loop in the assembler code ...
(in this case compile with `gcc -S -o loop -O3 ./loop.cpp -lstdc++`)

## Manual Mandelbrot computation

We will see here a complex example where the compiler failed to auto vectorize.

This example is directly inspired from [Intel guide](https://software.intel.com/en-us/articles/introduction-to-intel-advanced-vector-extensions), but adapted for gcc compilation.

The source code is available [as a gist](https://gist.github.com/cgravier/efc208fab365104e8224).

> Task : Compile and run. Explain the difference of order of magnitude in performance of the different versions.

## Your turn

We will know create our own implementation of an algorithm using AVX intrinsics.

The algorithm we want to implement is complex number multiplication.

Imagine (pun intended) that we have four complex numbers :
- i1 = a + bi
- i2 = c + di
- i3 = x +zi
- i4 = y + wi

For the two first complex numbers, their product is `i1*i2 = (a+bi)(c+di) = (ac -bd) + (ad + bc)i`

We want to devise a way to compute complex numbers multiplications using AVX (255 bits registers as on your 4770 processor) intrinsics.

We expected to be provided a array of complex numbers. Each complex number are represented in the array of int's using two consecutive numbers. For instance, the four aforementionned complex numbers will be represented as follows :

```
| a | b | c | d | x | z | y | w |
```

We will be working on arrays of size `1,000` complex numbers (hence a array of `2,000` numbers).

For this, we suggest the following tasks :

> Task : Provide a scalar algorithm to compute the `500` multiplications for these `1,000` complex numbers.

> Task : Devise ( = draw on a paper !) a strategy to do the same algorithm using AVX intrinsics and its registers.

You can think of the intrinsics (there is not a single solution !) starting with `_mm256_*` (those are AVX intrinsics).

> Task : Provide a benchmark in order to measure how much you win/loose for the scalar and vector versions.





