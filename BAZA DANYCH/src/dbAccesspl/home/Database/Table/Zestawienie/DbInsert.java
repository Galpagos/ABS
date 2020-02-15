package dbAccesspl.home.Database.Table.Zestawienie;

import pl.home.Database.components.LRecordSet;

public interface DbInsert {

	public QueryBuilder set(SystemTables pmPole, Object pmObject);

	public LRecordSet execute();

	public QueryBuilder setFromGenerator(SystemTables pmPole);
}
