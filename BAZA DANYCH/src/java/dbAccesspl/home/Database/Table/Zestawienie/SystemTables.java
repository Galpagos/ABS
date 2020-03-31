package dbAccesspl.home.Database.Table.Zestawienie;

import java.util.function.Function;

import Enums.InterfejsSlownika;

public interface SystemTables {
	public Class<?> getKlasa();

	public abstract String getTableName();

	public String getColumnName();

	public default Function<String, InterfejsSlownika> getOpisFunkcja() {
		return null;
	}

}
