# Mechanical Sympathy

It is quite obvious that when the CPU execute an instruction, it does not fecth the data each time from memory - this will make it wait too many cycles to actually get the data (waiting for it) before actually processing it.

You can have the intuition that different levels of _caches_ store those data so that the CPU can have a fast access to them when required.

The real questions are :
- How many levels of caches does a CPU have ?
- How many times (number of CPU cycles) does it take to fetch a data from the memory to each of these cache ?
- How the CPU handle cache accesses while still actually executing instructions ?
- Last but not least : **Why do these low-level considerations are of the utmost practical interest for the programmer ?**

In what follows, different programming activities will make your observe and then conclude on answers to those questions.

## Who is actually executing your instructions ?

As a warm-up, we want to get an idea on the architecture of our CPU(s) that is actually handling data in our algorithms. All CPUs are not the same, right ?

Take the example of my own processor, a [Intel 5820k](http://ark.intel.com/fr/products/82932/Intel-Core-i7-5820K-Processor-15M-Cache-up-to-3_60-GHz) processor. 

As a large number of modern processors, its architecture uses three level of cache : Level 1 (L1), Level 2 (L2), and Level 3 (L3 -- sometimes: Last Level Cach (LLC)). Level 1 is within each core (whose support 2-threads multithreading) and each core has its dedicated L2 cache. L3 cache is shared accross all cores. The resulting "cache block diagram" look as follows :

todo

## Penalty from cache misses

## Prefecthing and CPU pipelines

## Cache page size and line length through padding effects

## Random read vs sequential read

## Side remarks on instructions caches