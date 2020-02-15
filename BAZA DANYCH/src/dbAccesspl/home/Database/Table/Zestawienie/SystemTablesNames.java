package dbAccesspl.home.Database.Table.Zestawienie;

import java.util.EnumSet;

public enum SystemTablesNames {

	ZESTAWIENIE("Zestawienie", EnumSet.allOf(ZestawienieColumns.class)), //
	DNI_WOLNE("DniWolne", EnumSet.allOf(DniWolneColumns.class)), //
	AD_SYS_INFO("AD_SYS_INFO", EnumSet.allOf(SysInfoColumns.class)), //
	AD_GRUPY_POWIAZANIA("AD_GRUPY_POWIAZANIA", EnumSet.allOf(GrupyPowiazaniaColumns.class)), //
	AD_GRUPY("AD_GRUPY", EnumSet.allOf(GrupyColumns.class)), //
	ABSENCJE("Absencje", EnumSet.allOf(AbsencjeColumns.class));

	SystemTablesNames(String pmTableName, EnumSet<? extends SystemTables> pmTabela) {
		mTabela = pmTabela;
		mNazwa = pmTableName;
	}

	public EnumSet<? extends SystemTables> getTabela() {
		return mTabela;
	}

	public String getNazwa() {
		return mNazwa;
	}

	EnumSet<? extends SystemTables> mTabela;
	String mNazwa;
}
