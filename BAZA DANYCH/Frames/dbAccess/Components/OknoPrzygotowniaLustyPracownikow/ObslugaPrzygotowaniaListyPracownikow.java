package dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import java.util.ArrayList;
import java.util.List;

import Grupy.GrupaDTO;
import Grupy.ObslugaGrup;
import Pracownik.ObslugaPracownka;
import PrzygotowanieDanych.PracownikDTO;

public class ObslugaPrzygotowaniaListyPracownikow
{
	OknoPrzygotowaniaListyPracownikow mOkno;
	RepositoryPrzygotowaniaListyPracownikow mRepo;
	ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();

	public ObslugaPrzygotowaniaListyPracownikow(OknoPrzygotowaniaListyPracownikow pmOknoPrzygotowaniaListyPracownikow)
	{
		mOkno = pmOknoPrzygotowaniaListyPracownikow;
		mRepo = new RepositoryPrzygotowaniaListyPracownikow();
	}

	public void wszyscyWPrawo()
	{
		List<PracownikDTO> lvListaLewa = mOkno.getListaLewa();
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();
		for (PracownikDTO lvIndeks : lvListaLewa)
		{
			lvListaPrawa.add(lvIndeks);
		}
		for (int lvIndeks = lvListaLewa.size() - 1; lvIndeks >= 0; lvIndeks--)
		{
			lvListaLewa.remove(lvIndeks);
		}
		mOkno.odswiezTabLewa();
		mOkno.odswiezTabPrawa();
	}

	public void wszyscyWLewo()
	{
		List<PracownikDTO> lvListaLewa = mOkno.getListaLewa();
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();
		for (PracownikDTO lvIndeks : lvListaPrawa)
		{
			lvListaLewa.add(lvIndeks);
		}
		for (int lvIndeks = lvListaPrawa.size() - 1; lvIndeks >= 0; lvIndeks--)
		{
			lvListaPrawa.remove(lvIndeks);
		}
		mOkno.odswiezTabLewa();
		mOkno.odswiezTabPrawa();
	}

	public void zasilTabele(GrupaDTO pmObject)
	{
		List<PracownikDTO> lvDane = new ArrayList<>();
		if (pmObject.getNazwa().equals("Wszyscy"))
		{
			lvDane = mObslugaPracownika.getListaWszystkichPracownikow();
		} else
		{
			lvDane = mObslugaPracownika.getListaPracownikowWGrupie(pmObject.getID());
		}

		mOkno.setListaLewa(lvDane);
	}

	public void dodajWPrawo()
	{
		List<PracownikDTO> lvListaLewa = mOkno.getListaLewa();
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();
		int[] lvLista = mOkno.getSelectionLewaInt();
		for (int lvIndeks : lvLista)
		{
			lvListaPrawa.add(mOkno.getListaLewa().get(lvIndeks));
		}
		for (int lvIndeks = lvLista.length - 1; lvIndeks >= 0; lvIndeks--)
		{
			lvListaLewa.remove(lvLista[lvIndeks]);
		}
		mOkno.odswiezTabLewa();
		mOkno.odswiezTabPrawa();
	}

	public void dodajWLewo()
	{
		List<PracownikDTO> lvListaLewa = mOkno.getListaLewa();
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();
		int[] lvLista = mOkno.getSelectionPrawaInt();
		for (int lvIndeks : lvLista)
		{
			lvListaLewa.add(mOkno.getListaPrawa().get(lvIndeks));
		}
		for (int lvIndeks = lvLista.length - 1; lvIndeks >= 0; lvIndeks--)
		{
			lvListaPrawa.remove(lvLista[lvIndeks]);
		}
		mOkno.odswiezTabLewa();
		mOkno.odswiezTabPrawa();
	}

	public Object[] pobierzGrupy()
	{
		List<GrupaDTO> lvListaCB = ObslugaGrup.getListaGrup();

		GrupaDTO lvWszyscy = new GrupaDTO();
		lvWszyscy.setNazwa("Wszyscy");
		lvListaCB.add(0, lvWszyscy);

		return lvListaCB.toArray();
	}
}
