package ProjektGlowny.commons.DbBuilder;

import java.util.EnumSet;

public interface ISystemTableNames {
	public ISystemTableNames getByName(String pmName);

	public EnumSet<? extends SystemTables> getTabela();

	public String getNazwa();

	public String getPrimaryKey();
}
