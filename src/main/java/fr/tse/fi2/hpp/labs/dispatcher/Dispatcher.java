package fr.tse.fi2.hpp.labs.dispatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

/**
 * Dispatch receveid messages to the different queries
 * 
 * @author Julien
 * 
 */
public class Dispatcher implements Runnable {

	final static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	/**
	 * List of registered query processors
	 */
	Collection<AbstractQueryProcessor> registeredProcessors = new ArrayList<>();

	/**
	 * Files containing one event per line
	 */
	final String fileLocation;

	public Dispatcher(String fileLocation) {
		super();
		this.fileLocation = fileLocation;
	}

	/**
	 * 
	 * @param queryProcessor
	 */
	public void registerQueryProcessor(AbstractQueryProcessor queryProcessor) {
		registeredProcessors.add(queryProcessor);
	}

	/**
	 * Notify the
	 * 
	 * @param record
	 */
	private void notifyAllProcessors(DebsRecord record) {
		for (AbstractQueryProcessor processors : registeredProcessors) {
			processors.eventqueue.add(record);
		}
	}

	/**
	 * Convert a CSV line into a {@link DebsRecord}
	 * 
	 * @param line
	 * @return the {@link DebsRecord} of this line
	 */
	private DebsRecord process(String line) {
		String[] split = line.split(",");
		if (split.length != 16) {
			logger.error("Record does not match the required number of elements:\n"
					+ line);
			return null;
		}
		// Hmmm
		DebsRecord record = new DebsRecord(split[0], split[1],
				Long.valueOf(split[2]), Long.valueOf(split[3]),
				Long.valueOf(split[4]), Float.valueOf(split[5]),
				Float.valueOf(split[6]), Float.valueOf(split[7]),
				Float.valueOf(split[8]), Float.valueOf(split[9]), split[10],
				Float.valueOf(split[11]), Float.valueOf(split[12]),
				Float.valueOf(split[13]), Float.valueOf(split[14]),
				Float.valueOf(split[15]), false);
		return record;
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				fileLocation)))) {
			for (String line; (line = br.readLine()) != null;) {
				DebsRecord record = process(line);
				if (record != null) {
					notifyAllProcessors(record);
				}
			}
			// line is not visible here.
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
