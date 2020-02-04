package pl.home.ListaPlac;

import java.math.BigDecimal;

public enum SLEkwiwalentZaUrlop {

	PROCENT_0(0, BigDecimal.valueOf(0)), //
	PROCENT_100(100, BigDecimal.valueOf(1)), //
	PROCENT_80(80, BigDecimal.valueOf(0.8)), //
	PROCENT_60(60, BigDecimal.valueOf(0.6)), //
	PROCENT_90(90, BigDecimal.valueOf(0.9)), //
	PROCENT_75(75, BigDecimal.valueOf(0.75));

	Integer mKod;
	BigDecimal mProcent;

	SLEkwiwalentZaUrlop(Integer pmKod, BigDecimal pmProcent) {
		mKod = pmKod;
		mProcent = pmProcent;

	}

	public Integer getKod() {
		return mKod;
	}

	public String getKodString() {
		return mKod.toString();
	}

	public BigDecimal getProcent() {
		return mProcent;
	}

	public static SLEkwiwalentZaUrlop getByKod(String pmNazwa) {
		for (SLEkwiwalentZaUrlop e : SLEkwiwalentZaUrlop.values()) {
			if (e.getKodString().equalsIgnoreCase(pmNazwa)) {
				return e;
			}
		}
		System.out.println("Nie znaleziono Ekwiwalentu dla urlopu");
		return PROCENT_100;

	}
}
