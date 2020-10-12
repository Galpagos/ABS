package dbAccesspl.home.Database.Table.Zestawienie;

import ProjektGlowny.commons.DbBuilder.SystemTables;

public enum ZestawienieColumns implements SystemTables {
	ID_tabeli(Integer.class, "Identyfikator"), //
	Pracownik(String.class, "Pracownik"), //
	Data_Zwolnienia(java.sql.Timestamp.class, "Data zwolnienia"), //
	Data_Zatrudnienia(java.sql.Timestamp.class, "Data zatrudnienia"), //
	Urlop_Nalezny(Integer.class, "Urlop należny"), //
	Data_Urodzenia(java.sql.Timestamp.class, "Data urodzenia");//

	private Class<?> mKlasa;
	private String mColumnName;

	<T> ZestawienieColumns(Class<T> cls, String pmNazwa) {
		mColumnName = pmNazwa;
		mKlasa = cls;

	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getColumnName() {

		return mColumnName;
	}

	@Override
	public String getTableName() {

		return " Zestawienie";
	}

}
