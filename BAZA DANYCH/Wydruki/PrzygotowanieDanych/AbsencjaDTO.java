package PrzygotowanieDanych;

import org.joda.time.Interval;

import Enums.SLRodzajeAbsencji;

public class AbsencjaDTO
{
	private Interval mOkres;
	private SLRodzajeAbsencji mRodzaj;
	private int mId;
	private int mIdPracownika;

	public Interval getOkres()
	{
		return mOkres;
	}

	public void setOkres(Interval pmOkres)
	{
		mOkres = pmOkres;
	}

	public SLRodzajeAbsencji getRodzaj()
	{
		return mRodzaj;
	}

	public void setRodzaj(SLRodzajeAbsencji pmRodzaj)
	{
		mRodzaj = pmRodzaj;
	}

	public int getId()
	{
		return mId;
	}

	public void setId(int pmId)
	{
		mId = pmId;
	}

	public int getIdPracownika()
	{
		return mIdPracownika;
	}

	public void setIdPracownika(int pmIdPracownika)
	{
		mIdPracownika = pmIdPracownika;
	}

	public String getNazwaPracownika()
	{
		return mNazwaPracownika;
	}

	public void setNazwaPracownika(String pmNazwaPracownika)
	{
		mNazwaPracownika = pmNazwaPracownika;
	}

	private String mNazwaPracownika;
}
