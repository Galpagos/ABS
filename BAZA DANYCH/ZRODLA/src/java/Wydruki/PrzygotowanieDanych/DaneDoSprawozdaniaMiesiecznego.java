package Wydruki.PrzygotowanieDanych;

import java.time.LocalDate;

import enums.SLRodzajeAbsencji;

import java.util.List;

import ProjektGlowny.commons.utils.Interval;

public class DaneDoSprawozdaniaMiesiecznego {
	public List<PracownikDTO> getListaPracownikow() {
		return mListaPracownikow;
	}

	public void setListaPracownikow(List<PracownikDTO> pmListaPracownikow) {
		mListaPracownikow = pmListaPracownikow;
	}

	public List<SLRodzajeAbsencji> getListaAbsencji() {
		return mListaAbsencji;
	}

	public void setListaAbsencji(List<SLRodzajeAbsencji> pmListaAbsencji) {
		mListaAbsencji = pmListaAbsencji;
	}

	public Interval getOkresSprawozdawczy() {
		return mOkresSprawozdawczy;
	}

	public void setOkresSprawozdawczy(Interval pmOkresSprawozdawczy) {
		mOkresSprawozdawczy = pmOkresSprawozdawczy;
	}

	public LocalDate getData() {
		return mData;
	}

	public DaneDoSprawozdaniaMiesiecznego setData(LocalDate pmData) {
		mData = pmData;
		return this;
	}

	private List<PracownikDTO> mListaPracownikow;
	private List<SLRodzajeAbsencji> mListaAbsencji;
	private Interval mOkresSprawozdawczy;
	private LocalDate mData;
}
