package dbAccesspl.home.Database.Table.Zestawienie;

public enum GrupyPowiazaniaColumns implements SystemTables {

	ID_PRACOWNIKA(Integer.class), //
	ID_GRUPY(Integer.class);

	private Class<?> mKlasa;

	<T> GrupyPowiazaniaColumns(Class<T> cls) {
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return " AD_GRUPY_POWIAZANIA ";
	}
}
