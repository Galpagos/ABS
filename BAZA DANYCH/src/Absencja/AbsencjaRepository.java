package Absencja;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import Parsery.ParseryDB;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public class AbsencjaRepository implements AbsencjaRepositor {
	public static String mSciezkaDoBazy = "jdbc:ucanaccess://BAZA.accdb";

	public static void Zapisz(String instrukcja) {
		try {
			Connection conn = DriverManager.getConnection(mSciezkaDoBazy);

			System.out.println(instrukcja);
			Statement s = conn.createStatement();
			s.execute(instrukcja);
			s.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static int GetCount(String Zapytanie) {
		try {
			Connection conn = DriverManager.getConnection(mSciezkaDoBazy);
			ResultSet rs;
			String instrukcja = "SELECT Count(*) as total FROM " + Zapytanie;
			Statement s = conn.createStatement();
			s.execute(instrukcja);
			rs = s.getResultSet();
			rs.next();
			int k = rs.getInt("total");

			s.close();
			conn.close();
			return k;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static int GetNextID(String tabela) {
		try {
			Connection conn = DriverManager.getConnection(mSciezkaDoBazy);
			ResultSet rs;
			Statement s = conn.createStatement();
			s.execute("SELECT TOP 1 ID_tabeli FROM " + tabela + " ORDER BY ID_tabeli DESC");
			rs = s.getResultSet();
			if (!rs.next())
				return 1;
			int k = rs.getInt(1) + 1;

			s.close();
			conn.close();
			return k;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static Object[][] getRecordSets(String pmZapytanie) {
		Object[][] lvData;
		try {
			Connection conn = DriverManager.getConnection(mSciezkaDoBazy);

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
		Zapisz(zapytanie);
	}

	public Object[][] getAbsencjePracownika(int pmId) {
		return getRecordSets(
				"Select Id_tabeli, Id_pracownika,Od_kiedy,Do_kiedy,Rodzaj_absencji from Absencje where id_pracownika="
						+ pmId);
	}

	public Object[][] getAbsencjePoId(int pmId) {
		return getRecordSets(
				"Select Id_tabeli, Id_pracownika,Od_kiedy,Do_kiedy,Rodzaj_absencji from Absencje where id_tabeli="
						+ pmId);
	}

	public int ileDniWolnych(Date pmDataOd, Date pmDataDo) {
		return GetCount("DniWolne"//
				+ " where DATA BEtween " + ParseryDB.DateParserToSQL_SELECT(pmDataOd) + " and "
				+ ParseryDB.DateParserToSQL_SELECT(pmDataDo));
	}

	public void dodajAbsencje(AbsencjaDTO pmAbs) {
		Zapisz("INSERT INTO Absencje (ID_tabeli , ID_pracownika , Od_kiedy , Do_kiedy , Rodzaj_absencji ) "//
				+ " VALUES (" + pmAbs.getId() + " , " + pmAbs.getIdPracownika() + " ,"
				+ ParseryDB.DateParserToSQL_INSERT(pmAbs.getOkres().getStart().toDate()) + " , "
				+ ParseryDB.DateParserToSQL_INSERT(pmAbs.getOkres().getEnd().toDate()) + " ,\""
				+ pmAbs.getRodzaj().toString() + "\")");
	}

	public void usunAbsencje(int pmID) {
		Zapisz("Delete * from Absencje where Id_tabeli=" + pmID);
	}

	public int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja) {
		return GetCount("Absencje where id_tabeli != " + pmAbsencja.getId() + " and id_pracownika="
				+ pmAbsencja.getIdPracownika() + " and Od_kiedy <= "
				+ ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getEnd().toDate()) //
				+ " and Do_Kiedy>=" + ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getStart().toDate()));
	}
}
