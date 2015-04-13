package fr.tse.fi2.hpp.labs.dispatcher;

import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

public interface DispatcherI {

	/**
	 * 
	 * @param queryProcessor
	 */
	public abstract void registerQueryProcessor(
			AbstractQueryProcessor queryProcessor);

	public abstract long getRecords();

	public abstract void run();

}