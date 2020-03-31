package pl.home.components.frames.mainframes;

import javax.swing.DefaultComboBoxModel;

import Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow.InterfejsPrzygotowaniaListyPracownikow;
import Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow.ObslugaPrzygotowaniaListyPracownikow;
import Grupy.GrupaDTO;
import pl.home.components.frames.parameters.OPrzygListyPracWejscie;
import pl.home.components.frames.parameters.OPrzygListyPracWyjscie;
import pl.home.components.frames.src.SrcOknoPrzygotowaniaListyPracownikow;

public class OknoPrzygotowaniaListyPracownikow extends SrcOknoPrzygotowaniaListyPracownikow
		implements InterfejsPrzygotowaniaListyPracownikow {

	private static final long serialVersionUID = -5117051096105142242L;
	ObslugaPrzygotowaniaListyPracownikow mObsluga;

	public OknoPrzygotowaniaListyPracownikow(OPrzygListyPracWejscie pmParams) {
		super(pmParams);
	}

	@Override
	protected void wszyscyWPrawo() {
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

	@Override
	protected OPrzygListyPracWyjscie budujWyjscie() {

		return OPrzygListyPracWyjscie.builder().withLista(mListaPrawa).withAccepted(mAccepted).build();
	}

}
