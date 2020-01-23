package Wersja;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

import dbAccess.dbAccess;

public class Przekod {
	public static String mSciezkaDoBazy = "BAZA.accdb";

	public static void Wykonaj() throws IOException {
		Database db = DatabaseBuilder.open(new File(mSciezkaDoBazy));
		if (db.getTable("AD_SYS_INFO") == null) {
			Table newTable;
			try {
				db.setColumnOrder(Database.DEFAULT_COLUMN_ORDER);
				newTable = new TableBuilder("AD_SYS_INFO").addColumn(new ColumnBuilder("NAZWA", DataType.TEXT))
						.addColumn(new ColumnBuilder("WARTOSC").setSQLType(Types.VARCHAR)).toTable(db);
				newTable.addRow("Wersja", "1.00");

				new ColumnBuilder("Urlop_Nalezny").setType(DataType.INT).addToTable(db.getTable("Zestawienie"));
				new ColumnBuilder("Data_Urodzenia").setType(DataType.SHORT_DATE_TIME)
						.addToTable(db.getTable("Zestawienie"));

				dbAccess.Zapisz("Update Zestawienie set Urlop_Nalezny=26");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Wykonano");
		}
		// db.close();
	}
}
