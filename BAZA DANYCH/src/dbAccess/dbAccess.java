package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class dbAccess
{
	// public static String mSciezkaDoBazy = "jdbc:ucanaccess://BAZA.accdb";
	public static String mSciezkaDoBazy = "jdbc:ucanaccess://C:/Users/Bogdan/Desktop/BAZA_NEW/BAZA.accdb";

	public static void Zapisz(String instrukcja)
	{
		try
		{
			Connection conn = DriverManager.getConnection(mSciezkaDoBazy);

			Statement s = conn.createStatement();
			s.execute(instrukcja);
			s.close();
			conn.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static int GetNextID(String tabela)
	{
		try
		{
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

		catch (Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	public static Object[][] getRecordSets(String pmZapytanie)
	{
		Object[][] lvData;
		try
		{
			Connection conn = DriverManager.getConnection(mSciezkaDoBazy);

			Statement s = conn.createStatement();
			s.execute(pmZapytanie);
			ResultSet rs = s.getResultSet();
			ResultSetMetaData metaData = rs.getMetaData();

			// names of columns
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			ArrayList<Integer> lvListaKolumn = new ArrayList<Integer>();
			for (int column = 1; column <= columnCount; column++)
			{
				// if (!metaData.getColumnName(column).contains("ID"))
				// {
				columnNames.add(metaData.getColumnName(column).replace('_', ' '));
				lvListaKolumn.add(column);
				// }
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next())
			{
				Vector<Object> vector = new Vector<Object>();

				for (int columnIndex : lvListaKolumn)
				{
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			lvData = data.stream().map(List::toArray).toArray(Object[][]::new);
			s.close();
			conn.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}

		return lvData;
	}

	public static void DeleteRow(String tabela, int id)
	{
		String zapytanie = "Delete from " + tabela + " where ID_tabeli=" + id;
		Zapisz(zapytanie);
	}

	public static MyTableModel modelTabeliDB(String pmZapytanie) throws SQLException
	{
		try
		{
			Connection conn = DriverManager.getConnection(mSciezkaDoBazy);

			Statement s = conn.createStatement();
			s.execute(pmZapytanie);
			ResultSet rs = s.getResultSet();
			ResultSetMetaData metaData = rs.getMetaData();

			// names of columns
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			ArrayList<Integer> lvListaKolumn = new ArrayList<Integer>();
			for (int column = 1; column <= columnCount; column++)
			{
				// if (!metaData.getColumnName(column).contains("ID"))
				// {
				columnNames.add(metaData.getColumnName(column).replace('_', ' '));
				lvListaKolumn.add(column);
				// }
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next())
			{
				Vector<Object> vector = new Vector<Object>();

				for (int columnIndex : lvListaKolumn)
				{
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			Object[][] lvData = data.stream().map(List::toArray).toArray(Object[][]::new);
			String[] lvColumnNames = columnNames.toArray(new String[0]);
			MyTableModel mt = new MyTableModel(lvData, lvColumnNames);
			s.close();
			conn.close();
			return mt;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}

	}

	public static class MyTableModel extends AbstractTableModel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columnNames;
		private Object[][] data;

		public int getColumnCount()
		{
			return columnNames.length;
		}

		public int getRowCount()
		{
			return data.length;
		}

		public String getColumnName(int col)
		{
			return columnNames[col];
		}

		public Object getValueAt(int row, int col)
		{
			return data[row][col];
		}

		@SuppressWarnings(
		{ "unchecked", "rawtypes" })
		public Class getColumnClass(int c)
		{
			for (int row = 0; row < getRowCount(); row++)
			{
				Object o = getValueAt(row, c);

				if (o != null)
				{
					return o.getClass();
				}
			}

			return Object.class;
		}

		public boolean isCellEditable(int row, int col)
		{
			return false;
		}

		public void setValueAt(Object value, int row, int col)
		{
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

		public MyTableModel(Object[][] pmData, String[] pmColumnNames)
		{
			columnNames = pmColumnNames;
			data = pmData.clone();
		}
	}

	public static int GetCount(String Zapytanie)
	{
		try
		{
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

		catch (Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	public static String WarunekZListy(List<String> pmLista)
	{
		String lvResult = "IN (";
		for (String elem : pmLista)
			lvResult = lvResult + elem + ",";

		return lvResult.substring(0, lvResult.length() - 1) + ")";

	}
}
