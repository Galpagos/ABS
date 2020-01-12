package Grupy;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import Enums.Komunikat;
import Frames.dbAccess.Components.ObsluzenieComboPicker;

public class ObslugaGrup implements ObsluzenieComboPicker {
	static RepoGrupy mRepo = new RepoGrupy();

	public void dodaj() {
		String lvNazwa = JOptionPane.showInputDialog("Podaj nazwe Grupy");
		mRepo.dodajGrupe(lvNazwa);
	}

	public static List<GrupaDTO> getListaGrup() {
		List<GrupaDTO> lvLista = new ArrayList<>();

		Object[][] lvDane = mRepo.getGrupy();
		for (Object[] ob : lvDane) {
			GrupaDTO lvGrupa = new GrupaDTO();
			lvGrupa.setID((int) ob[0]);
			lvGrupa.setNazwa((String) ob[1]);
			lvLista.add(lvGrupa);
		}
		return lvLista;
	}

	public static String getGrupyPracownikaText(int pmId) {
		StringBuilder lvText = new StringBuilder();
		Object[][] lvDane = mRepo.pobierzGrupy(pmId);
		for (Object[] ob : lvDane)
			for (Object ob2 : ob)
				if (ob2.getClass().equals(String.class))
					lvText.append(ob2.toString() + "  ");
		return lvText.toString();
	}

	public void usun(Object pmObject) {
		if (pmObject != null) {
			if (!Komunikat.PotwierdzenieOperacjiUsuniecia())
				return;
			mRepo.usunGrupe((GrupaDTO) pmObject);
		}
	}

	public static void ustawGrupePracownikowi(int lvIdPracownika, Object pmGrupa) {
		mRepo.ustawGrupePracownikowi(lvIdPracownika, (GrupaDTO) pmGrupa);
	}

	public static void usunGrupePracownikowi(int lvIdPracownika, Object pmGrupa) {
		mRepo.usunGrupePracownikowi(lvIdPracownika, (GrupaDTO) pmGrupa);
	}

	@Override
	public ComboBoxModel<Object> getComboBoxModel() {
		ComboBoxModel<Object> mModel = new DefaultComboBoxModel<Object>(getListaGrup().toArray());

		return mModel;
	}
}
