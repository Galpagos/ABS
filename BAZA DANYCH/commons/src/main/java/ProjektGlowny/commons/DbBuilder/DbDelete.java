package ProjektGlowny.commons.DbBuilder;

public interface DbDelete extends DbWhere { // NO_UCD (use default)
	public QueryBuilder delete(ISystemTableNames pmTabela);

}
