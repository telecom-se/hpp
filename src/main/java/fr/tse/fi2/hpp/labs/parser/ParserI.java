package fr.tse.fi2.hpp.labs.parser;

import java.util.List;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;

/**
 * Interface for Parsers
 * 
 * @author Julien
 *
 */
public interface ParserI {
	/**
	 * 
	 * @param fileLocation Location on Disk
	 * @return the list of {@link DebsRecord} in the file.
	 */
	public List<DebsRecord> parseFile(String fileLocation);
	
}
