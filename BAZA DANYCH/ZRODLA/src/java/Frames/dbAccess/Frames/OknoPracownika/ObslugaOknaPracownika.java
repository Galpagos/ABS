package Frames.dbAccess.Frames.OknoPracownika;

import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.Frames.AskIntParams;
import ProjektGlowny.commons.utils.Interval;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import Frames.dbAccess.Components.ComboPicker;
import Grupy.GrupaDTO;
import Grupy.ObslugaGrup;
import Pracownik.ObslugaPracownka;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import enums.InformacjeUniwersalne;
import enums.PytanieOWartosc;
import enums.SLRodzajeAbsencji;
import enums.WalidacjeTwarde;
import pl.home.absencje.AbsencjeRepositoryDB;
import pl.home.absencje.ObslugaAbsencji;
import pl.home.absencje.WalidatorAbsencjiFactory;
import pl.home.components.frames.mainframes.DanePracownika;
import pl.home.components.frames.mainframes.OknoAbsencji;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.parameters.ODanePracownikaWejscie;

public class ObslugaOknaPracownika {
	private InterfejsOknaPracownika mOkno;

	private ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();
	private ObslugaGrup mObslugaGrup = new ObslugaGrup();
	private ObslugaAbsencji mObslugaAbs = new ObslugaAbsencji();
	private AbsencjeRepositoryDB mRepoAbs = new AbsencjeRepositoryDB();

	public ObslugaOknaPracownika(InterfejsOknaPracownika pmOknoPracownika) {
		mOkno = pmOknoPracownika;
	}

	public void DodajAbsencje() {

		AbsencjaDTO lvAbsencja = new AbsencjaDTO();
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji.urlop_wypoczynkowy;
		lvAbsencja.setId(QueryBuilder.getNextID(AbsencjeColumns.ID_tabeli));
		lvAbsencja.setIdPracownika(mOkno.getPracownika().getId());
		lvAbsencja.setOkres(new Interval(new Date(), new Date()));
		lvAbsencja.setRodzaj(lvRodzajAbs);

		OAbsencjiWejscie lvParams = OAbsencjiWejscie.builder().withAbsencja(lvAbsencja).withListaPracownikow(Collections.emptyList()).build();
		new OknoAbsencji(lvParams).get();
	}

	public void modyfikujAbsencje() {
		if (mOkno.getAbsencjeZTabeli().isEmpty())
			return;
		if (mOkno.getAbsencjeZTabeli().size() == 1) {
			OAbsencjiWejscie lvParams = OAbsencjiWejscie//
					.builder()//
					.withAbsencja(mOkno.getAbsencjeZTabeli().get(0))//
					.withListaPracownikow(Collections.emptyList())//
					.build();
			new OknoAbsencji(lvParams);
		} else {
			OAbsencjiWejscie lvParams = OAbsencjiWejscie//
					.builder()//
					.withCzyTylkoRodzaj(true)//
					.withAbsencja(mOkno.getAbsencjeZTabeli().get(0))//
					.withListaPracownikow(Collections.emptyList())//
					.build();
			AbsencjaDTO lvNowaAbs = new OknoAbsencji(lvParams).get().getAbsencja();

			List<AbsencjaDTO> lvZmienianeAbsencje = mOkno.getAbsencjeZTabeli();
			lvZmienianeAbsencje.forEach(lvAbs -> lvAbs.setProcent(lvNowaAbs.getProcent()).setRodzaj(lvNowaAbs.getRodzaj()));

			String lvWalidacja = WalidatorAbsencjiFactory.INSTANCE.create().waliduj(lvZmienianeAbsencje);
			if (!lvWalidacja.isEmpty()) {
				mOkno.err(WalidacjeTwarde.utworz(lvWalidacja, true));
				return;
			}
			lvZmienianeAbsencje.forEach(lvAbs -> //
			{
				mRepoAbs.deleteAbsence(lvAbs.getId());
				mRepoAbs.saveAbsence(lvAbs);
			});
		}

	}

	public void UsunAbsencje() {
		if (!mOkno.ask(InformacjeUniwersalne.POTWIERDZENIE_USUNIECIA))
			return;

		for (AbsencjaDTO lvAbs : mOkno.getAbsencjeZTabeli()) {
			int lvIdAbsencji = lvAbs.getId();
			mObslugaAbs.deleteAbsence(lvIdAbsencji);
		}

	}

	public String grupyPracownika() {
		return "Należy do grup: " + mObslugaGrup.getGrupyPracownikaText(mOkno.getPracownika().getId());
	}

	public void przypiszGrupe() {

		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null) {
			mObslugaGrup.ustawGrupePracownikowi(mOkno.getPracownika().getId(), lvGrupa);
		}

	}

	public void usunGrupe() {
		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null) {
			mObslugaGrup.usunGrupePracownikowi(mOkno.getPracownika().getId(), lvGrupa);
		}
	}

	public void ustawDateUrodzenia() {

		String lvUrlop = mOkno.askString(PytanieOWartosc.PODAJ_DATE);
		if (lvUrlop == null)
			return;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date lvData = null;
			lvData = sdf.parse(lvUrlop);
			mObslugaPracownika.ustawDateUrodzeniaPracownikowi(mOkno.getPracownika().getId(), lvData);
		} catch (ParseException e) {
			mOkno.err(WalidacjeTwarde.NiewlasciwyFormatDaty);
		}
	}

	public String getDataUrodzenia(PracownikDTO pmPracownik) {
		return mObslugaPracownika.getDataUrodzeniaTxt(pmPracownik);

	}

	public String getUrlop(PracownikDTO pmPracownik) {
		return mObslugaPracownika.getUrlopTxt(pmPracownik);
	}

	public void ustawUrlopNalezny() {
		AskIntParams lvParams = AskIntParams//
				.builder()//
				.defaultValue(mObslugaPracownika.getUrlopNal(mOkno.getPracownika().getId()))//
				.maxValue(99)//
				.build();

		Integer lvUrlop = mOkno.askInt(lvParams, PytanieOWartosc.PODAJ_LICZBE);
		if (lvUrlop != null)
			mObslugaPracownika.ustawUrlopNalezny(mOkno.getPracownika().getId(), lvUrlop);
	}

	public String getEtat(PracownikDTO pmPracownik) {
		if (pmPracownik.getEtat() != null)
			return pmPracownik.getEtat().getOpis();
		return mObslugaPracownika.getEtat(pmPracownik).getOpis();
	}

	public PracownikDTO ustawEtat() {
		return new DanePracownika(ODanePracownikaWejscie.builder().pracownik(mOkno.getPracownika()).build()).get().pracownik();

	}
}
