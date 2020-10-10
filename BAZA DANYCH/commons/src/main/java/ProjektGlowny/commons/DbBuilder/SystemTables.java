package ProjektGlowny.commons.DbBuilder;

import ProjektGlowny.commons.enums.InterfejsSlownika;

import java.util.function.Function;

public interface SystemTables {
	public Class<?> getKlasa();

	public abstract String getTableName();

	public String getColumnName();

	public default Function<String, InterfejsSlownika> getOpisFunkcja() {
		return null;
	}
}
