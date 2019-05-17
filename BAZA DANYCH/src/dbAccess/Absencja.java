package dbAccess;

import java.util.Date;

import Enums.SLRodzajeAbsencji;
import Parsery.ParseryDB;

public class Absencja extends AbsencjaBean
{

	public Absencja()
	{
		setId(dbAccess.GetNextID("Absencje"));
		setDataDo(new Date());
		setIdPracownika(0);
		setDataOd(new Date());
		setRodzajAbsencji(SLRodzajeAbsencji.urlop_w_pracy);
	}

	public Absencja(int id, int id_pracownika, Date dataod, Date datado, SLRodzajeAbsencji rodzabs)
	{
		setId(id);
		setIdPracownika(id_pracownika);
		setDataOd(dataod);
		setDataDo(datado);
		setRodzajAbsencji(rodzabs);
	}

	public String ZapiszDataSet()
	{
		return "INSERT INTO " + NazwaTabeli + " (" + kolumnaID + " , " + kolumnaIdPracownika + " , " + kolumnaOdKiedy
				+ " , " + kolumnaDoKiedy + " , " + kolumnaRodzajAbsencji + " ) "//
				+ " VALUES (" + getId() + " , " + getIdPracownika() + " ,"
				+ ParseryDB.DateParserToSQL_INSERT(getDataOd()) + " , " + ParseryDB.DateParserToSQL_INSERT(getDataDo())
				+ " ,\"" + getRodzajAbsencji().toString() + "\")";
	}

	public void SetNextIdAbsencji()
	{
		setId(dbAccess.GetNextID("Absencje"));
	}
}
