package dbAccesspl.home.Database.Table.Zestawienie;

import java.util.function.Function;

import ProjektGlowny.commons.DbBuilder.SystemTables;
import ProjektGlowny.commons.enums.InterfejsSlownika;

public enum GrupyPowiazaniaColumns implements SystemTables {

	ID_PRACOWNIKA(Integer.class, "Identyfikator pracownika"), //
	ID_GRUPY(Integer.class, "Identyfikator grupy");

	private Class<?> mKlasa;
	private String mColumnName;

	<T> GrupyPowiazaniaColumns(Class<T> cls, String pmNazwa) {
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

		return " AD_GRUPY_POWIAZANIA";
	}

	@Override
	public Function<String, InterfejsSlownika> getOpisFunkcja() {

		return null;
	}
}
