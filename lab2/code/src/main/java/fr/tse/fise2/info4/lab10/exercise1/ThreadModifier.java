package fr.tse.fise2.info4.lab10.exercise1;

public class ThreadModifier implements Runnable {

	private Counter counter;

	public ThreadModifier(Counter compteur) {
		this.counter = compteur;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			counter.incrementCounter();
			try {
				Thread.sleep((long) (Math.random() * 50));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}