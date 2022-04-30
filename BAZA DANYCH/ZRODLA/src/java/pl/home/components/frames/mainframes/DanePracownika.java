package pl.home.components.frames.mainframes;

import Pracownik.GodzinyPracyPracownika;
import Pracownik.PracownikRepository;
import Wydruki.PrzygotowanieDanych.ObiektDanychPracownika;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import enums.EtatPracownika;
import pl.home.components.frames.parameters.ODanePracownikaWejscie;
import pl.home.components.frames.parameters.ODanePracownikaWyjscie;
import pl.home.components.frames.src.SrcDanePracownika;

public class DanePracownika extends SrcDanePracownika {

	private static final long serialVersionUID = 1L;
	private PracownikRepository mPracownikRepo;
	private ObiektDanychPracownika mDane;
	private PracownikDTO mPracownik;
	private GodzinyPracyPracownika mGodziny;
	public DanePracownika(ODanePracownikaWejscie pmParams) {
		super(pmParams);

	}
	@Override
	protected void onOpen() {
		mPracownik = mParamsIn.getPracownik();
		pytajOZapis();
		super.onOpen();
		mPracownikRepo = new PracownikRepository();
		mDane = mPracownikRepo.getDanePracownika(mParamsIn.getPracownik().getId());
		mGodziny = mPracownikRepo.pobierzGodzinyPracy(mParamsIn.getPracownik().getId());
		przypiszKontrolki();
	}

	private void przypiszKontrolki() {
		mDataUrodzenia.setValue(mDane.getDataUrodzenia());
		txtEtat.setSelectedItem(mDane.getEtat());
		txtUlrop.setValue(mDane.getUrlop());
		txtGodzinaOd.setValue(mGodziny.getGodzinaOd());
		txtGodzinaDo.setValue(mGodziny.getGodzinaDo());
		txtNazwa.setText(mDane.getNazwa());
	}

	@Override
	public void budujOkno() {
		super.budujOkno();
	}

	@Override
	protected void beforeClose() {
		if (mAccepted) {
			mDane.setDataUrodzenia(mDataUrodzenia.getDateValue());
			mDane.setEtat((EtatPracownika) txtEtat.getSelectedItem());
			mDane.setUrlop(txtUlrop.getIntValue());
			GodzinyPracyPracownika lvGodziny = new GodzinyPracyPracownika(txtGodzinaOd.getIntValue(), txtGodzinaDo.getIntValue());
			if (!lvGodziny.equals(mGodziny))
				mPracownikRepo.zapiszGodzinyPracy(lvGodziny, mDane.getIdPracownika());
			mDane.setNazwa(txtNazwa.getText());
			mPracownikRepo.zapiszDanePracownika(mDane);
			mPracownik.setEtat(mDane.getEtat());

		}
		super.beforeClose();
	}

	@Override
	public ODanePracownikaWyjscie budujWyjscie() {
		return new ODanePracownikaWyjscie().pracownik(mPracownik);
	}
}
