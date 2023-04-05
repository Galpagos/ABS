package Wersja;

import ProjektGlowny.commons.DbBuilder.AccessDB;

import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.IndexBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

import dbAccesspl.home.Database.Table.Zestawienie.DaneWydrukiColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SysInfoColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;

public class Przekod extends AccessDB {

	public static void Wykonaj() throws IOException {

		if ("10.00".equals(pobierzWersje()))
			return;
		try (Database db = DatabaseBuilder.open(new File("..//BAZA.accdb"))) {
			System.out.println("Podbito wersję");
			TableBuilder lvTable = new TableBuilder(SystemTablesNames.DANE_WYDRUKI.getNazwa())//
					.addColumn(new ColumnBuilder(DaneWydrukiColumns.ID_tabeli.name()).setType(DataType.INT))//
					.addColumn(new ColumnBuilder(DaneWydrukiColumns.ID_OSOBY.name()).setType(DataType.INT))//
					.addColumn(new ColumnBuilder(DaneWydrukiColumns.ID_POZYCJI.name()).setType(DataType.INT))//
					.addColumn(new ColumnBuilder(DaneWydrukiColumns.ID_WYDRUKU.name()).setType(DataType.INT))
					.addIndex(new IndexBuilder(IndexBuilder.PRIMARY_KEY_NAME).addColumns(DaneWydrukiColumns.ID_tabeli.name()).setPrimaryKey());

			Table lvDaneWydruki = lvTable.toTable(db);
			List<Integer> lvInitIdList = Arrays.asList( //
					81, 16, 84, 20, 23, 26, 75, 27, 0, //
					2, 30, 35, 36, 39, 40, 42, 10, 43, 44, 45, 51, 46, 47, 0, //
					1, 22, 4, 5, 0, //
					18, 25, 8, 6, 9, 41, 0, //
					7);
			for (int i = 0; i < lvInitIdList.size(); i++) {
				Object[] lvRow = {i, lvInitIdList.get(i), i, 1};
				lvDaneWydruki.addRow(lvRow);
			}
		}

		podbijWersje("10.00");
		JOptionPane.showMessageDialog(null, "Uruchom ponownie program", "", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
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
