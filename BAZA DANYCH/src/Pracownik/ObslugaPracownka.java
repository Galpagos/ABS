package Pracownik;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import Enums.Komunikat;
import Frames.dbAccess.Components.DatePicker;
import Frames.dbAccess.Components.ScriptParams;
import Frames.dbAccess.Frames.OknoPracownika.OknoPracownika;
import Frames.dbAccess.Frames.OknoPracownika.OknoPracownikaParams;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public class ObslugaPracownka {
	PracownikRepository mRepo = new PracownikRepository();

	public void dodajNowegoPracownika() {
		String lvNazwa = JOptionPane.showInputDialog("Podaj nazw� pracownika: ");
		if (lvNazwa != null) {
			mRepo.dodajPracownika(lvNazwa);
			JOptionPane.showMessageDialog(null, "Dodano Pracownika " + lvNazwa, "Dodano Pracownika",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void usunPracownika(int pmId) {
		if (!Komunikat.PotwierdzenieOperacjiUsuniecia())
			return;

		String lvNazwa = mRepo.getPracownikNazwa(pmId);
		JOptionPane.showMessageDialog(null, "Usunieto pracownika " + lvNazwa, "Usuwanie Pracownika",
				JOptionPane.INFORMATION_MESSAGE);
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
		return mRepo.getDataUrodzenia(pmPracownik.getId()).toString().split(" ")[0];
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
		ScriptParams lvParams = new ScriptParams();
		lvParams.add(OknoPracownikaParams.PRACOWNIK_DTO, pmPracownik);
		new OknoPracownika(lvParams);
	}

	public List<PracownikDTO> getListaNieobecnych(LocalDate pmDataObecnosci) {
		return mRepo.pobierzNieobecnych(pmDataObecnosci);
	}
}
