# High Performance Programming Labs

You find here all the necessary materials for the labs of the High Performance Programming Course.

For each session of the course, a notion will be introduced (Data Structure, Algorithms, Archictecture) and will be applied in the following lab.

The general framework of the lab is a maven project that process data from the [DEBS 2015 Grand Challenge](http://www.debs2015.org/call-grand-challenge.html). This challenge contains data from taxi trips in NYC.

The framework reads trips from the file and sends them to the different registered query processors. Query processors process the data to produce 

You will be asked to answer queries on the data. Each query will reflect the notions seen during the course.
The goal being to answer these queries as fast as possible.

## Installation

First of all, fork this project into your own account: click on the Fork icon on this page.
Clone the forked project on your computer. Import the project in Eclipse via Import->Maven Project.

### Running the system

Two main classes are at your disposition, the first one , [`MainNoNStreaming`](https://github.com/telecom-se/hpp/blob/master/src/main/java/fr/tse/fi2/hpp/labs/main/MainNonStreaming.java) first loads all data in memory then sends the data to each query processor. The second one, [`MainStreaming`](https://github.com/telecom-se/hpp/blob/master/src/main/java/fr/tse/fi2/hpp/labs/main/MainStreaming.java)streams the data to the query processors.

### Additional data

The repository contains a small data file with 1000 records. This file is sufficient for test purpose but is too limited for large scale processing. 

You will need to download the different files from [here](http://datasets-satin.telecom-st-etienne.fr/jsubercaze/hpp). Unzip them in `src/main/resources/data`. 


## Create a query processor

To create a new query processor, create a new class in the package [`fr.tse.fi2.hpp.labs.queries.impl`](https://github.com/telecom-se/hpp/tree/master/src/main/java/fr/tse/fi2/hpp/labs/queries/impl). Your class must extend [`AbstractQueryProcessor`](https://github.com/telecom-se/hpp/blob/master/src/main/java/fr/tse/fi2/hpp/labs/queries/AbstractQueryProcessor.java).

An exemple of an empty class:
  
    public class SampleQueryProcessor extends AbstractQueryProcessor{
  
  	 public SampleQueryProcessor(QueryProcessorMeasure measure) {
		 super(measure);
	 }

	 @Override
	 protected void process(DebsRecord record) {
    	// Process the record
	 }
    
    }
    
You must complete the `process` method to implement the queries. This method is called for each [DebsRecord](https://github.com/telecom-se/hpp/blob/master/src/main/java/fr/tse/fi2/hpp/labs/beans/DebsRecord.java) that is sent by the framework. A `DebsRecord` contains information for one taxi trip: coordinates for pickup and dropoff, price paid, tip, ... The full list is available in the file as well as [here](http://www.debs2015.org/call-grand-challenge.html) (Data Section).


### Register your query processor

To be executed, your query processor must be registered in one (or both) main classes. Edit the files to add your own query processor:

		List<AbstractQueryProcessor> processors = new ArrayList<>();
		// Add you query processor here
		processors.add(new SimpleQuerySumEvent(measure));

### Write Output

To add a result to the output file simply use the `writeLine(String line)` method. It will automatically append a line in the `results/queryN.txt` file, where `N` is the identifier of your query processor (automatically generated).

## Performance measure

The framework includes a basic measurement system. Global execution time, per query execution time and throughput are automatically written in `results/result.txt`.

For some labs, specific instructions will be given to produce measure with [JMH](http://openjdk.java.net/projects/code-tools/jmh/).


## Queries per Session


#### Lab 1: Discovery. 

Follow the installation instruction. Verify that everything is ok with a `mvn install`. Install the extra data in your project. Modify the main classes to parse the `100k.csv` file.

Remove the existing query that counts the events.

To compare performance for two implementations of the same feature, create the following queries:
* `StupidAveragePrice` that puts every new trip price into a list and compute the average based on every number in the list
* `IncrementalAveragePrice` that uses the previous results to incrementally compute the average.

Execute both queries and measure the difference of running time and throughput, for both streaming and non streaming case.

#### Lab 2: JMH.

TBD

#### Lab 3: Fixing I/O bound

Using JMH, seen at the previous lab, measure the performance of the `IncrementalAveragePrice` with and without `writeLine`.
As you should see, the I/O is limiting the performance of your program. Use a thread to externalize the output of the query processor. Modify [AbstractQueryProcessor](https://github.com/telecom-se/hpp/blob/master/src/main/java/fr/tse/fi2/hpp/labs/queries/AbstractQueryProcessor.java) to manage the thread creation and management.
Measure the performance against the previous version.

## Evaluation

Evaluation will be made based on the code available on your forked version of this project. No additional material will be accepted.
