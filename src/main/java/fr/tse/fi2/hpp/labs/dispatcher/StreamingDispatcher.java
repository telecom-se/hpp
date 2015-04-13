package fr.tse.fi2.hpp.labs.dispatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;

/**
 * Dispatch receveid messages to the different queries.
 * 
 * Suitable for huge data processing. Records are immutable and are processed in
 * a streaming fashion. As soon as a record has been processed by every active
 * query, it is candidate for garbage collection.
 * 
 * @author Julien
 * 
 */
public class StreamingDispatcher extends AbstractDispatcher implements
		DispatcherI {

	public StreamingDispatcher(String fileLocation) {
		super(fileLocation);
	}

	@Override
	public void run() {
		logger.info("Starting dispatcher");
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				fileLocation)))) {
			for (String line; (line = br.readLine()) != null;) {
				DebsRecord record = process(line);
				if (logger.isTraceEnabled()) {
					logger.trace("Parsed " + record);
				}
				if (record != null) {
					records++;
					notifyAllProcessors(record);
				}
			}
			// line is not visible here.
		} catch (IOException e) {
			logger.error("Error while parsing record file", e);
			System.exit(-1);
		}
		// Send the poison pill
		logger.info("Sending poison pill");
		notifyAllProcessors(poisonpill());
	}

}
