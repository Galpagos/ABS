package dbAccesspl.home.Database.Table.Zestawienie;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import Parsery.ParseryDB;
import pl.home.Database.components.AccessDB;
import pl.home.Database.components.LRecordSet;

public class QueryBuilder extends AccessDB implements DbUpdate, DbSelect, DbDelete, DbInsert, DbCount {
	private QueryBuilder(TypZapytania pmTyp) {
		mTyp = pmTyp;
	}

	private StringBuilder mQuery = new StringBuilder();
	private StringBuilder mWarunek = new StringBuilder().append(" WHERE 1=1 ");
	private StringBuilder mOrderBy = new StringBuilder();
	private Integer mTop = null;

	private TypZapytania mTyp = null;
	private String mTabela;
	private Map<SystemTables, String> mPola = new LinkedHashMap<>();

	public static DbSelect SELECT() {
		return new QueryBuilder(TypZapytania.SELECT);
	}

	public static DbUpdate UPDATE() {
		return new QueryBuilder(TypZapytania.UPDATE);
	}

	public static DbDelete DELETE() {
		return new QueryBuilder(TypZapytania.DELETE);
	}

	public static DbInsert INSERT() {
		return new QueryBuilder(TypZapytania.INSERT);
	}

	public static DbCount COUNT() {
		return new QueryBuilder(TypZapytania.COUNT);
	}

	@Override
	public QueryBuilder set(SystemTables pmPole, Object pmObject) {
		mTabela = pmPole.getTableName();
		mPola.put(pmPole, valueToString(pmPole, pmObject));
		mQuery.append(" " + pmPole.toString() + "=" + valueToString(pmPole, pmObject) + ",");
		return this;
	}

	@Override
	public QueryBuilder setFromGenerator(SystemTables pmPole) {
		return set(pmPole, getNextID(pmPole));
	}

	@Override
	public QueryBuilder select(SystemTables pmTable, SystemTables... pmArgs) {
		mTabela = pmTable.getTableName();
		mQuery.append(mTabela + "." + pmTable);

		if (pmArgs != null && pmArgs.length > 0)
			for (int i = 0; i < pmArgs.length; i++)
				mQuery.append("," + pmArgs[i].getTableName() + "." + pmArgs[i]);
		mQuery.append(" FROM " + mTabela);
		return this;
	}

	@Override
	public QueryBuilder andWarunek(String pmWarunek) {
		mWarunek.append(" AND " + pmWarunek);
		return this;
	}

	@Override
	public QueryBuilder orWarunek(String pmWarunek) {
		mWarunek.append(" OR " + pmWarunek);
		return this;
	}

	@Override
	public QueryBuilder andWarunek(SystemTables pmIdPracownika, Object pmWartosc) {
		if (mTabela == null)
			mTabela = " " + pmIdPracownika.getTableName() + " ";
		if (pmWartosc == null) {
			mWarunek.append(" AND " + pmIdPracownika.getTableName() + "." + pmIdPracownika.toString() + " is null ");
			return this;
		}
		String lvWartosc = valueToString(pmIdPracownika, pmWartosc);

		mWarunek.append(" AND " + pmIdPracownika.getTableName() + "." + pmIdPracownika.toString() + "=" + lvWartosc);
		return this;
	}

	@Override
	public QueryBuilder andBeforeOrEqual(SystemTables pmPole, LocalDate pmWartosc) {

		String lvWartosc = valueToString(pmPole, pmWartosc);

		mWarunek.append(" AND " + pmPole.getTableName() + "." + pmPole.toString() + " <=" + lvWartosc);
		return this;
	}

	@Override
	public QueryBuilder andAfterOrEqual(SystemTables pmPole, LocalDate pmWartosc) {

		String lvWartosc = valueToString(pmPole, pmWartosc);

		mWarunek.append(" AND " + pmPole.getTableName() + "." + pmPole.toString() + " >=" + lvWartosc);
		return this;
	}

