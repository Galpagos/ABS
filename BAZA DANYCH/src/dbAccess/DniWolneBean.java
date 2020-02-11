package dbAccess;

import java.time.LocalDate;

import Parsery.ParseryDB;

public class DniWolneBean {
	public final static String NazwaTabeli = "DniWolne";
	public final static String kolumnaID = "ID_tabeli";
	public final static String kolumnaData = "Data";
	public final static String kolumnaOpis = "Opis";

	private int mId;
	private LocalDate mData;
	private String mOpis;

	public int getId() {
		return mId;
	}

	public void setId(int pmId) {
		mId = pmId;
	}

	public LocalDate getData() {
		return mData;
	}

	public void setData(LocalDate pmData) {
		mData = pmData;
	}

	public String getOpis() {
		return mOpis;
	}

	public void setOpis(String pmOpis) {
		mOpis = pmOpis;
	}

	public String ZapiszDataSet() {
		return "INSERT INTO " + NazwaTabeli + " (" + kolumnaID + " , " + kolumnaData + " , " + kolumnaOpis + " ) "//
				+ " VALUES (" + getId() + " , " + ParseryDB.DateParserToSQL_INSERT(getData()) + " , \"" + getOpis()
				+ "\")";
	}
}
