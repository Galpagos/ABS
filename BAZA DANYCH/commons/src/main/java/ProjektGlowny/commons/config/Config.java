package ProjektGlowny.commons.config;

import ProjektGlowny.commons.DbBuilder.ISystemTableNames;

public class Config {
	private static ISystemTableNames mSystemTableNames;

	public static ISystemTableNames getSystemTableNames() {
		return mSystemTableNames;
	}

	public static void setSystemTableNames(ISystemTableNames pmMSystemTableNames) {
		mSystemTableNames = pmMSystemTableNames;
	}
}
