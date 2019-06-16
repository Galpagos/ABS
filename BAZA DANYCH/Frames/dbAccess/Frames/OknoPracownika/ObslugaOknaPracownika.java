package dbAccess.Frames.OknoPracownika;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import Absencja.ObslugaAbsencji;
import Enums.Komunikat;
import Enums.SLRodzajeAbsencji;
import Grupy.GrupaDTO;
import Grupy.ObslugaGrup;
import Pracownik.ObslugaPracownka;
import PrzygotowanieDanych.AbsencjaDTO;
import PrzygotowanieDanych.PracownikDTO;
import dbAccess.AbsencjaBean;
import dbAccess.dbAccess;
import dbAccess.Components.ComboPicker;
import dbAccess.Frames.Absencja.OknoAbsencji;

public class ObslugaOknaPracownika
{
	InterfejsOknaPracownika mOkno;
	OknoPracownikaRepository mRepo;
	ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();
	ObslugaAbsencji mObslugaAbsencji = new ObslugaAbsencji();

	public ObslugaOknaPracownika(InterfejsOknaPracownika pmOknoPracownika)
	{
		mOkno = pmOknoPracownika;
		mRepo = new OknoPracownikaRepository();

	}

	public void DodajAbsencje()
	{
		AbsencjaDTO lvAbsencja = new AbsencjaDTO();
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji.urlop_wypoczynkowy;

		lvAbsencja.setId(dbAccess.GetNextID(AbsencjaBean.NazwaTabeli));
		lvAbsencja.setIdPracownika(mOkno.getPracownika().getId());
		lvAbsencja.setOkres(new Interval(DateTime.now().withTimeAtStartOfDay(),
				DateTime.now().withTimeAtStartOfDay().plusHours(2)));
		lvAbsencja.setRodzaj(lvRodzajAbs);

		mObslugaAbsencji.modyfikujAbsencje(lvAbsencja);
		mOkno.odswiezTabele();
	}

	public void ModyfikujAbsencje()
	{
		if (mOkno.getZaznaczenieTabeli() >= 0)
		{
			new OknoAbsencji(mOkno.getAbsencjeZTabeli());
			mOkno.odswiezTabele();
		} else
		{
			Komunikat.oknoPracownikaBrakZaznaczeniaWTabeli.pokaz();
		}
	}

	public void UsunAbsencje()
	{
		if (mOkno.getZaznaczenieTabeli() < 0)
		{
			Komunikat.oknoPracownikaBrakZaznaczeniaWTabeli.pokaz();
			return;
		}
		if (!Komunikat.PotwierdzenieOperacjiUsuniecia())
			return;

		int lvIdAbsencji = mOkno.getAbsencjeZTabeli().getId();
		mRepo.usunAbsencje(lvIdAbsencji);
		mOkno.odswiezTabele();

	}

	public String grupyPracownika()
	{
		return "Nale\u017Cy do grup: " + ObslugaGrup.getGrupyPracownikaText(mOkno.getPracownika().getId());
	}

	public void przypiszGrupe()
	{

		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null)
		{
			ObslugaGrup.ustawGrupePracownikowi(mOkno.getPracownika().getId(), lvGrupa);
		}
		mOkno.getLblGrupy().setText(grupyPracownika());
	}

	public void usunGrupe()
	{
		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null)
		{
			ObslugaGrup.usunGrupePracownikowi(mOkno.getPracownika().getId(), lvGrupa);
		}
		mOkno.getLblGrupy().setText(grupyPracownika());
	}

	public void ustawDateUrodzenia()
	{
		String lvUrlop = new JOptionPane().showInputDialog("Podaj dat� RRRR-MM-DD");
		if (lvUrlop == null)
			return;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date lvData = null;
			lvData = sdf.parse(lvUrlop);
			mObslugaPracownika.ustawDateUrodzeniaPracownikowi(mOkno.getPracownika().getId(), lvData);
		} catch (ParseException e)
		{
			JOptionPane.showMessageDialog(null, "Podaj dat� w odpowiednim formacie!");
		}
	}

	public String getDataUrodzenia(PracownikDTO pmPracownik)
	{
		Object[][] lvDane = mRepo.getDataUrodzenia(pmPracownik.getId());
		if (lvDane[0][0] == null)
			return "";
		return lvDane[0][0].toString().split(" ")[0];
	}

	public String getUrlop(PracownikDTO pmPracownik)
	{
		Object[][] lvDane = mRepo.getUrlopNalezny(pmPracownik.getId());
		if (lvDane[0][0] == null)
			return "";
		return lvDane[0][0].toString();
	}

	public void ustawUrlopNalezny()
	{
		String lvUrlop = new JOptionPane().showInputDialog("Podaj Warto��");
		int lvWartosc;
		try
		{
			lvWartosc = Integer.parseInt(lvUrlop);
			mRepo.ustawUrlopNalezny(mOkno.getPracownika().getId(), lvWartosc);

		} catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Podaj Wartosc Liczbow�!");
		}

	}
}
