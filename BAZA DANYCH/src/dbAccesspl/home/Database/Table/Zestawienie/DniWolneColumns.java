package dbAccesspl.home.Database.Table.Zestawienie;

import java.sql.Timestamp;

public enum DniWolneColumns implements SystemTables {
	ID_tabeli(Integer.class, "Identyfikator"), //
	Data(Timestamp.class, "Data"), //
	Opis(String.class, "Opis");

	private Class<?> mKlasa;
	private String mColumnName;

	<T> DniWolneColumns(Class<T> cls, String pmNazwa) {
		mColumnName = pmNazwa;
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return "DniWolne";
	}

	@Override
	public String getColumnName() {

		return mColumnName;
	}
}
