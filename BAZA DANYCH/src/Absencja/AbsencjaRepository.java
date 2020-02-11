package Absencja;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Parsery.ParseryDB;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import pl.home.Database.components.AccessDB;

public class AbsencjaRepository extends AccessDB implements AbsencjaRepositor {

	public static int GetCount(String pmTabela) {

		String lvZapytanie = "SELECT Count(*) as TOTAL FROM " + pmTabela;
		return executeQuery(lvZapytanie).get(0).getAsInteger("TOTAL");
	}

	public static Object[][] getRecordSets(String pmZapytanie) {
		Object[][] lvData;
		try {
			Connection conn = init();

			Statement s = conn.createStatement();
			s.execute(pmZapytanie);
			ResultSet rs = s.getResultSet();
			ResultSetMetaData metaData = rs.getMetaData();

			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			ArrayList<Integer> lvListaKolumn = new ArrayList<Integer>();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column).replace('_', ' '));
				lvListaKolumn.add(column);
			}

			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();

				for (int columnIndex : lvListaKolumn) {
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			lvData = data.stream().map(List::toArray).toArray(Object[][]::new);
			s.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return lvData;
	}

	public static void DeleteRow(String tabela, int id) {
		String zapytanie = "Delete from " + tabela + " where ID_tabeli=" + id;
		executeUpdate(zapytanie);
	}

	@Override
	public Object[][] getAbsencjePracownika(int pmId) {
		return getRecordSets(
				"Select Id_tabeli, Id_pracownika,Od_kiedy,Do_kiedy,RODZAJ,EKWIWALENT from Absencje where id_pracownika="
						+ pmId);
	}

	@Override
	public Object[][] getAbsencjePoId(int pmId) {
		return getRecordSets(
				"Select Id_tabeli, Id_pracownika,Od_kiedy,Do_kiedy,RODZAJ,EKWIWALENT from Absencje where id_tabeli="
						+ pmId);
	}

	@Override
	public int ileDniWolnych(LocalDate pmDataOd, LocalDate pmDataDo) {
		return GetCount("DniWolne"//
				+ " where DATA BEtween " + ParseryDB.DateParserToSQL_SELECT(pmDataOd) + " and "
				+ ParseryDB.DateParserToSQL_SELECT(pmDataDo));
	}

	@Override
	public void dodajAbsencje(AbsencjaDTO pmAbs) {
		executeUpdate("INSERT INTO Absencje (ID_tabeli , ID_pracownika , Od_kiedy , Do_kiedy , RODZAJ,EKWIWALENT ) "//
				+ " VALUES (" + pmAbs.getId() + " , " //
				+ pmAbs.getIdPracownika() + " ," + ParseryDB.DateParserToSQL_INSERT(pmAbs.getOkres().getStart()) + " , "
				+ ParseryDB.DateParserToSQL_INSERT(pmAbs.getOkres().getEnd()) + " ," //
				+ "'" + pmAbs.getRodzaj().getKod() + "'," //
				+ "'" + pmAbs.getProcent().getKodString() + "'" + ")");
	}

	@Override
	public void usunAbsencje(int pmID) {
		executeUpdate("Delete * from Absencje where Id_tabeli=" + pmID);
	}

	@Override
	public int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja) {
		return GetCount("Absencje where id_tabeli != " + pmAbsencja.getId() + " and id_pracownika="
				+ pmAbsencja.getIdPracownika() + " and Od_kiedy <= "
				+ ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getEnd()) //
				+ " and Do_Kiedy>=" + ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getStart()));
	}
}
