package Wersja;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import Enums.SLRodzajeAbsencji;
import pl.home.Database.components.AccessDB;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;

public class Przekod extends AccessDB {
	public static String mSciezkaDoBazy = "BAZA.accdb";

	public static void Wykonaj() throws IOException {

//	}
//
//	private  void przekodzik() throws IOException {
		if (!"1.00".equals(pobierzWersje()))
			return;
		podbijWersje("2.00");
		try (Database db = DatabaseBuilder.open(new File(mSciezkaDoBazy))) {
			System.out.println("Podbito wersjê");
			new ColumnBuilder("EKWIWALENT").setType(DataType.TEXT).addToTable(db.getTable("Absencje"));
			new ColumnBuilder("RODZAJ").setType(DataType.TEXT).addToTable(db.getTable("Absencje"));
		}
		System.out.println("Zaczynam updaty");
		updateAbsencja(SLRodzajeAbsencji.ci¹¿a, SLEkwiwalentZaUrlop.PROCENT_100);
		updateAbsencja(SLRodzajeAbsencji.DW, SLEkwiwalentZaUrlop.PROCENT_0);
		updateAbsencja(SLRodzajeAbsencji.L_4, SLEkwiwalentZaUrlop.PROCENT_80);
		updateAbsencja(SLRodzajeAbsencji.NB, SLEkwiwalentZaUrlop.PROCENT_0);
		updateAbsencja(SLRodzajeAbsencji.NN, SLEkwiwalentZaUrlop.PROCENT_0);
		updateAbsencja(SLRodzajeAbsencji.NUN, SLEkwiwalentZaUrlop.PROCENT_0);
		updateAbsencja(SLRodzajeAbsencji.NZ, SLEkwiwalentZaUrlop.PROCENT_0);
		updateAbsencja(SLRodzajeAbsencji.opieka2dni, SLEkwiwalentZaUrlop.PROCENT_100);
		updateAbsencja(SLRodzajeAbsencji.opieka_na_dziecko, SLEkwiwalentZaUrlop.PROCENT_80);
		updateAbsencja(SLRodzajeAbsencji.opieka_na_kogos, SLEkwiwalentZaUrlop.PROCENT_80);
		updateAbsencja(SLRodzajeAbsencji.swiadczenie_rehab, SLEkwiwalentZaUrlop.PROCENT_90);
		updateAbsencja(SLRodzajeAbsencji.szpital, SLEkwiwalentZaUrlop.PROCENT_80);
		updateAbsencja(SLRodzajeAbsencji.UB, SLEkwiwalentZaUrlop.PROCENT_0);
		updateAbsencja(SLRodzajeAbsencji.wypadek, SLEkwiwalentZaUrlop.PROCENT_100);
		updateAbsencja(SLRodzajeAbsencji.urlop_wypoczynkowy, SLEkwiwalentZaUrlop.PROCENT_100);
		updateAbsencja(SLRodzajeAbsencji.urlop_w_pracy, SLEkwiwalentZaUrlop.PROCENT_100);
		updateAbsencja(SLRodzajeAbsencji.urlop_rodzicielski, SLEkwiwalentZaUrlop.PROCENT_80);
		updateAbsencja(SLRodzajeAbsencji.urlop_okolicznoœciowy, SLEkwiwalentZaUrlop.PROCENT_100);
		updateAbsencja(SLRodzajeAbsencji.urlop_ojcowski, SLEkwiwalentZaUrlop.PROCENT_100);
		updateAbsencja(SLRodzajeAbsencji.urlop_macierzyñski, SLEkwiwalentZaUrlop.PROCENT_80);
		System.out.println("Koniec updatow");
		try (Connection lvCon = init();
				PreparedStatement lvStm = lvCon
						.prepareStatement(" ALTER TABLE Absencje DROP COLUMN Rodzaj_absencji ");) {

			lvStm.execute();
		} catch (SQLException lvE) {
			lvE.printStackTrace();
		}
	}

	private static void updateAbsencja(SLRodzajeAbsencji pmAbsencja, SLEkwiwalentZaUrlop pmProcent) {
		executeUpdate(""//
				+ " UPDATE "//
				+ "   Absencje "//
				+ " SET "//
				+ "   EKWIWALENT = '" + pmProcent.getKodString() + "',"//
				+ "   RODZAJ = '" + pmAbsencja.getKod() + "'"//
				+ " WHERE"//
				+ "   Rodzaj_absencji = '" + pmAbsencja.getNazwa() + "'");
		System.out.println("update " + pmAbsencja.getNazwa());
	}

	private static void podbijWersje(String pmWersja) {
		executeUpdate("UPDATE AD_SYS_INFO SET WARTOSC='" + pmWersja + "' WHERE NAZWA='Wersja'");
	}

	private static String pobierzWersje() {
		return executeQuery("SELECT WARTOSC FROM AD_SYS_INFO WHERE NAZWA='Wersja'").getAsString("WARTOSC");
	}
}
