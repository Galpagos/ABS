package dbAccesspl.home.Database.Table.Zestawienie;

public enum SysInfoColumns implements SystemTables {

	NAZWA(String.class, "Parametr"), //
	WARTOSC(String.class, "Wartoœæ");

	private Class<?> mKlasa;
	private String mColumnName;

	<T> SysInfoColumns(Class<T> cls, String pmNazwa) {
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

		return "AD_SYS_INFO";
	}
}
