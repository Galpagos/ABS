package dbAccesspl.home.Database.Table.Zestawienie;

import pl.home.Database.components.LRecordSet;

public interface DbSelect extends DbWhere {
	public QueryBuilder select(SystemTables pmTable, SystemTables... pmArgs);

	public QueryBuilder orderBy(SystemTables pmPole, boolean czyRosnaco);

	public LRecordSet execute();

	public QueryBuilder joinOn(SystemTables pmWith, SystemTables pmOn);

	public QueryBuilder andBeforeOrEqual(SystemTables pmIdPole, Object pmWartosc);

	public QueryBuilder andAfterOrEqual(SystemTables pmPole, Object pmWartosc);
}
