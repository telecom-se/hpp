package fr.tse.fi2.hpp.labs.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;

/**
 * Default parser. Suitable for huge files
 * 
 * @author Julien
 * 
 */
public class DefaultParser implements ParserI {

	final static Logger logger = LoggerFactory.getLogger(DefaultParser.class);

	@Override
	public List<DebsRecord> parseFile(String fileLocation) {
		// Size unknown, may highly vary
		List<DebsRecord> records = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				fileLocation)))) {
			for (String line; (line = br.readLine()) != null;) {
				DebsRecord record = process(line);
				if (record != null) {
					records.add(record);
				}
			}
			// line is not visible here.
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Convert a CSV line into a {@link DebsRecord}
	 * 
	 * @param line
	 * @return the {@link DebsRecord} of this line
	 */
	private DebsRecord process(String line) {
		String[] split = line.split(",");
		if (split.length != 17) {
			logger.error("Record does not match the required number of elements:\n"
					+ line);
			return null;
		}
		return null;
	}
}
