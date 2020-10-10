package ProjektGlowny.commons.DbBuilder;

import java.time.LocalDate;

abstract interface DbWhere {
	public QueryBuilder andWarunek(String pmWarunek);

	public QueryBuilder orWarunek(String pmWarunek);

	public QueryBuilder andWarunek(SystemTables pmIdPracownika, Object pmWartosc);

	public QueryBuilder orWarunek(SystemTables pmPole, Object pmWartosc);

	public QueryBuilder andBeforeOrEqual(SystemTables pmIdPole, LocalDate pmWartosc);

	public QueryBuilder andAfterOrEqual(SystemTables pmPole, LocalDate pmWartosc);

	public QueryBuilder andEqual(SystemTables pmPole, LocalDate pmWartosc);
}
