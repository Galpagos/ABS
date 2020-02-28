package Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import java.util.ArrayList;
import java.util.List;

import Grupy.GrupaDTO;
import Grupy.ObslugaGrup;
import Pracownik.PracownikRepository;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import pl.home.components.frames.src.SrcOknoPrzygotowaniaListyPracownikow;

public class ObslugaPrzygotowaniaListyPracownikow {
	private SrcOknoPrzygotowaniaListyPracownikow mOkno;
	private PracownikRepository mRepoPracownik = new PracownikRepository();

	private ObslugaGrup mObslugaGrup = new ObslugaGrup();

	public ObslugaPrzygotowaniaListyPracownikow(
			SrcOknoPrzygotowaniaListyPracownikow pmOknoPrzygotowaniaListyPracownikow) {
		mOkno = pmOknoPrzygotowaniaListyPracownikow;
	}

	public void wszyscyWPrawo() {
		List<PracownikDTO> lvListaLewa = mOkno.getListaLewa();
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();
		for (PracownikDTO lvIndeks : lvListaLewa) {
			lvListaPrawa.add(lvIndeks);
		}

		mOkno.odswiezTabPrawa();
	}

	public void wyczyscLewa() {
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();

		for (int lvIndeks = lvListaPrawa.size() - 1; lvIndeks >= 0; lvIndeks--) {
			lvListaPrawa.remove(lvIndeks);
		}
		mOkno.odswiezTabPrawa();
	}

	public void zasilTabele(GrupaDTO pmObject) {
		List<PracownikDTO> lvDane = new ArrayList<>();
		if (pmObject.getNazwa().equals("Wszyscy")) {
			lvDane = mRepoPracownik.getListaWszystkichPracownikow();
		} else {
			lvDane = mRepoPracownik.getListaPracownikowWGrupie(pmObject.getID());
		}

		mOkno.setListaLewa(lvDane);
		mOkno.odswiezTabLewa();
	}

	public void dodajWPrawo() {
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();
		int[] lvLista = mOkno.getSelectionLewaInt();
		for (int lvIndeks : lvLista) {
			lvListaPrawa.add(mOkno.getListaLewa().get(lvIndeks));
		}

		mOkno.odswiezTabPrawa();
	}

	public void usunZPrawa() {
		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();
		int[] lvLista = mOkno.getSelectionPrawaInt();

		for (int lvIndeks = lvLista.length - 1; lvIndeks >= 0; lvIndeks--) {
			lvListaPrawa.remove(lvLista[lvIndeks]);
		}

		mOkno.odswiezTabPrawa();
	}

	public GrupaDTO[] pobierzGrupy() {
		List<GrupaDTO> lvListaCB = mObslugaGrup.getListaGrup();

		GrupaDTO lvWszyscy = new GrupaDTO();
		lvWszyscy.setNazwa("Wszyscy");
		lvListaCB.add(0, lvWszyscy);

		return lvListaCB.toArray(new GrupaDTO[0]);
	}

	public void dodajPrzerwe() {

		List<PracownikDTO> lvListaPrawa = mOkno.getListaPrawa();

		lvListaPrawa.add(new PracownikDTO().setNazwa("----------"));

		mOkno.odswiezTabPrawa();
	}
}
