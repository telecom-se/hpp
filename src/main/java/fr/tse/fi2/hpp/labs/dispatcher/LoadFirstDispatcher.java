package fr.tse.fi2.hpp.labs.dispatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;

/**
 * Dispatcher that first loads the data, then send to the query processors.
 * Useful to avoid counting parsing time.
 * 
 * @author Julien
 * 
 */
public class LoadFirstDispatcher extends AbstractDispatcher implements
		DispatcherI {

	List<DebsRecord> recordsList = new LinkedList<>();

	public LoadFirstDispatcher(String string) {
		super(string);
		load();
	}

	@Override
	public void run() {
		for (DebsRecord record : recordsList) {
			notifyAllProcessors(record);
		}
		// Poison pill
		notifyAllProcessors(poisonpill());
	}

	private void load() {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				fileLocation)))) {
			for (String line; (line = br.readLine()) != null;) {
				DebsRecord record = process(line);
				if (record != null) {
					records++;
					recordsList.add(record);
				}
			}
			// line is not visible here.
		} catch (IOException e) {
			logger.error("Error while parsing record file", e);
			System.exit(-1);
		}

	}

}
