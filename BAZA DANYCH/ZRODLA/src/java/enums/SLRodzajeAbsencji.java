package enums;

import ProjektGlowny.commons.enums.InterfejsSlownika;

import java.util.function.Function;

import java.awt.Color;

import Absencja.WalidatorRodzajuAbsencji;
import Absencja.WalidatorRodzajuAbsencjiFactory;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;

public enum SLRodzajeAbsencji implements InterfejsSlownika {
	urlop_wypoczynkowy(
			"uW",
			"Urlop wypoczynkowy",
			new Color(255, 165, 0),
			"1",
			SLEkwiwalentZaUrlop.PROCENT_100,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createForUrlopWypoczynkowy), //

	urlop_w_pracy(
			"uPR",
			"Urlop w pracy",
			new Color(254, 127, 156),
			"2",
			SLEkwiwalentZaUrlop.PROCENT_100,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createForUrlopWypoczynkowy), //
	L_4("L4", "L-4", new Color(9, 200, 18), "3", SLEkwiwalentZaUrlop.PROCENT_80, WalidatorRodzajuAbsencjiFactory.INSTANCE::createChorobowe), //
	opieka_na_dziecko(
			"oDZ",
			"Opieka na dziecko",
			new Color(9, 200, 18),
			"4",
			SLEkwiwalentZaUrlop.PROCENT_80,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createOpiekiNaDziecko), //
	opieka_na_kogos(
			"oK",
			"Opieka na kogoś",
			new Color(9, 200, 18),
			"5",
			SLEkwiwalentZaUrlop.PROCENT_80,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createOpiekiNaKogos), //
	wypadek("WYP", "Wypadek", new Color(9, 200, 18), "6", SLEkwiwalentZaUrlop.PROCENT_100, WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	szpital("SZ", "Szpital", new Color(9, 200, 18), "7", SLEkwiwalentZaUrlop.PROCENT_80, WalidatorRodzajuAbsencjiFactory.INSTANCE::createChorobowe), //
	ciaza("C", "Ciąża", new Color(9, 200, 18), "8", SLEkwiwalentZaUrlop.PROCENT_100, WalidatorRodzajuAbsencjiFactory.INSTANCE::createChorobowe), //
	urlop_ojcowski(
			"uOJ",
			"Urlop ojcowski",
			new Color(51, 51, 255),
			"9",
			SLEkwiwalentZaUrlop.PROCENT_100,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createForUrlopOjcowski), //
	urlop_macierzynski(
			"uM",
			"Urlop macierzyński",
			new Color(51, 51, 255),
			"10",
			SLEkwiwalentZaUrlop.PROCENT_80,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createForMacierzynski), //
	urlop_rodzicielski(
			"uR",
			"Urlop rodzicielski",
			new Color(51, 51, 255),
			"11",
			SLEkwiwalentZaUrlop.PROCENT_80,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createForRodzicielski), //
	urlop_okolicznosciowy(
			"uOK",
			"Urlop okolicznościowy",
			new Color(249, 216, 85),
			"12",
			SLEkwiwalentZaUrlop.PROCENT_100,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	NN("NN", "NN", new Color(255, 38, 38), "13", SLEkwiwalentZaUrlop.PROCENT_0, WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	NUN("NUN", "NUN", new Color(255, 38, 38), "14", SLEkwiwalentZaUrlop.PROCENT_0, WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	NZ("NZ", "NZ", new Color(255, 38, 38), "15", SLEkwiwalentZaUrlop.PROCENT_0, WalidatorRodzajuAbsencjiFactory.INSTANCE::createForNZ), //
	DW("DW", "DW", new Color(255, 255, 0), "16", SLEkwiwalentZaUrlop.PROCENT_0, WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	NB("NB", "NB", new Color(128, 0, 128), "17", SLEkwiwalentZaUrlop.PROCENT_0, WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	swiadczenie_rehab(
			"SwR",
			"świadczenie rehab.",
			new Color(155, 155, 15),
			"18",
			SLEkwiwalentZaUrlop.PROCENT_90,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	opieka2dni(
			"188",
			"Opieka 2 dni na dziecko art.188",
			new Color(0, 155, 155),
			"19",
			SLEkwiwalentZaUrlop.PROCENT_100,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	UB("UB", "UB", new Color(255, 38, 38), "20", SLEkwiwalentZaUrlop.PROCENT_0, WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	BRAK_STOSUNKU_PRACY("BSP", "Brak stosunku pracy", Color.BLACK, "21", SLEkwiwalentZaUrlop.PROCENT_0, WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty), //
	urlop_wychowawczy(
			"WCH",
			"Urlop wychowawczy",
			new Color(155, 155, 155),
			"22",
			SLEkwiwalentZaUrlop.PROCENT_0,
			WalidatorRodzajuAbsencjiFactory.INSTANCE::createEmpty); //

	private final String mSkrot;
	private final String mNazwa;
	private final Color mColor;
	private final String mKod;
	private final SLEkwiwalentZaUrlop mProcent;
	private Function<Integer, WalidatorRodzajuAbsencji> mWalidator;

	SLRodzajeAbsencji(//
			String pmSkrot, //
			String pmNazwa, //
			Color pmColor, //
			String pmKod, //
			SLEkwiwalentZaUrlop pmProcent, //
			Function<Integer, WalidatorRodzajuAbsencji> pmWalidator) {

		this.mNazwa = pmNazwa;
		mColor = pmColor;
		mSkrot = pmSkrot;
		mKod = pmKod;
		mProcent = pmProcent;
		mWalidator = pmWalidator;

	}

	public String getNazwa() {
		return mNazwa;
	}

	public Color getColor() {
		return mColor;
	}

	@Override
	public String toString() {
		return mNazwa;
	}

	public static SLRodzajeAbsencji AbsencjaPoNazwie(String pmNazwa) {
		for (SLRodzajeAbsencji e : SLRodzajeAbsencji.values()) {
			if (e.mNazwa.equalsIgnoreCase(pmNazwa)) {
				return e;
			}
		}
		return null;
	}

	public static SLRodzajeAbsencji getByKod(String pmKod) {
		for (SLRodzajeAbsencji e : SLRodzajeAbsencji.values()) {
			if (e.mKod.equalsIgnoreCase(pmKod)) {
				return e;
			}
		}
		return null;
	}

	public String getSkrot() {
		return mSkrot;
	}

	@Override
	public String getKod() {
		return mKod;
	}

	public SLEkwiwalentZaUrlop DEFAULT_PROCENT() {
		return mProcent;
	}

	public String getNazwaByKod(String pmKod) {

		return getByKod(pmKod).getNazwa();
	}

	@Override
	public Object getOpis() {

		return mNazwa;
	}

	public Function<Integer, WalidatorRodzajuAbsencji> getWalidatorFunction() {
		return mWalidator;
	}

	public WalidatorRodzajuAbsencji getWalidator() {
		return mWalidator.apply(null);
	}

}
