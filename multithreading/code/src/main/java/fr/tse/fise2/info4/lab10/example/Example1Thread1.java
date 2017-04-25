package fr.tse.fise2.info4.lab10.example;

public class Example1Thread1 implements Runnable {
	int number;

	public Example1Thread1(int number) {
		super();
		this.number = number;
	}

	@Override
	public void run() {
		int i = 0;
		while (i < 5) {
			System.out.println("Thread " + number);
			i++;
		}
	}

}
