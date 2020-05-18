package Grupy;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import Enums.Komunikat;
import Frames.dbAccess.Components.ObsluzenieComboPicker;

public class ObslugaGrup implements ObsluzenieComboPicker {
	private GrupyRepository mRepo = new GrupyRepository();

	@Override
	public void dodaj() {
		String lvNazwa = JOptionPane.showInputDialog("Podaj nazwę Grupy");
		mRepo.dodajGrupe(lvNazwa);
	}

	public List<GrupaDTO> getListaGrup() {

		return mRepo.pobierzGrupy();
	}

	public String getGrupyPracownikaText(int pmId) {
		StringBuilder lvText = new StringBuilder();
		List<GrupaDTO> lvDane = mRepo.pobierzGrupyPracownika(pmId);
		for (GrupaDTO lvGRupa : lvDane)
			lvText.append(lvGRupa.getNazwa() + "  ");
		return lvText.toString();
	}

	@Override
	public void usun(Object pmObject) {
		if (pmObject != null) {
			if (!Komunikat.PotwierdzenieOperacjiUsuniecia())
				return;
			mRepo.usunGrupe(((GrupaDTO) pmObject).getID());
		}
	}

	public void ustawGrupePracownikowi(int lvIdPracownika, Object pmGrupa) {
		mRepo.ustawGrupePracownikowi(lvIdPracownika, (GrupaDTO) pmGrupa);
	}

	public void usunGrupePracownikowi(int lvIdPracownika, Object pmGrupa) {
		mRepo.usunGrupePracownikowi(lvIdPracownika, (GrupaDTO) pmGrupa);
	}

	@Override
	public ComboBoxModel<Object> getComboBoxModel() {
		ComboBoxModel<Object> mModel = new DefaultComboBoxModel<Object>(getListaGrup().toArray());

		return mModel;
	}
}
