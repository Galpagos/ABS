package dbAccesspl.home.Database.Table.Zestawienie;

import ProjektGlowny.commons.DbBuilder.SystemTables;

public enum DaneWydrukiColumns implements SystemTables {

	ID_tabeli(Integer.class, "ID_POZYCJI_TAB"), //
	ID_WYDRUKU(Integer.class, "ID_WYDRUKU"), //
	ID_OSOBY(Integer.class, "ID_OSOBY"), //
	ID_POZYCJI(Integer.class, "ID_POZYCJI");

	private Class<?> mKlasa;
	private String mColumnName;

	<T> DaneWydrukiColumns(Class<T> cls, String pmNazwa) {
		mColumnName = pmNazwa;
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return SystemTablesNames.DANE_WYDRUKI.getNazwa();
	}

	@Override
	public String getColumnName() {

		return mColumnName;
	}

}
