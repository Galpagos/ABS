package Wersja;

import ProjektGlowny.commons.DbBuilder.AccessDB;

import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.IndexBuilder;
import com.healthmarketscience.jackcess.TableBuilder;

import dbAccesspl.home.Database.Table.Zestawienie.SobotaRoboczaColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SysInfoColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;

public class Przekod extends AccessDB {

	public static void Wykonaj() throws IOException {

		if (!"3.00".equals(pobierzWersje()))
			return;
		try (Database db = DatabaseBuilder.open(new File("..//BAZA.accdb"))) {
			System.out.println("Podbito wersję");
			TableBuilder lvTable = new TableBuilder(SystemTablesNames.SOBOTA_ROBOCZA.getNazwa())//
					.addColumn(new ColumnBuilder(SobotaRoboczaColumns.ID_tabeli.name()).setType(DataType.INT))//
					.addColumn(new ColumnBuilder(SobotaRoboczaColumns.ID_pracownika.name()).setType(DataType.INT))//
					.addColumn(new ColumnBuilder(SobotaRoboczaColumns.DATA.name()).setType(DataType.SHORT_DATE_TIME))//
					.addColumn(new ColumnBuilder(SobotaRoboczaColumns.GODZINY.name()).setType(DataType.INT))
					.addIndex(new IndexBuilder(IndexBuilder.PRIMARY_KEY_NAME).addColumns(SobotaRoboczaColumns.ID_tabeli.name()).setPrimaryKey());

			lvTable.toTable(db);

		}

		podbijWersje("4.00");
		// try (Connection lvCon = init(); PreparedStatement lvStm =
		// lvCon.prepareStatement(" ALTER TABLE Absencje DROP COLUMN
		// Rodzaj_absencji ");) {
		//
		// lvStm.execute();
		// } catch (SQLException lvE) {
		// lvE.printStackTrace();
		// }
	}

	private static void podbijWersje(String pmWersja) {
		executeUpdate("UPDATE AD_SYS_INFO SET WARTOSC='" + pmWersja + "' WHERE NAZWA='Wersja'");
	}

	private static String pobierzWersje() {
		return executeQuery("SELECT WARTOSC FROM AD_SYS_INFO WHERE NAZWA='Wersja'").getAsString(SysInfoColumns.WARTOSC);
	}

}
