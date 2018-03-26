package fr.tse.fise2.info4.lab10.exercise1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Exercise 1 : Synchronize a counter
 * 
 * in this exercise, the {@link Counter} is not properly synchronized. Ten
 * threads are trying to increment i ten times, therefore the expected results
 * is 100.
 * 
 * Dans cet exercice, le compteur (Class compteur) n'est pas synchronis�
 * correctement. 10 threads tentent de l'incr�menter 10 fois chacun, le r�sultat
 * attendu est donc 100.
 * 
 * Cependant si vous ex�cutez ce programme vous n'obtiendrez pas ce r�sultat.
 * Vous allez synchroniser ce programme de 3 mani�res diff�rentes
 * <ol>
 * <li>Utiliser le mot cl� synchronized pour r�soudre le probl�me</li>
 * <li>Dans la classe compteur, utiliser des locks pour synchroniser</li>
 * <li>Rechercher dans le package java.util.concurrent.atomic une classe
 * permettant de r�aliser la synchonisation</li>
 * </ol>
 * 
 * @author Julien Subercaze
 * 
 */
public class Exercise1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Use a threadpool to manage multiple threads. Read doc for more
		Counter counter = new Counter();
		incrementCounterWithThreads(counter);
		System.out.println("Counter value:" + counter.getCounter());
	}

	public static void incrementCounterWithThreads(Counter counter){
		ExecutorService service = Executors.newFixedThreadPool(10);
		// Start 10 threads that increments the counter
		for (int i = 0; i < 10; i++) {
			service.execute(new ThreadModifier(counter));
		}
		//Finish
		shutdownAndAwaitTermination(service);
	}
	
	/**
	 * Good practice: code imported from Oracle example
	 * 
	 * @param pool
	 */
	static void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

}
