package dbAccesspl.home.Database.Table.Zestawienie;

import ProjektGlowny.commons.DbBuilder.SystemTables;

import java.sql.Timestamp;

public enum SobotaRoboczaColumns implements SystemTables {

	ID_tabeli(Integer.class, "Identyfikator"), //
	ID_pracownika(Integer.class, "Identyfikator_pracownika"), //
	DATA(Timestamp.class, "Data"), //
	GODZINY(Double.class, "Przepracowane_godziny");

	private Class<?> mKlasa;
	private String mColumnName;

	<T> SobotaRoboczaColumns(Class<T> cls, String pmNazwa) {
		mColumnName = pmNazwa;
		mKlasa = cls;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return SystemTablesNames.SOBOTA_ROBOCZA.getNazwa();
	}

	@Override
	public String getColumnName() {

		return mColumnName;
	}

}
