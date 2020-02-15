package dbAccesspl.home.Database.Table.Zestawienie;

public abstract interface DbWhere {
	public QueryBuilder andWarunek(String pmWarunek);

	public QueryBuilder orWarunek(String pmWarunek);

	public QueryBuilder andWarunek(SystemTables pmIdPracownika, Object pmWartosc);

	public QueryBuilder orWarunek(SystemTables pmPole, Object pmWartosc);
}
