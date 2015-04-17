# TP2 : Don't predict, measure !

## Discover JMH

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
    3. Why the solution using `ArrayList` is far better ?

7. Create another benchmark in order to study the impact of initializing the `ArrayList` with a default value instead of size `n` at instanciation.


## Work to do 2/2: JMH et le projet DEBS du module

You are to use JMH in the main lab project provided to you at TP1.

The lab project provided to you already includes JMH dependency, as it can be tricky to do without using the archetype -- we did it for you !

You are to benchmark using JMH the queries you made at lab #1.

For this, consider :
1. How to modify the program to make use of JMH ?
2. What do we need to measure ?
3. Implement your solution.

<a name="references"></a>
### JMH References
- http://www.oracle.com/technetwork/articles/java/architect-benchmarking-2266277.html
- http://nitschinger.at/Using-JMH-for-Java-Microbenchmarking
- http://java-performance.info/jmh/
- http://openjdk.java.net/projects/code-tools/jmh/
- http://shipilev.net/talks/devoxx-Nov2013-benchmarking.pdf
