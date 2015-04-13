package fr.tse.fi2.hpp.labs.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import fr.tse.fi2.hpp.labs.beans.measure.QueryProcessorMeasure;
import fr.tse.fi2.hpp.labs.dispatcher.Dispatcher;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

/**
 * Main class of the program. Register your new queries here
 * 
 * Design choice: no thread pool to show the students explicit
 * {@link CountDownLatch} based synchronization.
 * 
 * @author Julien
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Init dispatcher
		Dispatcher dispatch = new Dispatcher(
				"src/main/resources/data/1000Records.csv");
		// Init query time measure
		QueryProcessorMeasure measure = new QueryProcessorMeasure();
		// Countdownlatch for synchronisation
		CountDownLatch latch;
		// Query processors
		List<AbstractQueryProcessor> processors = new ArrayList<>();
		// Add you query processor here
		
		// Register query processors
		for (AbstractQueryProcessor queryProcessor : processors) {
			dispatch.registerQueryProcessor(queryProcessor);
		}

		// Initialize the latch with the number of query processors
		latch = new CountDownLatch(processors.size());
		// Start everything
		dispatch.run();
		// Wait for the latch
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
