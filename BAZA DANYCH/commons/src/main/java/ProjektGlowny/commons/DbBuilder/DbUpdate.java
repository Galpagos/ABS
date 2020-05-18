package ProjektGlowny.commons.DbBuilder;

public interface DbUpdate extends DbWhere { // NO_UCD (use default)

	public QueryBuilder set(SystemTables pmPole, Object pmObject);

	public LRecordSet execute();
}
