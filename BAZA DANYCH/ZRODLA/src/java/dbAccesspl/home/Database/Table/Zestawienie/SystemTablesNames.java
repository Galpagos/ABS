package dbAccesspl.home.Database.Table.Zestawienie;

import ProjektGlowny.commons.DbBuilder.ISystemTableNames;
import ProjektGlowny.commons.DbBuilder.SystemTables;

import java.util.EnumSet;

public enum SystemTablesNames implements ISystemTableNames {

	ZESTAWIENIE("Zestawienie", EnumSet.allOf(ZestawienieColumns.class), "ID_PRAC"), //
	DNI_WOLNE("DniWolne", EnumSet.allOf(DniWolneColumns.class), "ID_DN_WN"), //
	AD_SYS_INFO("AD_SYS_INFO", EnumSet.allOf(SysInfoColumns.class), null), //
	AD_GRUPY_POWIAZANIA("AD_GRUPY_POWIAZANIA", EnumSet.allOf(GrupyPowiazaniaColumns.class), null), //
	AD_GRUPY("AD_GRUPY", EnumSet.allOf(GrupyColumns.class), "ID_GRP"), //
	ABSENCJE("Absencje", EnumSet.allOf(AbsencjeColumns.class), "ID_ABS");

	SystemTablesNames(String pmTableName, EnumSet<? extends SystemTables> pmTabela, String pmPrimaryKey) {
		mTabela = pmTabela;
		mNazwa = pmTableName;
		mPrimaryKey = pmPrimaryKey;
	}

	@Override
	public EnumSet<? extends SystemTables> getTabela() {
		return mTabela;
	}

	@Override
	public String getNazwa() {
		return mNazwa;
	}

	private EnumSet<? extends SystemTables> mTabela;
	private String mNazwa;
	private String mPrimaryKey;

	@Override
	public SystemTablesNames getByName(String pmName) {
		for (SystemTablesNames e : SystemTablesNames.values()) {
			if (e.getNazwa().equalsIgnoreCase(pmName)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public String getPrimaryKey() {
		return mPrimaryKey;
	}
}
