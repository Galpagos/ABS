package Frames.dbAccess.Frames.OknoGlowne;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JOptionPane;

import Enums.Komunikat;
import Frames.dbAccess.Components.DatePicker;
import Frames.dbAccess.Components.ScriptParams;
import Frames.dbAccess.Frames.OknoSprawozdan.OknoSprawozdan;
import Parsery.ParseryDB;
import Pracownik.ObslugaPracownka;
import dbAccess.DniWolneBean;

public class ObslugaOknaGlownego {
	InterfejsOknaGlownego mOkno;
	RepositoryOknaGlownego mRepo;
	ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();

	public ObslugaOknaGlownego(InterfejsOknaGlownego pmSrcOknoGlowne) {
		mOkno = pmSrcOknoGlowne;
		mRepo = new RepositoryOknaGlownego();
	}

	public void pokazPracownika() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			Komunikat.oknoGlowneBrakZaznaczeniaWTabeli.pokaz();
		} else {
			mObslugaPracownika.pokazPracownika(mOkno.getPracownikZTabeli());
		}
	}

	public void dodajPracownika() {
		mObslugaPracownika.dodajNowegoPracownika();
		mOkno.odswiezTabele();
	}

	public void usunPracownika() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			Komunikat.oknoGlowneBrakZaznaczeniaWTabeli.pokaz();
		} else {
			mObslugaPracownika.usunPracownika(mOkno.getPracownikZTabeli().getId());
			mOkno.odswiezTabele();
		}
	}

	public void pokazNieobecnych() {
		String lvNieobecni = "";
		Date lvNaKiedy = new DatePicker().setPickedDate();
		if (lvNaKiedy == null)
			return;
		Object[][] lvDane = mRepo.pobierzNieobecnych(lvNaKiedy);

		if (lvDane.length == 0)
			JOptionPane.showMessageDialog(null, "Brak nieobecnoœci na dzieñ: " + ParseryDB.DateParserToMsg(lvNaKiedy), //
					"Nieobecni dnia :" + ParseryDB.DateParserToMsg(lvNaKiedy), JOptionPane.INFORMATION_MESSAGE);
		else {
			for (int i = 0; i < lvDane.length; i++)
				lvNieobecni = lvNieobecni + lvDane[i][0].toString() + " od " + lvDane[i][1] + " do " + lvDane[i][2]
						+ " z powodu " + lvDane[i][3] + "\n";
			lvNieobecni = lvNieobecni.replaceAll("00:00:00.0", "");

			JOptionPane.showMessageDialog(null, lvNieobecni, "Nieobecni dnia:" + ParseryDB.DateParserToMsg(lvNaKiedy),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void dodajDzienWolny() {
		DniWolneBean lvDzienWolny = new DniWolneBean();
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return;
		else {
			if (DayOfWeek.SUNDAY.equals(lvData.getDayOfWeek()) || DayOfWeek.SATURDAY.equals(lvData.getDayOfWeek())) {
				JOptionPane.showMessageDialog(null, "Nie mo¿na dodaæ dnia wolnego w weekend!", "B³¹d",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else
				lvDzienWolny.setData(lvData);
		}

		lvDzienWolny.setOpis(JOptionPane.showInputDialog("Podaj powód dnia wolnego: "));
		if (lvDzienWolny.getOpis() == null)
			return;
		else
			mRepo.zapiszNowyDzienWolny(lvDzienWolny);

		JOptionPane.showMessageDialog(null, "Dnia: " + lvDzienWolny.getData() + " z powodu " + lvDzienWolny.getOpis(),
				"Dodano dzieñ wolny", JOptionPane.INFORMATION_MESSAGE);

	}

	public void pokazDniWolne() {
		Object[][] lvDane = mRepo.pobierzDniWolne();
		String lvWynik = "";
		for (int i = 0; i < lvDane.length; i++)
			lvWynik = lvWynik + lvDane[i][0].toString() + " z powodu " + lvDane[i][1].toString() + "\n";
		lvWynik = lvWynik.replaceAll("00:00:00.0", "");

		JOptionPane.showMessageDialog(null, "Ostatnio wprowadzone 20 dni: \n" + lvWynik, "Dni Wolne",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public void sprawozdanie() {
		new OknoSprawozdan(new ScriptParams());
	}

	public void zwolnijPracownika() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			JOptionPane.showMessageDialog(null, "Wybierz pracownika!", "Ostrze¿enie", JOptionPane.WARNING_MESSAGE);
		} else {
			mObslugaPracownika.zwolnijPracownika(mOkno.getPracownikZTabeli().getId());
			mOkno.odswiezTabele();
		}
	}
}
