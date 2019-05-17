package PrzygotowanieDanych;

import java.util.List;

import org.joda.time.Interval;

import Enums.SLRodzajeAbsencji;

public class DaneDoSprawozdaniaMiesiecznego
{
	public List<PracownikDTO> getListaPracownikow()
	{
		return mListaPracownikow;
	}

	public void setListaPracownikow(List<PracownikDTO> pmListaPracownikow)
	{
		mListaPracownikow = pmListaPracownikow;
	}

	public List<SLRodzajeAbsencji> getListaAbsencji()
	{
		return mListaAbsencji;
	}

	public void setListaAbsencji(List<SLRodzajeAbsencji> pmListaAbsencji)
	{
		mListaAbsencji = pmListaAbsencji;
	}

	public Interval getOkresSprawozdawczy()
	{
		return mOkresSprawozdawczy;
	}

	public void setOkresSprawozdawczy(Interval pmOkresSprawozdawczy)
	{
		mOkresSprawozdawczy = pmOkresSprawozdawczy;
	}

	private List<PracownikDTO> mListaPracownikow;
	private List<SLRodzajeAbsencji> mListaAbsencji;
	private Interval mOkresSprawozdawczy;
}
