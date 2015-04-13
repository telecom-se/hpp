package fr.tse.fi2.hpp.labs.dispatcher;

import java.util.ArrayList;
import java.util.Collection;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

/**
 * Dispatch receveid messages to the different queries
 * 
 * @author Julien
 * 
 */
public class Dispatcher {

	Collection<AbstractQueryProcessor> registeredProcessors = new ArrayList<>();

	/**
	 * 
	 * @param queryProcessor
	 */
	public void registerQueryProcessor(AbstractQueryProcessor queryProcessor) {
		registeredProcessors.add(queryProcessor);
	}

	public void start(){
		
	}
	/**
	 * Notify the
	 * 
	 * @param record
	 */
	private void notifyAllProcessors(DebsRecord record) {
		for (AbstractQueryProcessor processors : registeredProcessors) {
			processors.onReceiveMessage(record);
		}
	}

}
