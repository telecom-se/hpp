# Mechanical Sympathy

Disclaimer: In this part, we do not statr with programming. Instead, we first want to have a common background of understanding on how the CPU works, since single computer high performance computing is very dependent on the CPU architecture. This may surprise you to dive into CPU architecture details to begin with but rest assure we will have our share of coding later on :)


It is quite obvious that when the CPU execute an instruction, it does not fetch the data each time from memory - this will make it wait too many cycles to actually get the data (waiting for it) before actually processing it. This observation is even more true considering the _Memory Wall_.


> Task : Watch [the following video](https://www.youtube.com/watch?v=1_mjiQesgGI) to understand what is the memory wall


You can have the intuition that different levels of _caches_ store those data so that the CPU can have a fast access to them when required (aka __Memory Hierarchy__)

To further understand this, read the following :

> Task : Read [this wikipedia article](https://www.extremetech.com/extreme/188776-how-l1-and-l2-cpu-caches-work-and-why-theyre-an-essential-part-of-modern-chips)

> Taks : Read [this blog article] (https://www.extremetech.com/extreme/188776-how-l1-and-l2-cpu-caches-work-and-why-theyre-an-essential-part-of-modern-chips)

What we want to do, is to answer the following questions :
- Why do we need a cache hierarchy ? 
- How many times (number of CPU cycles) does it take to fetch a data from the memory to each of these cache ? (hint: google "every number of programmer should know")
- How many levels of caches does a CPU have ?
- What are the usual way to associate cache lines with the main memory ?
- What is the virtual memory ?
- What is a CPU stall and how the CPU handle cache accesses while still actually executing instructions?
- Last but not least : **Why do these low-level considerations are of the utmost practical interest for the programmer ?**

In what follows, different programming activities will make your observe and then conclude on answers to those questions.

## Who is actually executing your instructions?

As a warm-up, we want to get an idea on the architecture of our CPU(s) that is actually handling data in our algorithms. All CPUs are not the same, right?

> Task : What is your processor ? What is its cache hierarchy ? What are the sizes and latencies of each data storage units (cache and main memory) ?

> Task : Create a simple block diagram of your processor, including all its cores, all level of cache, main memory and disk. Add latency and size numbers.

> Reminder : Why does it matter ? (_we will discuss some numbers every programmers should know_)

> Task : Connect the dots: Watch the following awesome videos on CPU pipeline : [pipelining](https://www.youtube.com/watch?v=AgpW0SDtqC8&index=44&list=PLAwxTw4SYaPmqpjgrmf4-DGlaeV0om4iP), [Pipelining in a processor](https://www.youtube.com/watch?v=otSXgSp-8EY&index=60&list=PLAwxTw4SYaPmqpjgrmf4-DGlaeV0om4iP) - Look also at the additional quizzes on pipelining for laundry and instructions in the same videos series.

> Draw a 5-stages pipeline where to instructions are in that order : 1) load something from memory in register 1, add 1 to R1 and store result in register 2, add 1 to register 2 and store it in register 3. What is a a pipeline stall (aka bubble) ? 
The instructions sequence can be written as : 
```
LW R1, ... // load somthing (...) into R1
ADD R1, R2, 1 // add 1 to R2 and store it into R1
ADD R2, R3, 1 // add 1 to R3 and store it into R2
```

> What are modern numbers of pipeline stages ? 

## Play time !

In what follows, we will create some Java programs in order to observe the effect of the cache hierarchy on simple data structure tasks.

You are strongly encourage to first read [a very nice document from U. Drepper](https://people.freebsd.org/~lstewart/articles/cpumemory.pdf). - mostly pages 15 -- 24. Don't expect everything to make sense, but be sure to read again this passage before the "Discover JMH" section.


## Read Walks using arrays

### Utility function
In what follows, we will need to be able to build fixed-size arrays of `2^k` bytes.
For this purpose, we will be creating arrays of random `int`s, provided that your JVM stores `int`s as 32 bits (4 bytes).
When we want to build arrays of `2^k` bytes, we will be building arrays of `(2^k) / 4` random `int`'s.

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

### Sequential read walk

We want to measure the elapsed time for walking the entire arrays of the following dimensions : `k = {20 .. 32}`.

> Task : Code the sequential array traversal and measuring the elapsed time for each values of k. Plot the results and make a guess on execution time for greater values. At each operation make something of `yourArray[i]`, for instance I suggest that you compute the cumulated sum of int in the table (let's ignore `ìnt`overflows as we won't do anything with this value).

> Task : Do the same thing but traversing the array sequentially from end to start.


### Random read walk

We now want to walk `(2^k / 4`) random values from the array (maybe with repetitions). `(2^k / 4)` is the number of `int`'s in the array, so we want to fetch as many `int`from the array while preventing a sequential access. At each iteration, you will be choosing the index `i` of the array cell you want to pick from a pseudo random generator bounded in `[0; 2^k / 4 -1]`. For such generator, looking on stackoverflow can lead you to [interesting piece of code](http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range).

Or, you are more cleaver and you make an array of the first `(2^k / 4 -1 - 1` integer, shuffle that array, and use the value as the next index to fetch.

> Task : Code the random (with possible repetitions) walk of arrays with different values for k, i.e.  `k = {20 .. 32}`.

> Task : How do a sequential and a random walk compares ? What is your intuition behind this ? (next activity covers the study of your possible intuition(s).

### CPU counters using perf

> Task : Run both a sequential and random walk of the array (for instance for k = 24) and observe CPU cycles using the [perf](https://perf.wiki.kernel.org/index.php/Main_Page) Linux tool installed on your machines.
__Here we will discuss pipeline stalling (bubbles) and NUMA accesses__

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

It is possible that, although not instanciated within an aray, that the consecutive call to `new Liste` will tend to instanciate the Liste elements in a contiguous manner. But know that having a garbage collector call will rearrange those elements in an unpredictable maner, and especially less likely to be contiguous. Usually we call `System.gc()`when we want to have the garbage collector called - use it between two `new Liste()`calls to enforce a non-contiguous memory layout. This is a bit tricky because this actually means to favour a garbage collection but do not ensure it to be synchronous with the call to this function. Instead, to enforce the garbage collection when called, we will called the following function (to add in your code base) as a neat solution to perform this pause the world to garbage collection (taken from [here](http://stackoverflow.com/questions/10039474/java-guaranteed-garbage-collection-using-jlibs)) :

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

> Task : Do the same for the case when the condition is implemented as : 
```
if (data[c] >= 128)
                    sum += data[c];
```

or 
```
int t = (data[c] - 128) >> 31;
sum += ~t & data[c];
```

For those two tasks, we want to complete the following table of running time :

| Sorted (yes/no)        | Condition with bitwise (Yes/No)  | Execution time.     |
| ---------------------- |----------------------------------| --------------------|
| No 			 | No			            |   xxxx ms	          |
| Yes 			 | No			            |   xxxx ms	          |
| No 			 | Yes			            |   xxxx ms	          |
| Yes 			 | Yes			            |   xxxx ms	          |

What do yo observe and what can you assume ?

> Now we will implement the C++ version of the algorithm when the data is unsorted.
Compile it with: `g++ -o main main.cpp` and with `g++ -o main -O3 main.cpp`

Compare execution time when the tbale is sorted when you compile with or without `-O3` option.

> Task (optional) : What happen in the C++ version if the data are in a 2D array (int[][]) with the `-O3` flag ?

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

From previous activities, you may have understood that running several times the same code does not yield the same execution time. There are indeed some variations between two runs that are natural. This is however not the only reason in the variance you observe and the problem posed by `nanoTime()`.

First, we had learn that the JIT compiler made it so that it not very likely that the code you write is actually what is executed. When we are using `nanoTime()`function, we expect to measure what is between the two timestamp `start`and `end`. Actually, the JIT can go as far as deleting some part of our code that is unnecessary (see [dead code elimination ](http://www.compileroptimizations.com/category/dead_code_elimination.htm)). By comparing two pieces of code using this method, we may have surprising results just because the JIT compiler made optimisations that ruins the benchmark.

Second, `nanoTime()`is not precise enough to measure operations that run in ... nanoseconds. This method is not made to be used for benchmarking things, because (see [Nanotrusting the Nanotime](https://shipilev.net/blog/2014/nanotrusting-nanotime/))):
* It has a cost (15 to 30 ns per call)
* Its resolution is *not* nanoseconds but a 30 ns resolution.
* It's a scalability bottleneck

As a consequence, benchmarking framework exists not only to ease the development of benchmarks but also to act to fight the pitfalls of JIT optimisations and `nanoTime()`when it comes to benchmarking.

What we couldn't encompass everything at the beginig of this course, this is now the right time to get our hands on a practical and efficient benchmarking framework. In what follows, we will investigate and practice one of the most popular Java microbenchmarking framework, aka [JMH](http://openjdk.java.net/projects/code-tools/jmh/).

### Introduction
This is the main part of the work in this lab.
You are invited to first read the nice introduction from [Mikhail Vorontsov](http://java-performance.info/author/Mike/) located [here](http://java-performance.info/jmh/).
Other useful resources at [the end of this page](#references).

In this lab, we will benchmark two ways of computing the sum of a [List](http://docs.oracle.com/javase/7/docs/api/java/util/AbstractList.html) of `n` random digits.
We will actually compare naive implementations on digit lists that are implemented using for the first solution [ArrayList](http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html) and for the second [LinkedList](http://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html).

An update short introduction is available [here](http://www.baeldung.com/java-microbenchmark-harness)

### JMH example

1. Create a JMH project using the 1.8 JMH online archetype.

		  mvn archetype:generate \
          -DinteractiveMode=false \
          -DarchetypeGroupId=org.openjdk.jmh \
          -DarchetypeArtifactId=jmh-java-benchmark-archetype \
          -DgroupId=org.sample \
          -DartifactId=test \
          -Dversion=1.0

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
   1. For each benchmarked solution (with `ArrayList` and with `LinkedList`), plot in MS Excel the operations per seconds with respect to the number of item in the list. Add the linear regression serie in each graph (hint: if you do not know how to add the linear regression in an Excel graph, you can refer to [this](http://www-physique.u-strasbg.fr/~udp/articles/www-clepsydre/Excel-how-to-II.pdf)).
  2. Answer the following questions :
    1. Does the complexities of each method seems linear from an empirical point of view ?
    2. Why did you receive an out of memory exception for the solution using LinkedList for smaller values of `n` that when it starts to occur for the solution using `ArrayList` ?
    3. Why the solution using `ArrayList` seems better ?

7. Create another benchmark in order to study the impact of initializing the `ArrayList` with a default value instead of size `n` at instantiation.

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
- you can do it "by hand" when necessary.
- compiler sometimes performs auto-vectorization for you !


## Vectorization by hand


### First example

Here is a sample C++ code that we will call `copie-naive.cpp` :

```
#include <immintrin.h>
#include <iostream>

#ifdef __i386
	__inline__ uint64_t rdtsc() {
	  uint64_t x;
	  __asm__ volatile ("rdtsc" : "=A" (x));
	  return x;
}
#elif __amd64
__inline__ uint64_t rdtsc() {
	uint64_t a, d;
	__asm__ volatile ("rdtsc" : "=a" (a), "=d" (d));
	return (d<<32) | a;
}
#endif

int main() {

	float array0[ 4 ] __attribute__ ((aligned(16))) = { 0.0f, 1.0f, 2.0f, 3.0f };
	float array1[ 4 ] __attribute__ ((aligned(16)));

	uint64_t t;
	t = rdtsc();
	for (int i = 0; i < 8; i++) {
		array1[i] = array0[i];
	}
	t = rdtsc() - t;

	std::cout << t << std::endl;
}
```

> Task: What do the `#ifndef`/`#endif` code does and especially what is the `rdtsc()`function ? (I mean, search on the Web, the code won't provide the answer itself).

> Task : What does this program do ?



This code can be vectorized manually. As a first example of vectorization, here is a vectorized version `copie-simd.cpp`:

```
#include <immintrin.h>
#include <iostream>

/* define this somewhere */
#ifdef __i386
        __inline__ uint64_t rdtsc() {
          uint64_t x;
          __asm__ volatile ("rdtsc" : "=A" (x));
          return x;
}
#elif __amd64
__inline__ uint64_t rdtsc() {
        uint64_t a, d;
        __asm__ volatile ("rdtsc" : "=a" (a), "=d" (d));
        return (d<<32) | a;
}
#endif

int main() {

	// Static arrays are stored into the stack thus we need to add an alignment attribute to tell the compiler to correctly align both arrays.
	float array0[ 4 ] __attribute__ ((aligned(16))) = { 0.0f, 1.0f, 2.0f, 3.0f };
	float array1[ 4 ] __attribute__ ((aligned(16)));

	uint64_t t;
        t = rdtsc();

	// Load 4 values from the first array into a SSE register.
	__m128 r0 = _mm_load_ps(array0);

	// Store the content of the register into the second array.
	_mm_store_ps( array1 , r0 );

        t = rdtsc() - t;

        std::cout << t << std::endl;


	return 0;
```
}

> Task : What does the `_mm_load_ps`instruction means ? Can you derive what __attribute__ ((aligned(16)))` code fragment does ?
__Hint__ : Look at what does intrinsics do on the [Intel website](https://software.intel.com/en-us/node/682974)


> Task : Compile each program using : `g++ -mavx copie-naive.cpp --o naive` and `g++ -mavx copie-simd.cpp --o simd`. Is there any difference ? Can you elaborate ?
(Why the `mavx`tag by the way ?)


Compile the previous naive version with `g++ -mavx copie-naive.cpp -O3 --o naive` (wee add the `-O3` tag)

> Task : Do you note any difference in CPU ticks for executing this program ? Why ?


Note that :
1. Of course, this is magic but magic as [limits](http://www.user.tu-berlin.de/apohl/sources/wpmvp_SIMDeval.pdf), or [slide 2 here](https://www.hlrn.de/twiki/pub/NewsCenter/ParProgWorkshopFall2017/ws_openmp4_simd_programming.pdf).
2. But Mister teacher, this is really cumbersome to write such code ! You are right, indeed, you are right. That's why [this library exists](https://github.com/NumScale/boost.simd) and is popular among C++ developers !
3. Do you know [Amdahl's law for CPU](https://en.wikipedia.org/wiki/Amdahl%27s_law) ? Three is Amdahl's law for SIMD going around ([slide 1 here](https://www.hlrn.de/twiki/pub/NewsCenter/ParProgWorkshopFall2017/ws_openmp4_simd_programming.pdf))


### Example in real life

- Librairies for linear algebra computations like [Matlab](https://fr.mathworks.com/help/coder/examples/replacing-math-functions-and-operators-with-implementations-that-require-data-alignment.html) or [BLAS](http://www.netlib.org/blas/).

Such librairies are heavily used for machine learning...


- Video games (Look for instance [this presentation](https://www.gdcvault.com/play/1022248/SIMD-at-Insomniac-Games-How) from [insomniac games](https://insomniac.games/), a PlayStation 4 developper studio).
I guess such a job offers are rekevant too : [Here is one](https://www.smartrecruiters.com/Ubisoft2/92799486-engine-programmer-core-plateforme-h-f-) , and [here is another](https://www.velvetjobs.com/job-posting/core-engineer-485789).

Still not convinced ?
Watch the `How Overwatch was built to make the most of a wide range of hardware` presentation from R. Greene, developper at Blizzard, about the Overwatch game [here](https://www.twitch.tv/videos/139411097).


- Your smartphone because it is constrained device and real-time is an issue. but beware that in this case [the intrinsics are not necessarily from Intel processors...](https://developer.android.com/ndk/guides/cpu-arm-neon.html)


Actually, anything that needs high performance, that is low latency/high throughput for data processing. but the algorithm 



### Your turn

> Task : Can you create a manually vectorize program that multiplies two matrices `A` and `B` such that `A` is of size 12 x 4 and `B` is of size 4 x 12 ?
I give 2 bonus point in the HPP course for student who provide me an implementation on [mootse](https://mootse.telecom-st-etienne.fr/mod/assign/view.php?id=13598).



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

### Lectures

[GeorgiaTech High Performance Computer Architecture lectures](https://www.youtube.com/results?search_query=Georgia+Tech+-+HPCA%3A+Part+1)

