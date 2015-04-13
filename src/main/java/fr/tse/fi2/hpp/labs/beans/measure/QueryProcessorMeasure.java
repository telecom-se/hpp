package fr.tse.fi2.hpp.labs.beans.measure;

import java.util.concurrent.ConcurrentHashMap;

import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

/**
 * Store the time required for each {@link AbstractQueryProcessor} to process
 * the full stream of events
 * 
 * @author Julien
 * 
 */
public class QueryProcessorMeasure {

	private final ConcurrentHashMap<Integer, Long> timePerProcessorStart;
	private final ConcurrentHashMap<Integer, Long> timePerProcessorFinish;

	public QueryProcessorMeasure() {
		timePerProcessorStart = new ConcurrentHashMap<>();
		timePerProcessorFinish = new ConcurrentHashMap<>();
	}

	/**
	 * 
	 * @param processorId
	 *            id of the {@link AbstractQueryProcessor} that just started
	 */
	public void notifyStart(int processorId) {
		timePerProcessorStart.put(processorId, System.nanoTime());
	}

	/**
	 * 
	 * @param processorId
	 *            id of the {@link AbstractQueryProcessor} that just started
	 */
	public void notifyFinish(int processorId) {
		timePerProcessorFinish.put(processorId, System.nanoTime());
	}

}
