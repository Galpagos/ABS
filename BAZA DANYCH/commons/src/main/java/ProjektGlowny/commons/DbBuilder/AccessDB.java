package ProjektGlowny.commons.DbBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Optional;

import ProjektGlowny.commons.config.Config;

public class AccessDB {
		public static LRecordSet executeQuery(String pmZapytanie) {
		LRecordSet lvWynik = new LRecordSet();
		System.out.println(pmZapytanie);
		try (//
				Connection lvCon = init(); //
				
				Statement lvStatement = lvCon.createStatement();)//

		{
			lvStatement.execute(pmZapytanie);
			ResultSet rs = lvStatement.getResultSet();
			if (rs != null) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				while (rs.next()) {
					LRecord lvMapa = new LRecord();
					for (int column = 1; column <= columnCount; column++) {
						ISystemTableNames lvTabela = Config.getSystemTableNames()
								.getByName(metaData.getTableName(column));
						String lvPoleS = metaData.getColumnName(column);
						if (lvTabela != null) {
							Optional<? extends SystemTables> lvPole = lvTabela.getTabela().stream()
									.filter(lvA -> lvPoleS.equalsIgnoreCase(lvA.toString()))//
									.findAny();
							if (lvPole.isPresent())
								lvMapa.put(lvPole.get(), rs.getObject(column));
						} else
							lvMapa.put(new AliasDB(lvPoleS, Object.class), rs.getObject(column));

					}
					lvWynik.add(lvMapa);
				}
				rs.close();
			}
		} catch (Exception lvLvE) {
			lvLvE.printStackTrace();
		}
		return lvWynik;
	}

	public static int getNextID(SystemTables pmPole) {

		try (//
				Connection lvCon = init(); //
				Statement lvStatement = lvCon.createStatement();) {
			lvStatement.execute(""//
					+ " SELECT"//
					+ "   TOP 1 " + pmPole.toString()//
					+ " FROM " //
					+ pmPole.getTableName()//
					+ " ORDER BY" //
					+ "   " + pmPole.toString() + " DESC");

			ResultSet rs = lvStatement.getResultSet();
			if (!rs.next())
				return 1;
			int k = rs.getInt(1) + 1;
			rs.close();
			return k;
		} catch (Exception lvException) {
			lvException.printStackTrace();
		}
		return 0;
	}

	protected static boolean executeUpdate(String pmZapytanie) {
		System.out.println(pmZapytanie);
		System.out.println("");
		boolean lvWynik = false;
		try (//
				Connection lvCon = init(); //
				Statement lvStatement = lvCon.createStatement();)//
		{
			lvWynik = lvStatement.execute(pmZapytanie);
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return lvWynik;
	}

	protected static Connection init() {
		Connection lvCon = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			lvCon = DriverManager.getConnection(Config.PATH_DATABASE);
			// lvCon.setAutoCommit(false);
		} catch (Exception lvE) {
			System.err.println("Nie polaczono z baza");
		}
		return lvCon;
	}
}
