package PrzygotowanieDanych;

import java.util.List;

public class PracownikDTO
{
	private int mId;
	private String mNazwa;
	private List<AbsencjaDTO> mListaAbsencji;

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
}
