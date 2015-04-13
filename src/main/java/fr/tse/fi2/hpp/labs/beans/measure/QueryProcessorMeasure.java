package fr.tse.fi2.hpp.labs.beans.measure;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

/**
 * Store the time required for each {@link AbstractQueryProcessor} to process
 * the full stream of events
 * 
 * @author Julien
 * 
 */
public class QueryProcessorMeasure {

	final static Logger logger = LoggerFactory
			.getLogger(QueryProcessorMeasure.class);
	/**
	 * Program starts
	 */
	long startTime;
	/**
	 * Program finishes
	 */
	long endTime;
	/**
	 * Maps PID <-> start time
	 */
	private final ConcurrentHashMap<Integer, Long> timePerProcessorStart;
	/**
	 * Maps PID <-> finish time
	 */
	private final ConcurrentHashMap<Integer, Long> timePerProcessorFinish;
	/**
	 * Number of records processed
	 */
	private long records;

	public QueryProcessorMeasure() {
		timePerProcessorStart = new ConcurrentHashMap<>();
		timePerProcessorFinish = new ConcurrentHashMap<>();
		startTime = System.nanoTime();
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

	public void setProcessedRecords(long records) {
		this.records = records;
	}

	/**
	 * Writes the results into a file in the result/ directory
	 */
	public void outputMeasure() {
		endTime = System.nanoTime();
		Set<Integer> pids = timePerProcessorStart.keySet();
		StringBuffer sbTime = new StringBuffer();
		sbTime.append("Global execution time "
				+ ((endTime - startTime) / 1_000_000) + "ms\n");
		for (Integer pid : pids) {
			long nanoDiff = timePerProcessorFinish.get(pid)
					- timePerProcessorStart.get(pid);
			long msDiff = nanoDiff / 1_000_000;
			sbTime.append("Query " + pid + " runtime: " + msDiff + "ms\n");
			long throughput = (records * 1_000_000) / nanoDiff;
			sbTime.append("Query " + pid + " throughput: " + throughput
					+ " events/second\n");
		}
		try {
			FileUtils.writeStringToFile(new File("result/result.txt"),
					sbTime.toString());
		} catch (IOException e) {
			logger.error("Error while saving running time stats", e);
		}
	}

}
