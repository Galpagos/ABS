package ProjektGlowny.commons.DbBuilder;

public interface DbInsert { // NO_UCD (use default)

	public QueryBuilder set(SystemTables pmPole, Object pmObject);

	public LRecordSet execute();

	public QueryBuilder setFromGenerator(SystemTables pmPole);
}
