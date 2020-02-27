package dbAccesspl.home.Database.Table.Zestawienie;

public enum GrupyColumns implements SystemTables {

	ID_tabeli(Integer.class), //
	Nazwa(String.class);

	private Class<?> mKlasa;

	<T> GrupyColumns(Class<T> cls) {
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return " AD_GRUPY";
	}
}
