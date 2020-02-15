package dbAccesspl.home.Database.Table.Zestawienie;

import java.sql.Timestamp;

public enum DniWolneColumns implements SystemTables {
	ID_tabeli(Integer.class), //
	Data(Timestamp.class), //
	Opis(String.class);

	private Class<?> mKlasa;

	<T> DniWolneColumns(Class<T> cls) {
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return " DniWolne ";
	}
}
