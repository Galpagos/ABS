package Pracownik;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.utils.Interval;

import java.util.Date;
import java.util.List;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import Absencja.AbsencjaRepository;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import enums.EtatPracownika;
import enums.SLRodzajeAbsencji;
import enums.WalidacjeTwarde;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.absencje.ObslugaAbsencji;
import pl.home.components.frames.mainframes.OknoPracownika;
import pl.home.components.frames.parameters.OPracWejscie;

public class ObslugaPracownka {
	PracownikRepository mRepo = new PracownikRepository();
	AbsencjaRepository mRepoAbs = new AbsencjaRepository();
	ObslugaAbsencji mObslugaAbs = new ObslugaAbsencji();
	public void dodajNowegoPracownika() {
		String lvNazwa = JOptionPane.showInputDialog("Podaj nazwę pracownika: ");
		if (lvNazwa != null) {
			mRepo.dodajPracownika(lvNazwa);
			JOptionPane.showMessageDialog(null, "Dodano Pracownika " + lvNazwa, "Dodano Pracownika", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void usunPracownika(int pmId) {
		if (!WalidacjeTwarde.PotwierdzenieOperacjiUsuniecia())
			return;

		String lvNazwa = mRepo.getPracownikNazwa(pmId);
		JOptionPane.showMessageDialog(null, "Usunięto pracownika " + lvNazwa, "Usuwanie Pracownika", JOptionPane.INFORMATION_MESSAGE);
		mRepo.usunPracownika(pmId);
	}

	public void zwolnijPracownika(int pmId) {
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData != null)
			mRepo.zwolnijPracownika(pmId, lvData);
	}

	public void ustawDateUrodzeniaPracownikowi(int pmId, Date pmData) {
		mRepo.ustawDateUrodzenia(pmId, pmData);
	}

	public String getDataUrodzeniaTxt(PracownikDTO pmPracownik) {
		if (mRepo.getDataUrodzenia(pmPracownik.getId()) != null)
			return mRepo.getDataUrodzenia(pmPracownik.getId()).toString().split(" ")[0];
		return "";
	}

	public String getUrlopTxt(PracownikDTO pmPracownik) {
		Integer lvUrlop = mRepo.getUrlopNalezny(pmPracownik.getId());
		if (lvUrlop == null)
			return "";
		return String.valueOf(lvUrlop);
	}

	public int getUrlopNal(int pmPracownikID) {
		Integer lvDane = mRepo.getUrlopNalezny(pmPracownikID);
		if (lvDane == null)
			return 0;
		return lvDane;
	}

	public void ustawUrlopNalezny(int pmId, int pmWartosc) {
		mRepo.ustawUrlopNalezny(pmId, pmWartosc);
	}

	public void pokazPracownika(PracownikDTO pmPracownik) {
		OPracWejscie lvParams = OPracWejscie.builder().pracownik(pmPracownik).build();
		new OknoPracownika(lvParams);
	}

	public List<PracownikDTO> getListaNieobecnych(LocalDate pmDataObecnosci) {
		return mRepo.pobierzNieobecnych(pmDataObecnosci);
	}

	public void zatrudnijPracownika(int pmId) {
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return;
		AbsencjaDTO lvAbs = new AbsencjaDTO()//
				.setId(QueryBuilder.getNextId(AbsencjeColumns.ID_tabeli))//
				.setIdPracownika(pmId)//
				.setOkres(new Interval(mRepo.getDataZwolnienia(pmId), lvData))//
				.setProcent(SLEkwiwalentZaUrlop.PROCENT_0)//
				.setRodzaj(SLRodzajeAbsencji.BRAK_STOSUNKU_PRACY);
		mObslugaAbs.saveAbsence(lvAbs);
		mRepo.zwolnijPracownika(pmId, null);
	}

	public EtatPracownika getEtat(PracownikDTO pmPracownik) {
		if (pmPracownik.getEtat() != null)
			return pmPracownik.getEtat();
		return mRepo.getEtatPracownika(pmPracownik.getId());
	}
}
