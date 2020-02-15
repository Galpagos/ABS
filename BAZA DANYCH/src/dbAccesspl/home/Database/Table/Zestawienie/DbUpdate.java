package dbAccesspl.home.Database.Table.Zestawienie;

import pl.home.Database.components.LRecordSet;

public interface DbUpdate extends DbWhere {

	public QueryBuilder set(SystemTables pmPole, Object pmObject);

	public LRecordSet execute();
}
