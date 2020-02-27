package dbAccesspl.home.Database.Table.Zestawienie;

public enum ZestawienieColumns implements SystemTables {
	ID_tabeli(Integer.class), //
	Pracownik(String.class), //
	Data_Zwolnienia(java.sql.Timestamp.class), //
	Data_Zatrudnienia(java.sql.Timestamp.class), //
	Urlop_Nalezny(Integer.class), //
	Data_Urodzenia(java.sql.Timestamp.class);//

	private Class<?> mKlasa;

	<T> ZestawienieColumns(Class<T> cls) {
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return " Zestawienie";
	}

}
