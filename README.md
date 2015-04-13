# High Performance Programming Labs

You find here all the necessary materials for the labs of the High Performance Programming Course.

For each session of the course, a notion will be introduced (Data Structure, Algorithms, Archictecture) and will be applied in the following lab.

The general framework of the lab is a maven project that process data from the [DEBS 2015 Grand Challenge](http://www.debs2015.org/call-grand-challenge.html). This challenge contains data from taxi trips in NYC.

You will be asked to answer queries on the data. Each query will reflect the notions seen during the course.
The goal being to answer these queries as fast as possible.

## Installation

First of all, fork this project into your own account: click on the Fork icon on this page.
Clone the forked project on your computer. Import the project in Eclipse via Import->Maven Project.

### Running the system

Two main classes are at your disposition, the first one , [`MainNoNStreaming`](https://github.com/telecom-se/hpp/blob/master/src/main/java/fr/tse/fi2/hpp/labs/main/MainNonStreaming.java) first loads all data in memory then sends the data to each query processor. The second one, [`MainStreaming`](https://github.com/telecom-se/hpp/blob/master/src/main/java/fr/tse/fi2/hpp/labs/main/MainStreaming.java)streams the data to the query processors.

### Additional data

The repository contains a small data file with 1000 records. This file is sufficient for test purpose but is too limited for large scale processing. You need to download the 2 millions records file from [here](https://drive.google.com/file/d/0B0TBL8JNn3JgTGNJTEJaQmFMbk0/view?usp=sharing) (130Mb). Unzip it in `src/main/resources`.


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
### Register your query processor

To be executed, an instance query processor must be registered in one (or both) main classes. Edit the files to add your own query processor:

		List<AbstractQueryProcessor> processors = new ArrayList<>();
		// Add you query processor here
		processors.add(new SimpleQuerySumEvent(measure));

### Write Output

To add a result to the output file simply use the `writeLine(String line)` method. It will automatically append a line in the `results/queryN.txt` file, where `N` is the identifier of your query processor (automatically generated).

## Performance measure

The framework includes a basic measurement system. Global execution time, per query execution time and throughput are automatically written in `results/result.txt`.

For some labs, specific instructions will be given to produce measure with [JMH](http://openjdk.java.net/projects/code-tools/jmh/).


## Queries per Session


* Lab 1: Discovery of the material.

