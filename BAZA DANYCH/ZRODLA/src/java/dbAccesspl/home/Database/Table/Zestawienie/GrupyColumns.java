package dbAccesspl.home.Database.Table.Zestawienie;

import ProjektGlowny.commons.DbBuilder.SystemTables;

public enum GrupyColumns implements SystemTables {

	ID_GRP(Integer.class, "Identyfikator"), //
	Nazwa(String.class, "Grupa");
	private Class<?> mKlasa;
	private String mColumnName;

	<T> GrupyColumns(Class<T> cls, String pmNazwa) {
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

		return " AD_GRUPY";
	}
}
