package Wydruki.PrzygotowanieDanych;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import Enums.SLRodzajeAbsencji;

public class AbsencjaDTO {
	private Interval mOkres;
	private SLRodzajeAbsencji mRodzaj;
	private int mId;
	private int mIdPracownika;

	public Interval getOkres() {
		return mOkres;
	}

	public DateTime getStart() {
		return getOkres().getStart();
	}

	public AbsencjaDTO setOkres(Interval pmOkres) {
		mOkres = pmOkres;
		return this;
	}

	public SLRodzajeAbsencji getRodzaj() {
		return mRodzaj;
	}

	public AbsencjaDTO setRodzaj(SLRodzajeAbsencji pmRodzaj) {
		mRodzaj = pmRodzaj;
		return this;
	}

	public int getId() {
		return mId;
	}

	public AbsencjaDTO setId(int pmId) {
		mId = pmId;
		return this;
	}

	public int getIdPracownika() {
		return mIdPracownika;
	}

	public AbsencjaDTO setIdPracownika(int pmIdPracownika) {
		mIdPracownika = pmIdPracownika;
		return this;
	}

	public String getNazwaPracownika() {
		return mNazwaPracownika;
	}

	public AbsencjaDTO setNazwaPracownika(String pmNazwaPracownika) {
		mNazwaPracownika = pmNazwaPracownika;
		return this;
	}

	private String mNazwaPracownika;

	public String toString() {
		return mRodzaj.toString();
	}

	public static AbsencjaDTO builder() {

		return new AbsencjaDTO();
	}
}
