package fr.tse.fise2.info4.lab10.example;

public class Exemple1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Example1Thread1 thread1 = new Example1Thread1(1);
		Example1Thread1 thread2 = new Example1Thread1(2);
		new Thread(thread1).start();
		new Thread(thread2).start();

	}

}
