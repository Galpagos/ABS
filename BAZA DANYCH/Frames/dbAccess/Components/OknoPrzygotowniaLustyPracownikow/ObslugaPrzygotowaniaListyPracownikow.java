package dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import java.util.ArrayList;
import java.util.List;

import Grupy.GrupaDTO;
import Grupy.ObslugaGrup;
import PrzygotowanieDanych.PracownikDTO;

public class ObslugaPrzygotowaniaListyPracownikow
{
	OknoPrzygotowaniaListyPracownikow mOkno;
	RepositoryPrzygotowaniaListyPracownikow mRepo;

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

	public void zasilTabele(Object pmObject)
	{
		List<PracownikDTO> lvDane = new ArrayList<PracownikDTO>();
		Object[][] lvPobrane = mRepo.getListaPracownikow(pmObject);

		for (Object[] lvRow : lvPobrane)
		{
			PracownikDTO lvPracownik = new PracownikDTO();
			lvPracownik.setId((int) lvRow[0]);
			lvPracownik.setNazwa((String) lvRow[1]);
			lvDane.add(lvPracownik);
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
		List<GrupaDTO> lvListaCB=ObslugaGrup.getListaGrup();
		GrupaDTO lvPusta=new GrupaDTO();
		lvPusta.setNazwa("");
		GrupaDTO lvWszyscy=new GrupaDTO();
		lvPusta.setNazwa("Wszyscy");
		
		lvListaCB.add(0, lvPusta);
		lvListaCB.add(0, lvWszyscy);
		return lvListaCB.toArray();
	}
}
