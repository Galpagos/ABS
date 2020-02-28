package Frames.dbAccess.Frames.OknoGlowne;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import Enums.Komunikat;
import Frames.dbAccess.Components.DatePicker;
import Pracownik.ObslugaPracownka;
import Pracownik.PracownikRepository;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import pl.home.DniWolne.DniWolneRepository;
import pl.home.DniWolne.DzienWolnyDTO;
import pl.home.components.frames.mainframes.OknoSprawozdan;
import pl.home.components.frames.parameters.OSprawozdanWejscie;

public class ObslugaOknaGlownego {
	InterfejsOknaGlownego mOkno;
	RepositoryOknaGlownego mRepo;
	ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();
	DniWolneRepository mRepoDniWolne = new DniWolneRepository();
	PracownikRepository mRepoPracownika = new PracownikRepository();

	public ObslugaOknaGlownego(InterfejsOknaGlownego pmSrcOknoGlowne) {
		mOkno = pmSrcOknoGlowne;
		mRepo = new RepositoryOknaGlownego();
	}

	public void pokazPracownika() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			Komunikat.oknoGlowneBrakZaznaczeniaWTabeli.pokaz();
		} else {
			mObslugaPracownika.pokazPracownika(mOkno.getPracownikZTabeli());
		}
	}

	public void dodajPracownika() {
		mObslugaPracownika.dodajNowegoPracownika();
		mOkno.odswiezTabele();
		mOkno.odswiezKontrolki();
	}

	public void usunPracownika() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			Komunikat.oknoGlowneBrakZaznaczeniaWTabeli.pokaz();
		} else {
			mObslugaPracownika.usunPracownika(mOkno.getPracownikZTabeli().getId());
			mOkno.odswiezTabele();
			mOkno.odswiezKontrolki();
		}
	}

	public void pokazNieobecnych() {
		String lvNieobecni = "";
		LocalDate lvNaKiedy = new DatePicker().setPickedLocalDate();
		if (lvNaKiedy == null)
			return;
		List<PracownikDTO> lvDane = mRepoPracownika.pobierzNieobecnych(lvNaKiedy);

		if (lvDane.isEmpty())
			JOptionPane.showMessageDialog(null, "Brak nieobecnoœci na dzieñ: " + lvNaKiedy, //
					"Nieobecni dnia :" + lvNaKiedy, JOptionPane.INFORMATION_MESSAGE);
		else {
			for (PracownikDTO lvPracownik : lvDane)
				lvNieobecni = lvNieobecni + lvPracownik.getNazwa() + " od "
						+ lvPracownik.getListaAbsencji().get(0).getOkres().getStart() + " do "
						+ lvPracownik.getListaAbsencji().get(0).getOkres().getEnd() + " z powodu "
						+ lvPracownik.getListaAbsencji().get(0).getRodzaj().getNazwa() + "\n";

			JOptionPane.showMessageDialog(null, lvNieobecni, "Nieobecni dnia:" + lvNaKiedy,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void dodajDzienWolny() {
		DzienWolnyDTO lvDzienWolny = new DzienWolnyDTO();
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return;
		else {
			if (DayOfWeek.SUNDAY.equals(lvData.getDayOfWeek()) || DayOfWeek.SATURDAY.equals(lvData.getDayOfWeek())) {
				JOptionPane.showMessageDialog(null, "Nie mo¿na dodaæ dnia wolnego w weekend!", "B³¹d",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else
				lvDzienWolny.setData(lvData);
		}

		lvDzienWolny.setOpis(JOptionPane.showInputDialog("Podaj powód dnia wolnego: "));
		if (lvDzienWolny.getOpis() == null)
			return;
		else
			mRepo.zapiszNowyDzienWolny(lvDzienWolny);

		JOptionPane.showMessageDialog(null, "Dnia: " + lvDzienWolny.getData() + " z powodu " + lvDzienWolny.getOpis(),
				"Dodano dzieñ wolny", JOptionPane.INFORMATION_MESSAGE);

	}

	public void pokazDniWolne() {
		List<DzienWolnyDTO> lvDane = mRepoDniWolne.pobierzOstatnieDniWolne();
		String lvWynik = "";
		for (DzienWolnyDTO lvDzien : lvDane)
			lvWynik = lvWynik + lvDzien.getData() + " z powodu " + lvDzien.getOpis() + "\n";
		lvWynik = lvWynik.replaceAll("00:00:00.0", "");

		JOptionPane.showMessageDialog(null, "Ostatnio wprowadzone 20 dni: \n" + lvWynik, "Dni Wolne",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public void sprawozdanie() {
		new OknoSprawozdan(OSprawozdanWejscie.builder().build());
	}

	public void zwolnijPracownika() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			JOptionPane.showMessageDialog(null, "Wybierz pracownika!", "Ostrze¿enie", JOptionPane.WARNING_MESSAGE);
		} else {
			mObslugaPracownika.zwolnijPracownika(mOkno.getPracownikZTabeli().getId());
			mOkno.odswiezTabele();
			mOkno.odswiezKontrolki();
		}
	}

	public void zatrudnijPracownika() {
		mObslugaPracownika.zatrudnijPracownika(mOkno.getPracownikZTabeli().getId());
		mOkno.odswiezTabele();
		mOkno.odswiezKontrolki();
	}

	public PracownikDTO getPracownik(Integer pmIdPracownika) {

		return mRepoPracownika.getPracownik(pmIdPracownika);
	}
}
