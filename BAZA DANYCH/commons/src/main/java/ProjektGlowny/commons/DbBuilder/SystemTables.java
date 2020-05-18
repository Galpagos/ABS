package ProjektGlowny.commons.DbBuilder;

import java.util.function.Function;

import ProjektGlowny.commons.enums.InterfejsSlownika;

public interface SystemTables {
	public Class<?> getKlasa();

	public abstract String getTableName();

	public String getColumnName();

	public default Function<String, InterfejsSlownika> getOpisFunkcja() {
		return null;
	}

}
