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

## Penalty from cache misses

## Prefecthing and CPU pipelines

## Cache page size and line length through padding effects

## Random read vs sequential read

## Side remarks on instructions caches
