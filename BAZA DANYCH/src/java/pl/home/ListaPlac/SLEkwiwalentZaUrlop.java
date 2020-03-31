package pl.home.ListaPlac;

import java.math.BigDecimal;

import Enums.InterfejsSlownika;

public enum SLEkwiwalentZaUrlop implements InterfejsSlownika {

	PROCENT_0("0", BigDecimal.valueOf(0)), //
	PROCENT_100("100", BigDecimal.valueOf(1)), //
	PROCENT_80("80", BigDecimal.valueOf(0.8)), //
	PROCENT_60("60", BigDecimal.valueOf(0.6)), //
	PROCENT_90("90", BigDecimal.valueOf(0.9)), //
	PROCENT_75("75", BigDecimal.valueOf(0.75));

	String mKod;
	BigDecimal mProcent;

	SLEkwiwalentZaUrlop(String pmKod, BigDecimal pmProcent) {
		mKod = pmKod;
		mProcent = pmProcent;

	}

	@Override
	public String getKod() {
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
			if (e.getKod().equalsIgnoreCase(pmNazwa)) {
				return e;
			}
		}
		System.out.println("Nie znaleziono Ekwiwalentu dla urlopu");
		return PROCENT_100;

	}

	@Override
	public String getNazwaByKod(String pmKod) {

		return getByKod(pmKod).getKod();
	}

	@Override
	public Object getOpis() {

		return mKod + "%";
	}
}
