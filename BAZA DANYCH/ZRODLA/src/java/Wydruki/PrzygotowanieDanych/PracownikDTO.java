package Wydruki.PrzygotowanieDanych;

import java.util.List;
import java.util.Locale;

import java.math.BigDecimal;
import java.text.Collator;
import java.time.LocalDate;

import enums.EtatPracownika;

public class PracownikDTO implements Comparable<PracownikDTO> {
	private int mId;
	private String mNazwa;
	private List<AbsencjaDTO> mListaAbsencji;
	private String mUrlopNalezny;
	private LocalDate mDataZwolnienia;
	private EtatPracownika mEtat;

	public String getUrlopNalezny() {
		return mUrlopNalezny;
	}

	public void setUrlopNalezny(String pmUrlopNalezny) {
		mUrlopNalezny = pmUrlopNalezny;
	}

	public EtatPracownika getEtat() {
		return mEtat;
	}

	public void setEtat(EtatPracownika pmEtat) {
		mEtat = pmEtat;
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

	@Override
	public int compareTo(PracownikDTO pmO) {
		return Collator.getInstance(new Locale("pl", "PL")).compare(mNazwa, pmO.mNazwa);
	}

	public BigDecimal getEtatBD() {
		return BigDecimal.ONE.divide(new BigDecimal(mEtat.getKod()));
	}
}
