package fr.tse.fi2.hpp.labs.queries.impl;

import java.util.ArrayList;
import java.util.List;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.beans.measure.QueryProcessorMeasure;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

public class NaiveAverage extends AbstractQueryProcessor {

	private List<Float> numbers = null;

	public NaiveAverage(QueryProcessorMeasure measure) {
		super(measure);
		numbers = new ArrayList<>();
	}

	@Override
	protected void process(DebsRecord record) {
		numbers.add(record.getFare_amount());

		float sum = 0f;
		for (Float f : numbers) {
			sum += f;
		}
		writeLine("current mean : " + (sum / numbers.size()));
	}

}