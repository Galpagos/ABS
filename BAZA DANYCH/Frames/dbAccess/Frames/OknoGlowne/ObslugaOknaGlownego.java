package dbAccess.Frames.OknoGlowne;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import Enums.Komunikat;
import Parsery.ParseryDB;
import dbAccess.DniWolneBean;
import dbAccess.Components.DatePicker;
import dbAccess.Frames.OknoPracownika.OknoPracownika;
import dbAccess.Frames.OknoSprawozdan.OknoSprawozdan;

public class ObslugaOknaGlownego
{
	InterfejsOknaGlownego mOkno;
	RepositoryOknaGlownego mRepo;

	public ObslugaOknaGlownego(InterfejsOknaGlownego pmSrcOknoGlowne)
	{
		mOkno = pmSrcOknoGlowne;
		mRepo = new RepositoryOknaGlownego();
	}

	public void pokazPracownika()
	{
		if (mOkno.getZaznaczenieTabeli() >= 0)
		{
			new OknoPracownika(mOkno.getPracownikZTabeli());

		} else
		{
			Komunikat.oknoGlowneBrakZaznaczeniaWTabeli.pokaz();
		}
	}

	public void dodajPracownika()
	{
		String lvNazwa = JOptionPane.showInputDialog("Podaj nazwê pracownika: ");
		if (lvNazwa != null)
		{
			mRepo.dodajPracownikaDB(lvNazwa);
			JOptionPane.showMessageDialog(null, "Dodano Pracownika " + lvNazwa, "Dodano Pracownika",
					JOptionPane.INFORMATION_MESSAGE);
			mOkno.odswiezTabele();
		}
	}

	public void usunPracownika()
	{
		if (mOkno.getZaznaczenieTabeli() < 0)
		{
			Komunikat.oknoGlowneBrakZaznaczeniaWTabeli.pokaz();
			return;
		}
		if (!Komunikat.PotwierdzenieOperacjiUsuniecia())
			return;

		mRepo.usunPracownikaDB(mOkno.getPracownikZTabeli().getId());
		mOkno.odswiezTabele();

	}

	public void pokazNieobecnych() throws ParseException
	{
		String lvNieobecni = "";
		Date lvNaKiedy = new DatePicker().setPickedDate();
		if (lvNaKiedy == null)
			return;
		Object[][] lvDane = mRepo.pobierzNieobecnych(lvNaKiedy);

		if (lvDane.length == 0)
			JOptionPane.showMessageDialog(null, "Brak nieobecnoœci na dzieñ: " + ParseryDB.DateParserToMsg(lvNaKiedy), //
					"Nieobecni dnia :" + ParseryDB.DateParserToMsg(lvNaKiedy), JOptionPane.INFORMATION_MESSAGE);
		else
		{
			for (int i = 0; i < lvDane.length; i++)
				lvNieobecni = lvNieobecni + lvDane[i][0].toString() + " od " + lvDane[i][1] + " do " + lvDane[i][2]
						+ " z powodu " + lvDane[i][3] + "\n";
			lvNieobecni = lvNieobecni.replaceAll("00:00:00.0", "");

			JOptionPane.showMessageDialog(null, lvNieobecni, "Nieobecni dnia:" + ParseryDB.DateParserToMsg(lvNaKiedy),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void dodajDzienWolny()
	{
		DniWolneBean lvDzienWolny = new DniWolneBean();
		Date lvData = new DatePicker().setPickedDate();
		if (lvData == null)
			return;
		else
			lvDzienWolny.setData(lvData);

		lvDzienWolny.setOpis(JOptionPane.showInputDialog("Podaj powód dnia wolnego: "));
		if (lvDzienWolny.getOpis() == null)
			return;
		else
			mRepo.zapiszNowyDzienWolny(lvDzienWolny);

		JOptionPane.showMessageDialog(null,
				"Dnia: " + ParseryDB.DateParserToMsg(lvDzienWolny.getData()) + " z powodu " + lvDzienWolny.getOpis(),
				"Dodano dzieñ wolny", JOptionPane.INFORMATION_MESSAGE);

	}

	public void pokazDniWolne()
	{
		Object[][] lvDane = mRepo.pobierzDniWolne();
		String lvWynik = "";
		for (int i = 0; i < lvDane.length; i++)
			lvWynik = lvWynik + lvDane[i][0].toString() + " z powodu " + lvDane[i][1].toString() + "\n";
		lvWynik = lvWynik.replaceAll("00:00:00.0", "");

		JOptionPane.showMessageDialog(null, "Ostatnio wprowadzone 5 dni: \n" + lvWynik, "Dni Wolne",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public void sprawozdanie()
	{
		new OknoSprawozdan();
	}

	public void zwolnijPracownika()
	{
		if (mOkno.getZaznaczenieTabeli() >= 0)
		{
			Date lvData = new DatePicker().setPickedDate();
			if (lvData != null)
				mRepo.zwolnijPracownika(mOkno.getPracownikZTabeli().getId(), lvData);
			mOkno.odswiezTabele();
		} else
		{
			JOptionPane.showMessageDialog(null, "Wybierz pracownika!", "Ostrze¿enie", JOptionPane.WARNING_MESSAGE);
		}

	}
}
