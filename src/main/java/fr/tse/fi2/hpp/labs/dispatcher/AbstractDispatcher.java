package fr.tse.fi2.hpp.labs.dispatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

public abstract class AbstractDispatcher implements Runnable {

	final static Logger logger = LoggerFactory
			.getLogger(StreamingDispatcher.class);
	/**
	 * Date parser, to convert to unix timestamp
	 */
	final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * List of registered query processors
	 */
	Collection<AbstractQueryProcessor> registeredProcessors = new ArrayList<>();

	/**
	 * Files containing one event per line
	 */
	final String fileLocation;
	/**
	 * Number of records
	 */
	protected long records = 0;

	public AbstractDispatcher(String string) {
		this.fileLocation = string;
	}

	/**
	 * Convert a CSV line into a {@link DebsRecord}
	 * 
	 * @param line
	 * @return the {@link DebsRecord} of this line
	 */
	protected DebsRecord process(String line) {
		String[] split = line.split(",");
		if (split.length != 17) {
			logger.error("Record does not match the required number of elements:\n"
					+ line + " \n" + split.length);
			return null;
		}
		// Hmmm
		DebsRecord record;
		try {
			record = new DebsRecord(split[0], split[1], dateFormat.parse(
					split[2]).getTime(), dateFormat.parse(split[3]).getTime(),
					Long.valueOf(split[4]), Float.valueOf(split[5]),
					Float.valueOf(split[6]), Float.valueOf(split[7]),
					Float.valueOf(split[8]), Float.valueOf(split[9]),
					split[10], Float.valueOf(split[11]),
					Float.valueOf(split[12]), Float.valueOf(split[13]),
					Float.valueOf(split[14]), Float.valueOf(split[15]),
					Float.valueOf(split[16]), false);
			return record;
		} catch (NumberFormatException | ParseException e) {
			logger.error("Unable to parse date for " + line);
		}
		return null;

	}

	/**
	 * Convert a CSV line into a {@link DebsRecord}
	 * 
	 * @param line
	 * @return the {@link DebsRecord} of this line
	 */
	protected DebsRecord poisonpill() {
		// Immutable is somewhat a pain here
		return new DebsRecord("", "", 0, 0, 0, 0, 0, 0, 0, 0, "", 0, 0, 0, 0,
				0, 0, true);

	}

	public void registerQueryProcessor(AbstractQueryProcessor queryProcessor) {
		registeredProcessors.add(queryProcessor);
	}

	/**
	 * Notify the
	 * 
	 * @param record
	 */
	protected void notifyAllProcessors(DebsRecord record) {
		for (AbstractQueryProcessor processors : registeredProcessors) {
			processors.eventqueue.add(record);
			if (logger.isTraceEnabled()) {
				logger.trace("Notified " + record);
			}
		}
	}

	/**
	 * 
	 * @return the number of parsed records
	 */
	public long getRecords() {
		return records;
	}
}
