package Frames.dbAccess.Frames.OknoPracownika;

import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.utils.Interval;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import Absencja.ObslugaAbsencji;
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
import pl.home.components.frames.mainframes.OknoAbsencji;
import pl.home.components.frames.parameters.OAbsencjiWejscie;

public class ObslugaOknaPracownika {
	private InterfejsOknaPracownika mOkno;

	private ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();
	private ObslugaAbsencji mObslugaAbsencji = new ObslugaAbsencji();
	private ObslugaGrup mObslugaGrup = new ObslugaGrup();

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

		mObslugaAbsencji.modyfikujAbsencje(lvAbsencja);
	}

	public void ModyfikujAbsencje() {
		OAbsencjiWejscie lvParams = OAbsencjiWejscie.builder().withAbsencja(mOkno.getAbsencjeZTabeli()).build();
		new OknoAbsencji(lvParams);
	}

	public void UsunAbsencje() {
		if (!mOkno.ask(InformacjeUniwersalne.POTWIERDZENIE_USUNIECIA))
			return;

		int lvIdAbsencji = mOkno.getAbsencjeZTabeli().getId();
		mObslugaAbsencji.usunAbsencje(lvIdAbsencji, true);

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
		new JOptionPane();
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
		new JOptionPane();
		String lvUrlop = mOkno.askString(PytanieOWartosc.PODAJ_LICZBE);
		int lvWartosc;
		try {
			lvWartosc = Integer.parseInt(lvUrlop);
			mObslugaPracownika.ustawUrlopNalezny(mOkno.getPracownika().getId(), lvWartosc);

		} catch (NumberFormatException e) {
			mOkno.err(WalidacjeTwarde.NiewlasciwyFormatLiczby);
		}

	}
}
