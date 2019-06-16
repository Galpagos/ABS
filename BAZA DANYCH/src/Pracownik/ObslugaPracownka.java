package Pracownik;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import Enums.Komunikat;
import PrzygotowanieDanych.PracownikDTO;
import dbAccess.Components.DatePicker;

public class ObslugaPracownka
{
	PracownikRepository mRepo = new PracownikRepository();

	public void dodajNowegoPracownika()
	{
		String lvNazwa = JOptionPane.showInputDialog("Podaj nazwê pracownika: ");
		if (lvNazwa != null)
		{
			mRepo.dodajPracownika(lvNazwa);
			JOptionPane.showMessageDialog(null, "Dodano Pracownika " + lvNazwa, "Dodano Pracownika",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void usunPracownika(int pmId)
	{
		if (!Komunikat.PotwierdzenieOperacjiUsuniecia())
			return;

		mRepo.usunPracownika(pmId);
	}

	public void zwolnijPracownika(int pmId)
	{
		Date lvData = new DatePicker().setPickedDate();
		if (lvData != null)
			mRepo.zwolnijPracownika(pmId, lvData);
	}

	public List<PracownikDTO> getListaWszystkichPracownikow()
	{
		List<PracownikDTO> lvDane = new ArrayList<PracownikDTO>();
		Object[][] lvPobrane = mRepo.getListaWszystkichPracownikow();

		for (Object[] lvRow : lvPobrane)
		{
			PracownikDTO lvPracownik = new PracownikDTO();
			lvPracownik.setId((int) lvRow[0]);
			lvPracownik.setNazwa((String) lvRow[1]);
			lvDane.add(lvPracownik);
		}
		return lvDane;
	}

	public List<PracownikDTO> getListaPracownikowWGrupie(int pmGrupaId)
	{
		List<PracownikDTO> lvDane = new ArrayList<PracownikDTO>();
		Object[][] lvPobrane = mRepo.getListaPracownikowWGrupie(pmGrupaId);

		for (Object[] lvRow : lvPobrane)
		{
			PracownikDTO lvPracownik = new PracownikDTO();
			lvPracownik.setId((int) lvRow[0]);
			lvPracownik.setNazwa((String) lvRow[1]);
			lvDane.add(lvPracownik);
		}
		return lvDane;
	}

	public void ustawDateUrodzeniaPracownikowi(int pmId, Date pmData)
	{
		mRepo.ustawDateUrodzenia(pmId, pmData);
	}

	public String getDataUrodzeniaTxt(PracownikDTO pmPracownik)
	{
		return getDataUrodzenia(pmPracownik).toString().split(" ")[0];
	}

	public Date getDataUrodzenia(PracownikDTO pmPracownik)
	{
		Object[][] lvDane = mRepo.getDataUrodzenia(pmPracownik.getId());
		if (lvDane[0][0] == null)
			return null;
		return (Date) lvDane[0][0];
	}

	public String getUrlop(PracownikDTO pmPracownik)
	{
		Object[][] lvDane = mRepo.getUrlopNalezny(pmPracownik.getId());
		if (lvDane[0][0] == null)
			return "";
		return lvDane[0][0].toString();
	}

	public void ustawUrlopNalezny(int pmId, int pmWartosc)
	{
		mRepo.ustawUrlopNalezny(pmId, pmWartosc);
	}
}
