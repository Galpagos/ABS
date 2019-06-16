package PrzygotowanieDanych;

import java.util.List;

public class PracownikDTO
{
	private int mId;
	private String mNazwa;
	private List<AbsencjaDTO> mListaAbsencji;
	private String mUrlopNalezny;

	public String getUrlopNalezny()
	{
		return mUrlopNalezny;
	}

	public void setUrlopNalezny(String pmUrlopNalezny)
	{
		mUrlopNalezny = pmUrlopNalezny;
	}

	public int getId()
	{
		return mId;
	}

	public void setId(int pmId)
	{
		mId = pmId;
	}

	public String getNazwa()
	{
		return mNazwa;
	}

	public void setNazwa(String pmNazwa)
	{
		mNazwa = pmNazwa;
	}

	public List<AbsencjaDTO> getListaAbsencji()
	{
		return mListaAbsencji;
	}

	public void setListaAbsencji(List<AbsencjaDTO> pmListaAbsencji)
	{
		mListaAbsencji = pmListaAbsencji;
	}

	public String toString()
	{
		return this.mNazwa;
	}

	public String ZapisDataSetu()
	{
		return "INSERT INTO Zestawienie (ID_tabeli, Pracownik, Urlop_Nalezny) VALUES (" + getId() + ",\"" + getNazwa()
				+ "\", 26)";
	}
}
