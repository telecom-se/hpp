package fr.tse.fise2.info4.lab10.exercise2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO Modify this class so that:
 * <ul>
 * <li>It counts the number of words in an application wide
 * {@link AtomicInteger} shared among the {@link Consumer}</li>
 * <li>Counts the occurrence of each word using an application wide
 * {@link ConcurrentHashMap} shared among the {@link Consumer}</li>
 * </ul>
 * 
 *
 */
public class Consumer implements Runnable {

	@Override
	public void run() {

	}

	public int countWords(String sentence) {
		// TODO complete
		return -1;
	}

}
