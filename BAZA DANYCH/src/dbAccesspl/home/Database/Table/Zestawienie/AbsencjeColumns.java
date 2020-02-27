package dbAccesspl.home.Database.Table.Zestawienie;

import java.sql.Timestamp;

public enum AbsencjeColumns implements SystemTables {

	ID_tabeli(Integer.class), //
	ID_pracownika(Integer.class), //
	Od_kiedy(Timestamp.class), //
	Do_kiedy(Timestamp.class), //
	EKWIWALENT(Integer.class), //
	RODZAJ(String.class);

	private Class<?> mKlasa;

	<T> AbsencjeColumns(Class<T> cls) {
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return " Absencje ";
	}

}
