package dbAccesspl.home.Database.Table.Zestawienie;

import java.sql.Timestamp;

import javax.print.DocFlavor.STRING;

public enum AbsencjeColumns implements SystemTables {

	ID_tabeli(Integer.class), //
	ID_pracownika(Integer.class), //
	Od_kiedy(Timestamp.class), //
	Do_kiedy(Timestamp.class), //
	EKWIWALENT(String.class), //
	RODZAJ(STRING.class);

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
