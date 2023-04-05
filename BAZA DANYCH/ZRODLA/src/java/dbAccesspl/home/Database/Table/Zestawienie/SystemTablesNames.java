package dbAccesspl.home.Database.Table.Zestawienie;

import ProjektGlowny.commons.DbBuilder.ISystemTableNames;
import ProjektGlowny.commons.DbBuilder.SystemTables;

import java.util.EnumSet;
public enum SystemTablesNames implements ISystemTableNames {

	ZESTAWIENIE("Zestawienie", EnumSet.allOf(ZestawienieColumns.class), ZestawienieColumns.ID_tabeli), //
	DNI_WOLNE("DniWolne", EnumSet.allOf(DniWolneColumns.class), DniWolneColumns.ID_tabeli), //
	AD_SYS_INFO("AD_SYS_INFO", EnumSet.allOf(SysInfoColumns.class), null), //
	AD_GRUPY_POWIAZANIA("AD_GRUPY_POWIAZANIA", EnumSet.allOf(GrupyPowiazaniaColumns.class), null), //
	AD_GRUPY("AD_GRUPY", EnumSet.allOf(GrupyColumns.class), GrupyColumns.ID_tabeli), //
	ABSENCJE("Absencje", EnumSet.allOf(AbsencjeColumns.class), AbsencjeColumns.ID_tabeli), //
	SOBOTA_ROBOCZA("SOB_ROB", EnumSet.allOf(SobotaRoboczaColumns.class), SobotaRoboczaColumns.ID_tabeli),
	DANE_WYDRUKI("DANE_WYDRUKI", EnumSet.allOf(DaneWydrukiColumns.class), DaneWydrukiColumns.ID_tabeli);

	SystemTablesNames(String pmTableName, EnumSet<? extends SystemTables> pmTabela, SystemTables pmPrimaryKey) {
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
	private SystemTables mPrimaryKey;

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
	public SystemTables getPrimaryKey() {
		return mPrimaryKey;
	}
}
