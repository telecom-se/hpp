package fr.tse.fi2.hpp.labs.queries.impl;

import java.util.ArrayList;
import java.util.List;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.beans.measure.QueryProcessorMeasure;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

public class MembershipQuery extends AbstractQueryProcessor {

	private List<DebsRecord> recs = null;

	public MembershipQuery(QueryProcessorMeasure measure) {
		super(measure);
		recs = new ArrayList<>();
	}

	@Override
	protected void process(DebsRecord record) {
		recs.add(record);

	}

	public List<DebsRecord> getRecs() {
		return recs;
	}

	public void setRecs(List<DebsRecord> recs) {
		this.recs = recs;
	}

}