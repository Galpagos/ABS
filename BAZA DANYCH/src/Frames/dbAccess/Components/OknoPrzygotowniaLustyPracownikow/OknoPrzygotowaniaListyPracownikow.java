package Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import Frames.dbAccess.Components.ScriptParams;
import Grupy.GrupaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public class OknoPrzygotowaniaListyPracownikow extends SrcOknoPrzygotowaniaListyPracownikow
		implements InterfejsPrzygotowaniaListyPracownikow {

	private static final long serialVersionUID = -5117051096105142242L;
	ObslugaPrzygotowaniaListyPracownikow mObsluga;

	public OknoPrzygotowaniaListyPracownikow(ScriptParams pmParams) {
		super(pmParams);
	}

	@Override
	void wszyscyWPrawo() {
		mObsluga.wszyscyWPrawo();
	}

	@Override
	protected void jedenwPrawo() {
		mObsluga.dodajWPrawo();
	}

	@Override
	protected void usunZPrawo() {
		mObsluga.usunZPrawa();
	}

	@Override
	protected void wyczyscLewa() {
		mObsluga.wyczyscLewa();
	}

	@Override
	protected void dodajPrzerwe() {
		mObsluga.dodajPrzerwe();
	}

	@Override
	protected void wyborGRupy() {
		mObsluga.zasilTabele((GrupaDTO) cbWyborGrupy.getSelectedItem());
		odswiezKontrolki();
	}

	@Override
	protected void cancel() {
		mListaPrawa = new ArrayList<PracownikDTO>();
		dispose();

	}

	@Override
	protected void budujOkno() {
		super.budujOkno();
		mObsluga = new ObslugaPrzygotowaniaListyPracownikow(this);
		init();
	}

	private void init() {
		GrupaDTO lvStart = new GrupaDTO();
		lvStart.setNazwa("Wszyscy");
		mObsluga.zasilTabele(lvStart);
		odswiezKontrolki();
	}

	@Override
	protected void odswiezKontrolki() {
		super.odswiezKontrolki();
		cbWyborGrupy.setModel(new DefaultComboBoxModel<GrupaDTO>(mObsluga.pobierzGrupy()));
	}

}
