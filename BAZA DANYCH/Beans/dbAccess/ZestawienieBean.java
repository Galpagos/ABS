package dbAccess;

public class ZestawienieBean
{
	private static String NazwaTabeli = "Zestawienie";
	private static String kolumnaID = "ID_tabeli";
	private static String kolumnaNazwaPracownika = "Pracownik";
	private int lvID;
	private String lvNazwa;

	public static String getNazwaTabeli()
	{
		return NazwaTabeli;
	}

	public static String getKolumnaID()
	{
		return kolumnaID;
	}

	public static String getKolumnaNazwaPracownika()
	{
		return kolumnaNazwaPracownika;

	}

	public int getLvID()
	{
		return lvID;
	}

	public void setLvID(int pmLvID)
	{
		lvID = pmLvID;
	}

	public String getLvNazwa()
	{
		return lvNazwa;
	}

	public void setLvNazwa(String pmLvNazwa)
	{
		lvNazwa = pmLvNazwa;
	}

	public String ZapisDataSetu()
	{
		return "INSERT INTO " + NazwaTabeli + " (" + kolumnaID + ", " + kolumnaNazwaPracownika + ")" + " VALUES ("
				+ getLvID() + ",\"" + getLvNazwa() + "\")";
	}

}
