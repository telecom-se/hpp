package fr.tse.fise2.info4.lab10.exercise1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class Exercise1Test {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//Run the test multiple times, to reduce chances of 'lucky strike'
		for (int i = 0; i < 10; i++) {
			Counter counter = new Counter();
			Exercise1.incrementCounterWithThreads(counter);
			int expected = 100;
			assertEquals(expected, counter.getCounter());
		}

	}

}
