# Mechanical Sympathy

It is quite obvious that when the CPU execute an instruction, it does not fetch the data each time from memory - this will make it wait too many cycles to actually get the data (waiting for it) before actually processing it. This observation is even more true considering the _Memory Wall_.

You can have the intuition that different levels of _caches_ store those data so that the CPU can have a fast access to them when required.

The real questions are :
- How many levels of caches does a CPU have ?
- How many times (number of CPU cycles) does it take to fetch a data from the memory to each of these cache ?
- How the CPU handle cache accesses while still actually executing instructions?
- Last but not least : **Why do these low-level considerations are of the utmost practical interest for the programmer ?**

In what follows, different programming activities will make your observe and then conclude on answers to those questions.

## Who is actually executing your instructions?

As a warm-up, we want to get an idea on the architecture of our CPU(s) that is actually handling data in our algorithms. All CPUs are not the same, right?

Take the example of a "old" [Intel i5 760](http://ark.intel.com/fr/products/48496/Intel-Core-i5-760-Processor-8M-Cache-2_80-GHz) processor. It is based on a Nehalem microarchitecture, and a block schema for this architecture is provided by Texa A&M University:

![](http://sc.tamu.edu/Images/NehalemMemBlock.PNG)

As a large number of modern processors, its architecture uses three level of cache : Level 1 (L1), Level 2 (L2), and Level 3 (L3 -- sometimes: Last Level Cache (LLC)). This is a very common cache hierarchy architecture.

> Task : What is your processor ? What is its cache hierarchy ? What are the sizes and latencies of each data storage units (cache and main memory) ?

> Task : Create a simple block diagram of your processor, including all its cores, all level of cache, main memory and disk. Add latency and size numbers. Optionally, include TLB information if you happen to have read on it and get the idea. Students will present their perception of your processor, we will discuss it and I will draw my own schema.

> Task : Why does it matter ? (_we will discuss some numbers every programmers should know_)

## Play time !

In what follows, we will create some Java programs in order to observe the effect of the cache hierarchy on simple data structure tasks.

You are strongly encourage to first read [a very nice document from U. Drepper](https://people.freebsd.org/~lstewart/articles/cpumemory.pdf)).


## Read Walks using arrays

### Utility function
In what follows, we will need to be able to build fxed-size arrays of `2^k` bytes.
For this purpose, we will be creating arrays of random `int`s, provided that your JVM stores `int`s as 32 bits (4 bytes).
When we want to build arrays of `2^k` bytes, we will be building arrays of `(2^k) / 4` random `ìnt`'s.

> Task : Write a Java class that expose a single static function to create such an array, whose size is given as parameter. That is write `public static int[] makeArray(int k)`.

### On measuring execution time

We want to measure the time needed to walk the entire array from the beginning to its end, in a sequential way. In order to measure the time spent, we will be using two methods :

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

We want to measure the elapsed time for walking the entire arrays of the following dimensions : `k = {20 .. 32}`.

> Task : Code the sequential array traversal and measuring the elapsed time for each values of k. Plot the results and make a guess on execution time for greater values. At each operation make something of `yourArray[i]`, for instance I suggest that you compute the cumulated sum of int in the table (let's ignore `ìnt`overflows as we won't do anything with this value).

> Task : Do the same thing but traversing the array sequentially from end to start.


### random read walk

We now want to walk `(2^k / 4`) random values from the array (maybe with repetitions). `(2^k / 4)` is the number of `int`'s in the array, so we want to fetch as many `int`from the array while preventing a sequential access. At each iteration, you will be choosing the index `i` of the array cell you want to pick from a pseudo random generator bounded in `[0; 2^k / 4 -1])`. For such generator, looking on stackoverflow can lead you to [interesting piece of code](http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range).

> Task : Code the random (with possible repetitions) walk of arrays with different values for k, i.e.  `k = {20 .. 32}`.

> Task : How do a sequential and a random walk compares ? What is your intuition behind this ? (next activity covers the study of your possible intuition(s).

### CPU counters using perf

> Task : Run both a sequential and random walk of the array (for instance for k = 24) and observe CPU cycles using the [perf](https://perf.wiki.kernel.org/index.php/Main_Page) Linux tool installed on your machines.



## Read Walks using linked list

In this part we will be interested in traversing a linked list in a sequential manner for varying cell size in bytes. We will study the impact of traversing a linkedlist that lies in a contiguous memory space with respect to a a linkedlist that lies in a noncontiguous space. Remember that traversing a linkedlist is not prefetched by current CPUs -- although you can see [some proposal in the academic literature](Dependence Based Prefetching for Linked Data Structures).

### Our linkedlist

We will be using a simple LinkedList that we provide below :

	class Liste {
		int[] array = { 1, 2, 3, 4 };
		Liste next;
	}

Each cell holds a 4-`int` 's array along the pointer to the next cell. This is the pointer we will be using to travers the linkedlist sequentially.

### Linkedlist in a contiguous memory space

> Task : Provide a method to build a `Liste`of `2^k`bytes. (provided each cell contains 5 `ìnt`s, that is 4 `int`'s for the array and one as the pointer to the next cell), the number of cells is `2^k / 4 / 5` (derived from previous tasks).
In order to realize this task, you have to make sure that all cells lies in a contiguous memory space. To perform this, it is possible to create an array of `size`Liste elements (the elements will be contiguous as property of an array). Then you can wire each cells to the next one in the array so that you can later measure the traversal of the linkedlist using its pointers `next`, while being sure that all elements are contiguous.

> Task : Observe the execution time for `k = {20 .. 32}`


### LinkedList in a non-contiguous space

> Task : Provide another method to build a `Liste` so that you are no longer guarantee that the `liste`elements lie in a contiguous memory space. 

It is possible that, although not instanciated within an aray, that the consecutive call to `new Liste` will tend to instanciate the Liste elements in a contiguous manner. But know that having a garbage collector call will rearrange those elements in an unpredictable maner, and especially less likely to be contiguous. Usually we call `System.gc()`when we want to have the garbage collector called. This is a bit tricky because this actually means to favour a garbage collection but do not ensure it to be synchronous with the call to this function. Instead, to enforce the garbage collection when called, we will called the following function (to add in your code base) as a neat solution to perform this pause the world to garbage collection (taken from [here](http://stackoverflow.com/questions/10039474/java-guaranteed-garbage-collection-using-jlibs)) :

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


## Branch predictions and compiler optimizations.

I first want you to read about branch prediction from this [legendary stackoverflow answer](http://stackoverflow.com/questions/11227809/why-is-it-faster-to-process-a-sorted-array-than-an-unsorted-array).

Once you get through that answer, you should be eager to perform the following task :

> Task : Implement the test from the stackoverflow answer in order to observe branch mispredictions penalties when data are unsorted.

What do yo observe and what can you assume ?

> Task : We will test further this assumption by making the same test as above but when data are in a 2D array (int[][]) and see if this affects the behavior of the performance penalty.

## On optimization automation

Until now, what we have discovered suggest that the developer must be aware on how he/she store and traverse data, which implies a careful trade-off between data layout and algorithms being used.

This trade-off require important knowledge and thorough benchmarking to get all the juice out of it.
Thankfully, part part of these CPU and cache-aware optimization techniques can be automatically optimize, and actually are without you to know until now. You will come to understand in this activity that the code that is actually run as assembly (in C/C++ or Java) is never the code that you actually wrote in your high lelve language (C/C++ or Java). Quite disturbing, isn't it ? :-)

> Task : You are invited to look at the following automatic optimization techniques (what are they ?) : 
- Branch prediction (you should now be familiar with this one)
- Null Check Elimination
- Loop unrolling
- Inlining methods
- Thread Local Storage
- Dead code elimination

[A good starting point](http://blog.takipi.com/java-on-steroids-5-super-useful-jit-optimization-techniques/).

Can you see some similitudes for instructions from what we have observed on data layout in the array and linkedlist use case, speaking of CPU and cache friendliness ?

> Task : *When* does these optimizations appear in the interpretation/compilation/runtime for C/C++ on one hand, and Java on the other ?
Hint: it is significantly different for C/C++ and Java.




# Discover JMH : Don't predict, measure !

### Introduction
This is the main part of the work in this lab.
You are invited to first read the nice introduction from [Mikhail Vorontsov](http://java-performance.info/author/Mike/) located [here](http://java-performance.info/jmh/).
Other useful resources at [the end of this page](#references).

In this lab, we will benchmark two ways of computing the sum of a [List](http://docs.oracle.com/javase/7/docs/api/java/util/AbstractList.html) of `n` random digits.
We will actually compare naive implementations on digit lists that are implemented using for the first solution [ArrayList](http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html) and for the second [LinkedList](http://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html).


### Work to do 1/2 : Use JMH in a stand alone project

1. Create a JMH project using the 1.8 JMH online archetype.

2. Annotate the generated `MyBenchmark` class generated for your benchmarks :
  1. Add the `Scope` to `MyBenchmark`class.
  2. Create a private member of type `List<Integer` and initialize it to null;
  3. Create a parameter in the benchmark for making the size of the list on which we want to compute the mean vary. For this, create a private member of type `ìnt` called `n`. Annotate the member so that it can take values in the following set : 1000, 10000, 100000, 1000000, 10000000.

2. Initialize the List at each benchmark iteration using a JMH setup fixture (`@Setup` annotation). This method initializes the list with `n` random digits (hint: use [Random](http://docs.oracle.com/javase/7/docs/api/java/util/Random.html)). At this step, choose an `ArrayList` for the list implementation, and set the instance to host `n`values from the start ([a constructor with an `int` exists](http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html#ArrayList(int))).

3. Implement the business logic in the benchmark method. Dont forget to return the computed mean: not that it will be used but it is a good practise in benchmarking in order to defend against Dead Code elimination.

4. Run the benchmark and measure
  1. using `mvn clean install && java -jar target/benchamrks.jar` (or any alternative you may prefer).
  Hint: for debugging, you can limit the number of warmup and measurement iterations using `@Measurement` and `@Warmup` annotations. You can also limit the number of forked benchmark using `@Fork`. When everything is OK, dont forget to roll back to default values that are chosen for providing sufficiently relevant figures.
  2. Note the running time, and the operations per seconds (ops/s) that your method achieves.

5. Add another benchmark in your project that will do just the same operation but using a `LinkedList` implementation for the List on which we want to compute the mean.

6. Analyze
   1. For each benchmarked solution (with `ArrayList` and with `LinkedList`), plot in MS Excel the operations per seconds with respect to the number of item in the list. Add the linear regression serie in each graph (hint: if you do not know how to add the linear regression in an Excel gaph, you can refer to [this](http://www-physique.u-strasbg.fr/~udp/articles/www-clepsydre/Excel-how-to-II.pdf)).
  2. Answer the following questions :
    1. Does the complexities of each method seems linear from an empirical point of view ?
    2. Why did you receive an out of memory exception for the solution using LinkedList for smaller values of `n` that when it starts to occur for the solution using `ArrayList` ?
    3. Why the solution using `ArrayList` seems better ?

7. Create another benchmark in order to study the impact of initializing the `ArrayList` with a default value instead of size `n` at instanciation.

# SIMD short intro (if time allows)

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

In order to help reading Intel Vector intrinsic instructions, you can refer to [this](https://software.intel.com/sites/landingpage/IntrinsicsGuide/)

Trick: you can also insert comments in the assembler code using `asm volatile ("# my comment");`
If you insert such comment before and after the loop you want to check, it makes it easier to locate the loop in the assembler code ...
(in this case compile with `gcc -S -o loop -O3 ./loop.cpp -lstdc++`)

## Manual Mandelbrot computation

We will see here a complex example where the compiler failed to auto vectorize.

This example is directly inspired from [Intel guide](https://software.intel.com/en-us/articles/introduction-to-intel-advanced-vector-extensions), but adapted for gcc compilation.

The source code is available [as a gist](https://gist.github.com/cgravier/efc208fab365104e8224).

> Task : Compile and run. Explain the difference of order of magnitude in performance of the different versions.




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

[Branch prediction : performance of some mergesort implementations](http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-172-performance-engineering-of-software-systems-fall-2010/video-lectures/lecture-5-performance-engineering-with-profiling-tools/MIT6_172F10_lec05.pdf)

