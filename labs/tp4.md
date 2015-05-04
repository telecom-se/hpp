# TP4 : One of the keys to happiness is a bad memory !

## Remember everything

### Introduction

In this lab we are interested in querying the file `sorted_data.csv`. If not already done, you should download it into your `main/src/resources/data` folder.

The objective is to query the file in order to know if the system has reccorded at least one route that has:
1. started at a given location (`record.getPickup_longitude()` and `record.getPickup_latitude()`)
2. finish at a given location (`record.getDropoff_longitude()` and `record.getDropoff_latitude()`)
3. for a given taxi (`record.getHack_license()`)

(in this list `record` is the instance given as parameter to each call of the `protected void process(DebsRecord record)`function).

The system will be query *after* it has processed all reccords, and *we assume that we do not know until then what are the value of the route that will be looked up*.


### Work to do

1. Create a new `QueryProcessor` that will store all records (let's call it `RouteMembershipProcessor`).

2. Use this `RouteMembershipProcessor` after all records were processed in order to lookup for a route of your choice. At this step, you will most surely add a method to do so in `RouteMembershipProcessor`.

3. We know want to benchmark what is the running time for querying the `RouteMembershipProcessor`. For this, you will use the JMH framework provided with this project, and that we have discussed in TP2.
It is likely that the `@Setup` fixture will run the streaming of the records, and that the `@Benchmark` annotated method will likely be the method that actually query the `RouteMembership` query processor instance.

4. We want you to write down :
  1. the memory footprint of this query processor (hint: use `jvisualvm` that we demonstrate in TP2)
  2. the running time as reported by the JMH benchmark.
  
  
## Sometimes it is better not to remember everything ! 

### Bloom filters

1. At this point, you will be instructed on membership queries using bloom filters and couting bloom filters.

### Work to do

There a some libraries that provided bloom filters implementations.
For instance, [Google Guava](https://code.google.com/p/guava-libraries/wiki/) that is already a dependance in this project.
For the sake of learning, we will build our own bloom filter.

2. With this new knowledge, let us consider that we process the `1000Records.csv`file.
   1. What is the optimal bloom filter size (`m`in bits) for a false positive rate of `p=0.1%` ? 
   2. Given `m`, how many hash function `k` are required ?

In what follows, we will floor this value.
   
3. You are to write `k` hash function, that produces (hopefully) different values for the same input.

At this step, you are permitted to hard code the value for `k .

There are a number of hints that you should be aware when looking for a solution, and before we discuss a complete solution:
   1. We are looking hash functions of arbitrary hash size. A table [here can help](http://en.wikipedia.org/wiki/List_of_hash_functions). (Although we could have use some hack and use several a hash function that output a hash code of size lesser than `m`, we going to make it "easy" on this one)
   2. Best Java compliant Crypto API is [Bouncy Castle](https://www.bouncycastle.org), hands up ! (you may well find example on using Bouncy Castle in a lot of places on [Github](https://github.com/akoskm/bouncy-castle-sha3/blob/master/src/main/java/io/github/bouncycastlesha3/SHA3Util.java))
   3. In order to create `k`hash functions, you could create `k` different "salt" values that you prepend (or append) to the item to hash.
   
4. Add a `contain()` method that tests if a value is present in a bloom filter after the QueryProcessor has run. Evaluate its memory footprint and benchmark its running time with respect with the solution where we indexed all values in the previous exercice.

### Counting bloom filter.

At the end of this session, you will be given some hints on counting bloom filter, although we won't implement them in order to save time for other adventures!

