package Wydruki.PrzygotowanieDanych;

import java.time.LocalDate;
import java.util.List;

public class PracownikDTO {
	private int mId;
	private String mNazwa;
	private List<AbsencjaDTO> mListaAbsencji;
	private String mUrlopNalezny;
	private LocalDate mDataZwolnienia;

	public String getUrlopNalezny() {
		return mUrlopNalezny;
	}

	public void setUrlopNalezny(String pmUrlopNalezny) {
		mUrlopNalezny = pmUrlopNalezny;
	}

	public int getId() {
		return mId;
	}

	public PracownikDTO setId(int pmId) {
		mId = pmId;
		return this;
	}

	public String getNazwa() {
		return mNazwa;
	}

	public PracownikDTO setNazwa(String pmNazwa) {
		mNazwa = pmNazwa;
		return this;
	}

	public List<AbsencjaDTO> getListaAbsencji() {
		return mListaAbsencji;
	}

	public PracownikDTO setListaAbsencji(List<AbsencjaDTO> pmListaAbsencji) {
		mListaAbsencji = pmListaAbsencji;
		return this;
	}

	@Override
	public String toString() {
		return this.mNazwa;
	}

	public LocalDate getDataZwolnienia() {
		return mDataZwolnienia;
	}

	public PracownikDTO setDataZwolnienia(LocalDate pmDataZwolnienia) {
		mDataZwolnienia = pmDataZwolnienia;
		return this;
	}
}
