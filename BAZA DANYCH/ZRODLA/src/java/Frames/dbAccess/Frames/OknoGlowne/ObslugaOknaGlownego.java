package Frames.dbAccess.Frames.OknoGlowne;

import ProjektGlowny.commons.utils.Interval;

import java.util.Date;
import java.util.List;

import java.time.DayOfWeek;
import java.time.LocalDate;

import Pracownik.ObslugaPracownka;
import Pracownik.PracownikRepository;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import enums.Informacje;
import enums.PytanieOWartosc;
import enums.SLRodzajeAbsencji;
import enums.WalidacjeTwarde;
import pl.home.DniWolne.DniWolneRepository;
import pl.home.DniWolne.DzienWolnyDTO;
import pl.home.components.frames.mainframes.OknoAbsencji;
import pl.home.components.frames.mainframes.OknoSprawozdan;
import pl.home.components.frames.mainframes.OneDayView;
import pl.home.components.frames.mainframes.OneMonthView;
import pl.home.components.frames.mainframes.SaturdayView;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.parameters.OSprawozdanWejscie;

public class ObslugaOknaGlownego {
	private InterfejsOknaGlownego mOkno;
	private RepositoryOknaGlownego mRepo;
	private ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();
	private DniWolneRepository mRepoDniWolne = new DniWolneRepository();
	private PracownikRepository mRepoPracownika = new PracownikRepository();

	public ObslugaOknaGlownego(InterfejsOknaGlownego pmSrcOknoGlowne) {
		mOkno = pmSrcOknoGlowne;
		mRepo = new RepositoryOknaGlownego();
	}

	public void pokazPracownika() {
		mObslugaPracownika.pokazPracownika(mOkno.getPracownikZTabeli());
	}

	public void dodajPracownika() {
		mObslugaPracownika.dodajNowegoPracownika();
		mOkno.odswiezTabele();
	}

	public void usunPracownika() {
		mObslugaPracownika.usunPracownika(mOkno.getPracownikZTabeli().getId());
		mOkno.odswiezTabele();
	}

	public void pokazNieobecnych() {
		LocalDate lvNaKiedy = mOkno.askLocalDate();
		if (lvNaKiedy == null)
			return;

		List<PracownikDTO> lvDane = mRepoPracownika.pobierzNieobecnych(lvNaKiedy);

		if (lvDane.isEmpty())
			mOkno.info(Informacje.BRAK_NIEOBECNOSCI_DNIA, String.valueOf(lvNaKiedy));
		else {
			String lvNieobecni = przygotujKomunikat(lvDane);
			mOkno.info(Informacje.NIEOBECNI_DNIA, String.valueOf(lvNaKiedy), lvNieobecni);
		}
	}

	private String przygotujKomunikat(List<PracownikDTO> lvDane) {
		StringBuilder lvNieobecni = new StringBuilder();
		for (PracownikDTO lvPracownik : lvDane)
			lvNieobecni.append(lvPracownik.getNazwa() + " od " + lvPracownik.getListaAbsencji().get(0).getOkres().getStart() + " do "
					+ lvPracownik.getListaAbsencji().get(0).getOkres().getEnd() + " z powodu " + lvPracownik.getListaAbsencji().get(0).getRodzaj().getNazwa()
					+ "\n");
		return lvNieobecni.toString();
	}

	public void dodajDzienWolny() {
		LocalDate lvData = mOkno.askLocalDate();
		if (lvData == null)
			return;

		if (DayOfWeek.SUNDAY.equals(lvData.getDayOfWeek()) || DayOfWeek.SATURDAY.equals(lvData.getDayOfWeek())) {
			mOkno.err(WalidacjeTwarde.DzienWolnyWWeekend);
			return;
		}
		DzienWolnyDTO lvDzienWolny = new DzienWolnyDTO();
		lvDzienWolny.setData(lvData);
		lvDzienWolny.setOpis(mOkno.askString(PytanieOWartosc.POWOD_DNIA_WOLNEGO));
		if (lvDzienWolny.getOpis() == null)
			return;
		else
			mRepo.zapiszNowyDzienWolny(lvDzienWolny);
		mOkno.info(Informacje.DODANO_DZIEN_WOLNY, lvDzienWolny.getData().toString(), lvDzienWolny.getOpis());

	}

	public void pokazDniWolne() {
		List<DzienWolnyDTO> lvDane = mRepoDniWolne.pobierzOstatnieDniWolne();
		String lvWynik = przygotujKomunikatDniWolnych(lvDane);
		mOkno.info(Informacje.DNI_WOLNE, lvWynik);
	}

	private String przygotujKomunikatDniWolnych(List<DzienWolnyDTO> lvDane) {
		StringBuilder lvWynik = new StringBuilder();
		for (DzienWolnyDTO lvDzien : lvDane)
			lvWynik.append(lvDzien.getData() + " z powodu " + lvDzien.getOpis() + "\n");
		return lvWynik.toString();
	}

	public void sprawozdanie() {
		new OknoSprawozdan(OSprawozdanWejscie.builder().build());
	}

	public void zwolnijPracownika() {
		mObslugaPracownika.zwolnijPracownika(mOkno.getPracownikZTabeli().getId());
		mOkno.odswiezTabele();
	}

	public void zatrudnijPracownika() {
		mObslugaPracownika.zatrudnijPracownika(mOkno.getPracownikZTabeli().getId());
		mOkno.odswiezTabele();
	}

	public PracownikDTO getPracownik(Integer pmIdPracownika) {

		return mRepoPracownika.getPracownik(pmIdPracownika);
	}

	public void dodajMasowaAbsencje() {
		AbsencjaDTO lvAbsencja = new AbsencjaDTO();
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji.urlop_wypoczynkowy;
		lvAbsencja.setOkres(new Interval(new Date(), new Date()));
		lvAbsencja.setRodzaj(lvRodzajAbs);

		OAbsencjiWejscie lvWejscie = OAbsencjiWejscie.builder().withAbsencja(lvAbsencja).withListaPracownikow(mOkno.getPracownicyZTabeli()).build();
		new OknoAbsencji(lvWejscie).get();
	}

	public void showOneDayView() {
		new OneDayView(null);
	}

	public void showOneMonthView() {
		new OneMonthView(null);
	}

	public void showSaturdayView() {
		new SaturdayView(null);
	}
}
