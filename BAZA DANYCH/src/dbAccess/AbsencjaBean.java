package dbAccess;

import java.util.Date;

import Enums.SLRodzajeAbsencji;

public class AbsencjaBean {
	public final static String NazwaTabeli = "Absencje";
	public final static String kolumnaID = "ID_tabeli";
	public final static String kolumnaIdPracownika = "ID_pracownika";
	public final static String kolumnaOdKiedy = "Od_kiedy";
	public final static String kolumnaDoKiedy = "Do_kiedy";
	public final static String kolumnaRodzajAbsencji = "Rodzaj_absencji";
	private int mId;
	private int mIdPracownika;
	private Date mDataOd;
	private Date mDataDo;
	private SLRodzajeAbsencji mRodzajAbsencji;

	public void setId(int id) {
		mId = id;
	}

	public int getId() {
		return mId;
	}

	public void setIdPracownika(int id) {
		mIdPracownika = id;
	}

	public int getIdPracownika() {
		return mIdPracownika;
	}

	public void setDataOd(Date dataod) {
		mDataOd = dataod;
	}

	public Date getDataOd() {
		return mDataOd;
	}

	public void setDataDo(Date datado) {
		mDataDo = datado;
	}

	public Date getDataDo() {
		return mDataDo;
	}

	public static String GetNazwaTabeli() {
		return NazwaTabeli;
	}

	public SLRodzajeAbsencji getRodzajAbsencji() {
		return mRodzajAbsencji;
	}

	public void setRodzajAbsencji(SLRodzajeAbsencji pmRodzajAbsencji) {
		mRodzajAbsencji = pmRodzajAbsencji;
	}

	public static String GetNazwaKolumnyId() {
		return kolumnaID;
	}

	public static String GetKolumnIdPracownika() {
		return kolumnaIdPracownika;
	}

	public static String GetKolumnOdKiedy() {
		return kolumnaOdKiedy;
	}

	public static String GetKolumnDoKiedy() {
		return kolumnaDoKiedy;
	}

	public static String GetKolumnRodzajAbsencji() {
		return kolumnaRodzajAbsencji;
	}

	public String GetRodzajAbsencji_txt() {
		return mRodzajAbsencji.toString();
	}

}