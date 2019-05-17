package dbAccess.Frames.OknoPracownika;

import dbAccess.AbsencjaBean;
import dbAccess.dbAccess;

public class OknoPracownikaRepository
{
	public void usunAbsencje(int pmIdAbsencji)
	{
		dbAccess.Zapisz("Delete * from " + AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaID + " = "
				+ pmIdAbsencji);
	}

}
