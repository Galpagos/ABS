package Wydruki.PrzygotowanieDanych;

import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.Do_kiedy;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.EKWIWALENT;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.ID_pracownika;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.ID_ABS;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.Od_kiedy;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.RODZAJ;

import ProjektGlowny.commons.DbBuilder.LRecord;
import ProjektGlowny.commons.utils.Interval;

import java.time.LocalDate;

import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;
import enums.SLRodzajeAbsencji;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
public class AbsencjaDTO {
	private Interval mOkres;
	private SLRodzajeAbsencji mRodzaj;
	private int mId;
	private int mIdPracownika;
	private SLEkwiwalentZaUrlop mProcent;

	public AbsencjaDTO(AbsencjaDTO pmAbsencja) {
		mOkres = pmAbsencja.getOkres();
		mRodzaj = pmAbsencja.getRodzaj();
		mId = pmAbsencja.getId();
		mIdPracownika = pmAbsencja.getIdPracownika();
		mProcent = pmAbsencja.getProcent();
	}

	public AbsencjaDTO() {
	}

	public Interval getOkres() {
		return mOkres;
	}

	public LocalDate getStart() {
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

	@Override
	public String toString() {
		return mRodzaj.toString();
	}

	public static AbsencjaDTO builder() {

		return new AbsencjaDTO();
	}

	public SLEkwiwalentZaUrlop getProcent() {
		if (mProcent != null)
			return mProcent;
		return mRodzaj.DEFAULT_PROCENT();

	}

	public AbsencjaDTO setProcent(SLEkwiwalentZaUrlop pmProcent) {
		mProcent = pmProcent;
		return this;
	}

	public static AbsencjaDTO parsuj(LRecord pmRecord) {
		return new AbsencjaDTO()//
				.setId(pmRecord.getAsInteger(ID_ABS))//
				.setIdPracownika(pmRecord.getAsInteger(ID_pracownika))//
				.setNazwaPracownika(pmRecord.getAsString(ZestawienieColumns.Pracownik))//
				.setOkres(new Interval(pmRecord.getAsLocalDate(Od_kiedy), pmRecord.getAsLocalDate(Do_kiedy)))//
				.setProcent(SLEkwiwalentZaUrlop.getByKod(pmRecord.getAsString(EKWIWALENT)))//
				.setRodzaj(SLRodzajeAbsencji.getByKod(pmRecord.getAsString(RODZAJ)));

	}
}
