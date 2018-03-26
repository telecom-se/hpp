package fr.tse.fise2.info4.lab10.exercise2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestProducer {

	/**
	 * We call {@link Producer#generatedWords()} before the production has
	 * started, therefore we expect an {@link IllegalStateException}, which is
	 * unchecked
	 */
	@Test(expected = IllegalStateException.class)
	public void test() {
		// TODO modify the test to take an int and a queue in the constructor
		Producer prod = new Producer();
		prod.generatedWords();
	}

	public void testWordGeneration() {
		Producer prod = new Producer();
		prod.generateSentence(10);
		prod.generateSentence(2);
		// Assert that the number of generated words is good
		assertEquals(12, prod.generatedWords());
	}

}
