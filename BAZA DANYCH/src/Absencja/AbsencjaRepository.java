package Absencja;

import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.ID_tabeli;

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
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.AliasDB;
import dbAccesspl.home.Database.Table.Zestawienie.QueryBuilder;
import pl.home.Database.components.AccessDB;

public class AbsencjaRepository extends AccessDB implements AbsencjaRepositor {

	private static int GetCount(String pmTabela) {

		String lvZapytanie = "SELECT Count(1) as TOTAL FROM " + pmTabela;
		return executeQuery(lvZapytanie).getAsInteger(new AliasDB("TOTAL", Integer.class));
	}

	private static Object[][] getRecordSets(String pmZapytanie) {
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
		QueryBuilder.INSERT()//
				.set(ID_tabeli, pmAbs.getId())// a
				.set(AbsencjeColumns.ID_pracownika, pmAbs.getIdPracownika())//
				.set(AbsencjeColumns.Od_kiedy, pmAbs.getOkres().getStart())//
				.set(AbsencjeColumns.Do_kiedy, pmAbs.getOkres().getEnd())//
				.set(AbsencjeColumns.EKWIWALENT, pmAbs.getProcent().getKod())//
				.set(AbsencjeColumns.RODZAJ, pmAbs.getRodzaj().getKod())//
				.execute();

	}

	@Override
	public void usunAbsencje(int pmID) {
		QueryBuilder.DELETE()//
				.andWarunek(ID_tabeli, pmID)//
				.execute();
	}

	@Override
	public int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja) {
		return GetCount("Absencje where id_tabeli != " + pmAbsencja.getId() + " and id_pracownika="
				+ pmAbsencja.getIdPracownika() + " and Od_kiedy <= "
				+ ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getEnd()) //
				+ " and Do_Kiedy>=" + ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getStart()));
	}
}
