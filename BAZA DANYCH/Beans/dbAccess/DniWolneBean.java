package dbAccess;

import java.util.Date;

import Parsery.ParseryDB;

public class DniWolneBean
{
	public final static String NazwaTabeli = "DniWolne";
	public final static String kolumnaID = "ID_tabeli";
	public final static String kolumnaData = "Data";
	public final static String kolumnaOpis = "Opis";
	
	private int mId;
	private Date mData;
	private String mOpis;
	public int getId()
	{
		return mId;
	}
	public void setId(int pmId)
	{
		mId = pmId;
	}
	public Date getData()
	{
		return mData;
	}
	public void setData(Date pmData)
	{
		mData = pmData;
	}
	public String getOpis()
	{
		return mOpis;
	}
	public void setOpis(String pmOpis)
	{
		mOpis = pmOpis;
	}
	public String ZapiszDataSet()
	{
		return "INSERT INTO " + NazwaTabeli + " (" + kolumnaID + " , " + kolumnaData + " , " + kolumnaOpis+ " ) "//
				+ " VALUES (" + getId() + " , " + ParseryDB.DateParserToSQL_INSERT(getData()) + " , \"" + getOpis() + "\")";
	}
}
