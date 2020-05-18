package ProjektGlowny.commons.DbBuilder;

public interface DbSelect extends DbWhere {
	public QueryBuilder select(SystemTables pmTable, SystemTables... pmArgs);

	public QueryBuilder orderBy(SystemTables pmPole, boolean czyRosnaco);

	public LRecordSet execute();

	public QueryBuilder joinOn(SystemTables pmWith, SystemTables pmOn);

	public QueryBuilder top(int pmTop);
}