	private String valueToString(SystemTables pmSystemTables, Object pmWartosc) {
		String lvWartosc = "NULL";
		if (mTabela == null)
			mTabela = " " + pmSystemTables.getTableName() + " ";
		if (Integer.class.equals(pmSystemTables.getKlasa()) && pmWartosc instanceof Integer)
			lvWartosc = String.valueOf(((Integer) pmWartosc).intValue());
		if (String.class.equals(pmSystemTables.getKlasa()) && pmWartosc instanceof String)
			lvWartosc = "'" + (String) pmWartosc + "'";
		if (Timestamp.class.equals(pmSystemTables.getKlasa()) && pmWartosc instanceof LocalDate
				&& TypZapytania.SELECT.equals(mTyp))
			lvWartosc = ParseryDB.DateParserToSQL_SELECT((LocalDate) pmWartosc);
		if (Timestamp.class.equals(pmSystemTables.getKlasa()) && pmWartosc instanceof Date
				&& TypZapytania.SELECT.equals(mTyp))
			lvWartosc = ParseryDB.DateParserToSQL_SELECT((Date) pmWartosc);
		if (Timestamp.class.equals(pmSystemTables.getKlasa()) && pmWartosc instanceof LocalDate
				&& !TypZapytania.SELECT.equals(mTyp))
			lvWartosc = ParseryDB.DateParserToSQL_INSERT((LocalDate) pmWartosc);
		if (Timestamp.class.equals(pmSystemTables.getKlasa()) && pmWartosc instanceof Date
				&& !TypZapytania.SELECT.equals(mTyp))
			lvWartosc = ParseryDB.DateParserToSQL_INSERT((Date) pmWartosc);
		return lvWartosc;
	}

	@Override
	public int count() {
		return executeQuery("SELECT Count(1) as TOTAL FROM " + mTabela + mWarunek.toString())
				.getAsInteger(new AliasDB("TOTAL", Integer.class));// count(*)
	}

	@Override
	public QueryBuilder orWarunek(SystemTables pmPole, Object pmWartosc) {
		mWarunek.append(" or " + pmPole.getTableName() + "." + pmPole.toString() + "=" + pmWartosc);
		return this;
	}

	@Override
	public QueryBuilder orderBy(SystemTables pmPole, boolean czyRosnaco) {

		mWarunek.append(
				" ORDER BY " + pmPole.getTableName() + "." + pmPole.toString() + (czyRosnaco ? " ASC" : " DESC"));
		return this;
	}

	@Override
	public LRecordSet execute() {
		if (TypZapytania.SELECT.equals(mTyp))
			return executeQuery("SELECT " + withTop() + mQuery.toString() + mWarunek.toString() + mOrderBy.toString());
		if (TypZapytania.UPDATE.equals(mTyp))
			executeUpdate("UPDATE " + mTabela + " SET " + mQuery.deleteCharAt(mQuery.length() - 1).toString()
					+ mWarunek.toString());
		if (TypZapytania.DELETE.equals(mTyp))
			executeUpdate("DELETE FROM " + mTabela + mWarunek.toString());
		if (TypZapytania.INSERT.equals(mTyp))
			executeUpdate("INSERT INTO " + mTabela + mapToInsert());
		return new LRecordSet();
	}

	private String withTop() {

		return mTop != null ? " TOP " + mTop : "";
	}

	private String mapToInsert() {

		StringBuilder lvInsert = new StringBuilder();
		lvInsert.append(" (");
		mPola.forEach((key, pole) -> lvInsert.append(key + ","));
		lvInsert.deleteCharAt(lvInsert.length() - 1);
		lvInsert.append(" ) values (");
		mPola.forEach((key, pole) -> lvInsert.append(pole + ","));
		lvInsert.deleteCharAt(lvInsert.length() - 1);
		lvInsert.append(")");

		return lvInsert.toString();
	}

	private enum TypZapytania {
		SELECT, UPDATE, DELETE, INSERT, COUNT;
	}

	@Override
	public QueryBuilder delete(SystemTablesNames pmTabela) {
		mTabela = " " + pmTabela.getNazwa() + " ";
		return this;
	}

	@Override
	public QueryBuilder joinOn(SystemTables pmWith, SystemTables pmOn) {
		mQuery.append(" INNER JOIN " + pmWith.getTableName() + " ON " + pmWith.getTableName() + "." + pmWith + "="
				+ pmOn.getTableName() + "." + pmOn);
		return this;
	}

	public static int getNextId(SystemTables pmPole) {
		Integer lvActalId = executeQuery(
				"SELECT TOP 1 " + pmPole + " FROM " + pmPole.getTableName() + " ORDER BY ID_tabeli DESC")
						.getAsInteger(pmPole);
		return (lvActalId == null ? 0 : lvActalId.intValue()) + 1;
	}

	@Override
	public QueryBuilder top(int pmTop) {

		mTop = new Integer(pmTop);
		return this;
	}

}
