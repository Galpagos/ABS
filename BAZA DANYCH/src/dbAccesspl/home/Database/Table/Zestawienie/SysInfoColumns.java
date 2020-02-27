package dbAccesspl.home.Database.Table.Zestawienie;

public enum SysInfoColumns implements SystemTables {

	NAZWA(String.class), //
	WARTOSC(String.class);

	private Class<?> mKlasa;

	<T> SysInfoColumns(Class<T> cls) {
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return "AD_SYS_INFO";
	}
}
