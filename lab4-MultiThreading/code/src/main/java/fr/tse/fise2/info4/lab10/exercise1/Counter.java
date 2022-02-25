package fr.tse.fise2.info4.lab10.exercise1;

public class Counter {

	int counter;

	public Counter() {
		this.counter = 0;
	}

	public void incrementCounter() {

		int tmp = counter;
		tmp++;
		try {
			Thread.sleep((long) (Math.random() * 50));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.counter = tmp;

	}

	public int getCounter() {
		return counter;
	}

}
